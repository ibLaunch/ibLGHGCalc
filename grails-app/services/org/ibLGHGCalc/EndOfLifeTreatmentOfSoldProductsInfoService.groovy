package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class EndOfLifeTreatmentOfSoldProductsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    EndOfLifeTreatmentOfSoldProductsInfo findEndOfLifeTreatmentOfSoldProductsInfo(Long id) {
      EndOfLifeTreatmentOfSoldProductsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def EndOfLifeTreatmentOfSoldProductsInfo[] findEndOfLifeTreatmentOfSoldProductsInfos(Map<String, String> parameters) {
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return EndOfLifeTreatmentOfSoldProductsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in endOfLifeTreatmentOfSoldProductsInfoListService.findendOfLifeTreatmentOfSoldProductsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def endOfLifeTreatmentOfSoldProductsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Distribution sources which are within the inventory year
      int i = 0
      theOrganization?.endOfLifeTreatmentOfSoldProductsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            endOfLifeTreatmentOfSoldProductsInfoList[i] = it
            i++
         }
      }
      return endOfLifeTreatmentOfSoldProductsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def EndOfLifeTreatmentOfSoldProductsInfo[] getEndOfLifeTreatmentOfSoldProductsInfos(Map<String, String> parameters) {
      //EndOfLifeTreatmentOfSoldProductsInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.endOfLifeTreatmentOfSoldProductsInfoList
      //EndOfLifeTreatmentOfSoldProductsInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def EndOfLifeTreatmentOfSoldProductsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      EndOfLifeTreatmentOfSoldProductsInfo theEndOfLifeTreatmentOfSoldProductsInfo = EndOfLifeTreatmentOfSoldProductsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Distribution"
      String emissionsType = "End Of Life Treatment Of Sold Products" //Method

      def emissions =[:]
      
      if (!theEndOfLifeTreatmentOfSoldProductsInfo) {
        println "theEndOfLifeTreatmentOfSoldProductsInfo is Null"
        theEndOfLifeTreatmentOfSoldProductsInfo = new EndOfLifeTreatmentOfSoldProductsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theEndOfLifeTreatmentOfSoldProductsInfo.addToEmissionsDetailsList(theEmissionsDetails)
        
      }
      else
      {
        println "theEndOfLifeTreatmentOfSoldProductsInfo is Not Null"
        theEndOfLifeTreatmentOfSoldProductsInfo.emissionsDetailsList.each{
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
            
      theEndOfLifeTreatmentOfSoldProductsInfo.dataBeginDate = dataBeginDate
      theEndOfLifeTreatmentOfSoldProductsInfo.dataEndDate = dataEndDate   
      
      theEndOfLifeTreatmentOfSoldProductsInfo.sourceDescription=parameters.sourceDescription
      theEndOfLifeTreatmentOfSoldProductsInfo.soldProductName=parameters.soldProductName
        
      switch (parameters.methodType){
        case ["Calculation Method"] :                
              theEndOfLifeTreatmentOfSoldProductsInfo.massOfSoldProductsAfterConsumerUse=parameters.massOfSoldProductsAfterConsumerUse?.toDouble()
              theEndOfLifeTreatmentOfSoldProductsInfo.massOfSoldProductsAfterConsumerUse_Unit=parameters.massOfSoldProductsAfterConsumerUse_Unit                        
              theEndOfLifeTreatmentOfSoldProductsInfo.percentOfWasteTreatedByWasteTreatmentMethod=parameters.percentOfWasteTreatedByWasteTreatmentMethod?.toDouble()   
              theEndOfLifeTreatmentOfSoldProductsInfo.wasteType=parameters.wasteType      
              theEndOfLifeTreatmentOfSoldProductsInfo.wasteTreatmentType=parameters.wasteTreatmentType              
              theEndOfLifeTreatmentOfSoldProductsInfo.EF_WasteTreatmentMethod=parameters.EF_WasteTreatmentMethod?.toDouble()
              theEndOfLifeTreatmentOfSoldProductsInfo.EF_WasteTreatmentMethod_Unit=parameters.EF_WasteTreatmentMethod_Unit
              
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theEndOfLifeTreatmentOfSoldProductsInfo.userNotesOnData=parameters.userNotesOnData
      theEndOfLifeTreatmentOfSoldProductsInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theEndOfLifeTreatmentOfSoldProductsInfo.organization = theOrganization
      
      //--Save the user reference
      theEndOfLifeTreatmentOfSoldProductsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theEndOfLifeTreatmentOfSoldProductsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theEndOfLifeTreatmentOfSoldProductsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theEndOfLifeTreatmentOfSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theEndOfLifeTreatmentOfSoldProductsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //EndOfLifeTreatmentOfSoldProductsInfo.get(parameters.id)?.delete()
      def theEndOfLifeTreatmentOfSoldProductsInfo = EndOfLifeTreatmentOfSoldProductsInfo.get(parameters.id)

      if (theEndOfLifeTreatmentOfSoldProductsInfo){
        delete(theEndOfLifeTreatmentOfSoldProductsInfo)
      }
      else {
          println "No theEndOfLifeTreatmentOfSoldProductsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theEndOfLifeTreatmentOfSoldProductsInfo, delete) or hasPermission(#theEndOfLifeTreatmentOfSoldProductsInfo, admin)")
   void delete(EndOfLifeTreatmentOfSoldProductsInfo theEndOfLifeTreatmentOfSoldProductsInfo) {
      theEndOfLifeTreatmentOfSoldProductsInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theEndOfLifeTreatmentOfSoldProductsInfo
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
                case ["Calculation Method"] :                
                    combinedEmissions= parameters.massOfSoldProductsAfterConsumerUse?.toDouble() *
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
