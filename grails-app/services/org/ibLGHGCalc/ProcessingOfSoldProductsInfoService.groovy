package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class ProcessingOfSoldProductsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    ProcessingOfSoldProductsInfo findProcessingOfSoldProductsInfo(Long id) {
      ProcessingOfSoldProductsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def ProcessingOfSoldProductsInfo[] findProcessingOfSoldProductsInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.processingOfSoldProductsInfoList
      //ProcessingOfSoldProductsInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return ProcessingOfSoldProductsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in processingOfSoldProductsInfoListService.findprocessingOfSoldProductsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def processingOfSoldProductsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased ProcessingOfSoldProducts sources which are within the inventory year
      int i = 0
      theOrganization?.processingOfSoldProductsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            processingOfSoldProductsInfoList[i] = it
            i++
         }
      }
      return processingOfSoldProductsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def ProcessingOfSoldProductsInfo[] getProcessingOfSoldProductsInfos(Map<String, String> parameters) {
      //ProcessingOfSoldProductsInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.processingOfSoldProductsInfoList
      //ProcessingOfSoldProductsInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def ProcessingOfSoldProductsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      ProcessingOfSoldProductsInfo theProcessingOfSoldProductsInfo = ProcessingOfSoldProductsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased ProcessingOfSoldProducts"
      String emissionsType = "Processing Of Sold Products Emission" //Method

      def emissions =[:]
      
      if (!theProcessingOfSoldProductsInfo) {
        println "theProcessingOfSoldProductsInfo is Null"
        theProcessingOfSoldProductsInfo = new ProcessingOfSoldProductsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theProcessingOfSoldProductsInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theProcessingOfSoldProductsInfo is Not Null"
        theProcessingOfSoldProductsInfo.emissionsDetailsList.each{
          EmissionsDetails theEmissionsDetails = it
          emissions = calculateEmissions(parameters, programType, emissionsType)
          
          theEmissionsDetails.combinedEmissions = emissions.get("combinedEmissions").toDouble()		 
          theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions").toDouble()
          theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions").toDouble()
          theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions").toDouble()
          theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions").toDouble()

          theEmissionsDetails.combinedEmissionsUnit = emissions.get("combinedEmissionsUnit")		
          theEmissionsDetails.CO2EmissionsUnit = emissions.get("CO2EmissionsUnit")
          theEmissionsDetails.biomassCO2EmissionsUnit = emissions.get("biomassCO2EmissionsUnit")
          theEmissionsDetails.CH4EmissionsUnit = emissions.get("CH4EmissionsUnit")
          theEmissionsDetails.N2OEmissionsUnit = emissions.get("N2OEmissionsUnit")

          theEmissionsDetails.emissionsType = emissions.get("emissionsType")
          theEmissionsDetails.programType = emissions.get("programType")
        }        
      }
      
      Date dataBeginDate = new Date().parse("yyyy-MM-dd", parameters.dataBeginDate)
      Date dataEndDate = new Date().parse("yyyy-MM-dd", parameters.dataEndDate)
            
      theProcessingOfSoldProductsInfo.dataBeginDate = dataBeginDate
      theProcessingOfSoldProductsInfo.dataEndDate = dataEndDate   
      
      theProcessingOfSoldProductsInfo.sourceDescription=parameters.sourceDescription
      theProcessingOfSoldProductsInfo.soldProductName=parameters.soldProductName      
      theProcessingOfSoldProductsInfo.productSoldTo=parameters.productSoldTo
      theProcessingOfSoldProductsInfo.productSoldToContact=parameters.productSoldToContact
        
      switch (parameters.methodType){
        case ["Site Specific Method - Processing Of Sold Products Emissions"] :
              theProcessingOfSoldProductsInfo.fuelType=parameters.fuelType              
              theProcessingOfSoldProductsInfo.fuelConsumed=parameters.fuelConsumed?.toDouble()
              theProcessingOfSoldProductsInfo.fuelConsumed_Unit=parameters.fuelConsumed_Unit      
              theProcessingOfSoldProductsInfo.EF_Fuel=parameters.EF_Fuel?.toDouble()
              theProcessingOfSoldProductsInfo.EF_Fuel_Unit=parameters.EF_Fuel_Unit

              theProcessingOfSoldProductsInfo.electricityConsumed=parameters.electricityConsumed?.toDouble()
              theProcessingOfSoldProductsInfo.electricityConsumed_Unit=parameters.electricityConsumed_Unit
              theProcessingOfSoldProductsInfo.EF_Electricity=parameters.EF_Electricity?.toDouble()
              theProcessingOfSoldProductsInfo.EF_Electricity_Unit=parameters.EF_Electricity_Unit

              theProcessingOfSoldProductsInfo.refrigerantType=parameters.refrigerantType              
              theProcessingOfSoldProductsInfo.refrigerantLeakage=parameters.refrigerantLeakage?.toDouble()
              theProcessingOfSoldProductsInfo.refrigerantLeakage_Unit=parameters.refrigerantLeakage_Unit      
              theProcessingOfSoldProductsInfo.EF_Refrigerant=parameters.EF_Refrigerant?.toDouble()
              theProcessingOfSoldProductsInfo.EF_Refrigerant_Unit=parameters.EF_Refrigerant_Unit

              theProcessingOfSoldProductsInfo.massOfWasteOutput=parameters.massOfWasteOutput?.toDouble()
              theProcessingOfSoldProductsInfo.massOfWasteOutput_Unit=parameters.massOfWasteOutput_Unit      
              theProcessingOfSoldProductsInfo.EF_WasteActivity=parameters.EF_WasteActivity?.toDouble()
              theProcessingOfSoldProductsInfo.EF_WasteActivity_Unit=parameters.EF_WasteActivity_Unit

         break
        case ["Average Data Method - Processing Of Sold Products Emissions"] :                
              theProcessingOfSoldProductsInfo.massOfSoldIntermediateProduct=parameters.massOfSoldIntermediateProduct?.toDouble()
              theProcessingOfSoldProductsInfo.massOfSoldIntermediateProduct_Unit=parameters.massOfSoldIntermediateProduct_Unit      
              theProcessingOfSoldProductsInfo.EF_ProcessingOfSoldProducts=parameters.EF_ProcessingOfSoldProducts?.toDouble()
              theProcessingOfSoldProductsInfo.EF_ProcessingOfSoldProducts_Unit=parameters.EF_ProcessingOfSoldProducts_Unit
              
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theProcessingOfSoldProductsInfo.userNotesOnData=parameters.userNotesOnData
      theProcessingOfSoldProductsInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theProcessingOfSoldProductsInfo.organization = theOrganization
      
      //--Save the user reference
      theProcessingOfSoldProductsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theProcessingOfSoldProductsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theProcessingOfSoldProductsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theProcessingOfSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theProcessingOfSoldProductsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //ProcessingOfSoldProductsInfo.get(parameters.id)?.delete()
      def theProcessingOfSoldProductsInfo = ProcessingOfSoldProductsInfo.get(parameters.id)

      if (theProcessingOfSoldProductsInfo){
        delete(theProcessingOfSoldProductsInfo)
      }
      else {
          println "No theProcessingOfSoldProductsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theProcessingOfSoldProductsInfo, delete) or hasPermission(#theProcessingOfSoldProductsInfo, admin)")
   void delete(ProcessingOfSoldProductsInfo theProcessingOfSoldProductsInfo) {
      theProcessingOfSoldProductsInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theProcessingOfSoldProductsInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0
        println "I am in..............calculateEmissions"
        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if (programType.equals("US EPA")) {
            println "I am in..............if program type"
            switch (parameters.methodType){
                case ["Site Specific Method - Processing Of Sold Products Emissions"] :                                          
                     combinedEmissions= ( parameters.fuelConsumed?.toDouble() *
                                        parameters.EF_Fuel?.toDouble() +
                                        parameters.electricityConsumed?.toDouble()*
                                        parameters.EF_Electricity?.toDouble()+
                                        parameters.refrigerantLeakage?.toDouble() *
                                        parameters.EF_Refrigerant?.toDouble() ) *
                                        (parameters.massOfWasteOutput?.toDouble() /
                                        parameters.EF_WasteActivity?.toDouble())                     
                     
                     break

                case ["Average Data Method - Processing Of Sold Products Emissions"] :                
                    combinedEmissions= parameters.massOfSoldIntermediateProduct?.toDouble() *
                                       parameters.EF_ProcessingOfSoldProducts?.toDouble()
                
                     break

                default:                 
                     println "In-correct Method Type for Info service"                        
            } // Switch                        
         }  // if statement
         
         emissions.put("combinedEmissions", combinedEmissions)		 
         emissions.put("CO2Emissions", 0)
         emissions.put("biomassCO2Emissions", 0)
         emissions.put("CH4Emissions", 0)
         emissions.put("N2OEmissions", 0)

         emissions.put("combinedEmissionsUnit", "Kg")
         emissions.put("CO2EmissionsUnit", "lb")
         emissions.put("biomassCO2EmissionsUnit", "lb")
         emissions.put("CH4EmissionsUnit", "lb")
         emissions.put("N2OEmissionsUnit", "lb")

         emissions.put("emissionsType", emissionsType)
         emissions.put("programType", programType)
         
         println "I finished calculating emission details in purchased Energy"
         return emissions        
    }                
}
