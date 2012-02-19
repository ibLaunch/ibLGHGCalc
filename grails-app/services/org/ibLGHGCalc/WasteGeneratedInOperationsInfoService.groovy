package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class WasteGeneratedInOperationsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    WasteGeneratedInOperationsInfo findWasteGeneratedInOperationsInfo(Long id) {
      WasteGeneratedInOperationsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def WasteGeneratedInOperationsInfo[] findWasteGeneratedInOperationsInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.wasteGeneratedInOperationsInfoList
      //WasteGeneratedInOperationsInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return WasteGeneratedInOperationsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in wasteGeneratedInOperationsInfoListService.findwasteGeneratedInOperationsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def wasteGeneratedInOperationsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Distribution sources which are within the inventory year
      int i = 0
      theOrganization?.wasteGeneratedInOperationsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            wasteGeneratedInOperationsInfoList[i] = it
            i++
         }
      }
      return wasteGeneratedInOperationsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def WasteGeneratedInOperationsInfo[] getWasteGeneratedInOperationsInfos(Map<String, String> parameters) {
      //WasteGeneratedInOperationsInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.wasteGeneratedInOperationsInfoList
      //WasteGeneratedInOperationsInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def WasteGeneratedInOperationsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      WasteGeneratedInOperationsInfo theWasteGeneratedInOperationsInfo = WasteGeneratedInOperationsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Distribution"
      String emissionsType = "Waste Generated In Operations" //Method

      def emissions =[:]
      
      if (!theWasteGeneratedInOperationsInfo) {
        println "theWasteGeneratedInOperationsInfo is Null"
        theWasteGeneratedInOperationsInfo = new WasteGeneratedInOperationsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theWasteGeneratedInOperationsInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theWasteGeneratedInOperationsInfo is Not Null"
        theWasteGeneratedInOperationsInfo.emissionsDetailsList.each{
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
            
      theWasteGeneratedInOperationsInfo.dataBeginDate = dataBeginDate
      theWasteGeneratedInOperationsInfo.dataEndDate = dataEndDate   
      
      theWasteGeneratedInOperationsInfo.sourceDescription=parameters.sourceDescription
      theWasteGeneratedInOperationsInfo.serviceProviderName=parameters.serviceProviderName
      theWasteGeneratedInOperationsInfo.serviceProviderContact=parameters.serviceProviderContact
      theWasteGeneratedInOperationsInfo.wasteType=parameters.wasteType      
      theWasteGeneratedInOperationsInfo.wasteTreatmentType=parameters.wasteTreatmentType
      theWasteGeneratedInOperationsInfo.wasteProduced=parameters.wasteProduced?.toDouble()
      theWasteGeneratedInOperationsInfo.wasteProduced_Unit=parameters.wasteProduced_Unit
        
      switch (parameters.methodType){
        case ["Waste Type Specific Method - Waste Generated In Operations"] :       
              theWasteGeneratedInOperationsInfo.EF_WasteTypeAndWasteTreatment=parameters.EF_WasteTypeAndWasteTreatment?.toDouble()
              theWasteGeneratedInOperationsInfo.EF_WasteTypeAndWasteTreatment_Unit=parameters.EF_WasteTypeAndWasteTreatment_Unit      

         break
        case ["Average Data Method - Waste Generated In Operations"] :                
              theWasteGeneratedInOperationsInfo.percentOfWasteTreatedByWasteTreatmentMethod=parameters.percentOfWasteTreatedByWasteTreatmentMethod?.toDouble()   
              theWasteGeneratedInOperationsInfo.EF_WasteTreatmentMethod=parameters.EF_WasteTreatmentMethod?.toDouble()
              theWasteGeneratedInOperationsInfo.EF_WasteTreatmentMethod_Unit=parameters.EF_WasteTreatmentMethod_Unit
              
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theWasteGeneratedInOperationsInfo.userNotesOnData=parameters.userNotesOnData
      theWasteGeneratedInOperationsInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theWasteGeneratedInOperationsInfo.organization = theOrganization
      
      //--Save the user reference
      theWasteGeneratedInOperationsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theWasteGeneratedInOperationsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theWasteGeneratedInOperationsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theWasteGeneratedInOperationsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theWasteGeneratedInOperationsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //WasteGeneratedInOperationsInfo.get(parameters.id)?.delete()
      def theWasteGeneratedInOperationsInfo = WasteGeneratedInOperationsInfo.get(parameters.id)

      if (theWasteGeneratedInOperationsInfo){
        delete(theWasteGeneratedInOperationsInfo)
      }
      else {
          println "No theWasteGeneratedInOperationsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theWasteGeneratedInOperationsInfo, delete) or hasPermission(#theWasteGeneratedInOperationsInfo, admin)")
   void delete(WasteGeneratedInOperationsInfo theWasteGeneratedInOperationsInfo) {
      theWasteGeneratedInOperationsInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theWasteGeneratedInOperationsInfo
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
                case ["Waste Type Specific Method - Waste Generated In Operations"] :                                          
                     combinedEmissions=  parameters.wasteProduced?.toDouble() *
                                        parameters.EF_WasteTypeAndWasteTreatment?.toDouble()                   
                     
                     break

                case ["Average Data Method - Waste Generated In Operations"] :                
                    combinedEmissions= parameters.wasteProduced?.toDouble() *
                                       parameters.EF_WasteTreatmentMethod?.toDouble() *
                                       parameters.percentOfWasteTreatedByWasteTreatmentMethod?.toDouble()
                
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
