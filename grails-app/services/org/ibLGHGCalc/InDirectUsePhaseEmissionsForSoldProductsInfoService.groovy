package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class InDirectUsePhaseEmissionsForSoldProductsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    InDirectUsePhaseEmissionsForSoldProductsInfo findInDirectUsePhaseEmissionsForSoldProductsInfo(Long id) {
      InDirectUsePhaseEmissionsForSoldProductsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def InDirectUsePhaseEmissionsForSoldProductsInfo[] findInDirectUsePhaseEmissionsForSoldProductsInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.inDirectUsePhaseEmissionsForSoldProductsInfoList
      //InDirectUsePhaseEmissionsForSoldProductsInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return InDirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in inDirectUsePhaseEmissionsForSoldProductsInfoListService.findinDirectUsePhaseEmissionsForSoldProductsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def inDirectUsePhaseEmissionsForSoldProductsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased InDirectUsePhaseEmissionsForSoldProducts sources which are within the inventory year
      int i = 0
      theOrganization?.inDirectUsePhaseEmissionsForSoldProductsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            inDirectUsePhaseEmissionsForSoldProductsInfoList[i] = it
            i++
         }
      }
      return inDirectUsePhaseEmissionsForSoldProductsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def InDirectUsePhaseEmissionsForSoldProductsInfo[] getInDirectUsePhaseEmissionsForSoldProductsInfos(Map<String, String> parameters) {
      //InDirectUsePhaseEmissionsForSoldProductsInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.inDirectUsePhaseEmissionsForSoldProductsInfoList
      //InDirectUsePhaseEmissionsForSoldProductsInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def InDirectUsePhaseEmissionsForSoldProductsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      InDirectUsePhaseEmissionsForSoldProductsInfo theInDirectUsePhaseEmissionsForSoldProductsInfo = InDirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased InDirectUsePhaseEmissionsForSoldProducts"
      String emissionsType = "In-Direct Use Phase Emissions For Sold Products" //Method

      def emissions =[:]
      
      if (!theInDirectUsePhaseEmissionsForSoldProductsInfo) {
        println "theInDirectUsePhaseEmissionsForSoldProductsInfo is Null"
        theInDirectUsePhaseEmissionsForSoldProductsInfo = new InDirectUsePhaseEmissionsForSoldProductsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theInDirectUsePhaseEmissionsForSoldProductsInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theInDirectUsePhaseEmissionsForSoldProductsInfo is Not Null"
        theInDirectUsePhaseEmissionsForSoldProductsInfo.emissionsDetailsList.each{
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
            
      theInDirectUsePhaseEmissionsForSoldProductsInfo.dataBeginDate = dataBeginDate
      theInDirectUsePhaseEmissionsForSoldProductsInfo.dataEndDate = dataEndDate   
      
      theInDirectUsePhaseEmissionsForSoldProductsInfo.sourceDescription=parameters.sourceDescription
      theInDirectUsePhaseEmissionsForSoldProductsInfo.productType=parameters.productType      
      theInDirectUsePhaseEmissionsForSoldProductsInfo.productName=parameters.productName
      theInDirectUsePhaseEmissionsForSoldProductsInfo.numberSoldInReportingPeriod=parameters.numberSoldInReportingPeriod?.toDouble()
        
      switch (parameters.methodType){
        case ["Typical Use Phase Profile Method"] :

              theInDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeExpectedUsesOfProduct=parameters.totalLifetimeExpectedUsesOfProduct?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.scenarioDescription=parameters.scenarioDescription
            
              theInDirectUsePhaseEmissionsForSoldProductsInfo.percentOfTotalLifetimeUsesInThisScenario=parameters.percentOfTotalLifetimeUsesInThisScenario?.toDouble()
              
              
              theInDirectUsePhaseEmissionsForSoldProductsInfo.fuelType=parameters.fuelType              
              theInDirectUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUseInThisScenario=parameters.fuelConsumedPerUseInThisScenario?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUseInThisScenario_Unit=parameters.fuelConsumedPerUseInThisScenario_Unit      
              theInDirectUsePhaseEmissionsForSoldProductsInfo.EF_Fuel=parameters.EF_Fuel?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.EF_Fuel_Unit=parameters.EF_Fuel_Unit

              theInDirectUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUseInThisScenario=parameters.electricityConsumedPerUseInThisScenario?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUseInThisScenario_Unit=parameters.electricityConsumedPerUseInThisScenario_Unit
              theInDirectUsePhaseEmissionsForSoldProductsInfo.EF_Electricity=parameters.EF_Electricity?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.EF_Electricity_Unit=parameters.EF_Electricity_Unit

              theInDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantType=parameters.refrigerantType              
              theInDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUseInThisScenario=parameters.refrigerantLeakagePerUseInThisScenario?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUseInThisScenario_Unit=parameters.refrigerantLeakagePerUseInThisScenario_Unit      
              theInDirectUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant=parameters.EF_Refrigerant?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant_Unit=parameters.EF_Refrigerant_Unit

              theInDirectUsePhaseEmissionsForSoldProductsInfo.GHG_EmittedIndirectly=parameters.GHG_EmittedIndirectly?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.GHG_EmittedIndirectly_Unit=parameters.GHG_EmittedIndirectly_Unit
              theInDirectUsePhaseEmissionsForSoldProductsInfo.GHG_Name=parameters.GHG_Name
              theInDirectUsePhaseEmissionsForSoldProductsInfo.GWP_GHG=parameters.GWP_GHG?.toDouble()
              
         break
         
        case ["Functional Unit Method"] :                
              theInDirectUsePhaseEmissionsForSoldProductsInfo.functionalUnitsPerformedPerProduct=parameters.functionalUnitsPerformedPerProduct?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.emissionsPerFunctionalUnitOfProduct=parameters.emissionsPerFunctionalUnitOfProduct?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.emissionsPerFunctionalUnitOfProduct_Unit=parameters.emissionsPerFunctionalUnitOfProduct_Unit      
              theInDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeEmissions=parameters.totalLifetimeEmissions?.toDouble()
              theInDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeEmissions_Unit=parameters.totalLifetimeEmissions_Unit
              
         break
            
        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theInDirectUsePhaseEmissionsForSoldProductsInfo.userNotesOnData=parameters.userNotesOnData
      theInDirectUsePhaseEmissionsForSoldProductsInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theInDirectUsePhaseEmissionsForSoldProductsInfo.organization = theOrganization
      
      //--Save the user reference
      theInDirectUsePhaseEmissionsForSoldProductsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theInDirectUsePhaseEmissionsForSoldProductsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theInDirectUsePhaseEmissionsForSoldProductsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theInDirectUsePhaseEmissionsForSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theInDirectUsePhaseEmissionsForSoldProductsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //InDirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)?.delete()
      def theInDirectUsePhaseEmissionsForSoldProductsInfo = InDirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)

      if (theInDirectUsePhaseEmissionsForSoldProductsInfo){
        delete(theInDirectUsePhaseEmissionsForSoldProductsInfo)
      }
      else {
          println "No theInDirectUsePhaseEmissionsForSoldProductsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theInDirectUsePhaseEmissionsForSoldProductsInfo, delete) or hasPermission(#theInDirectUsePhaseEmissionsForSoldProductsInfo, admin)")
   void delete(InDirectUsePhaseEmissionsForSoldProductsInfo theInDirectUsePhaseEmissionsForSoldProductsInfo) {
      theInDirectUsePhaseEmissionsForSoldProductsInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theInDirectUsePhaseEmissionsForSoldProductsInfo
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
                case ["Typical Use Phase Profile Method"] :                                          
                     combinedEmissions=  parameters.totalLifetimeExpectedUsesOfProduct?.toDouble() * 
                                         parameters.percentOfTotalLifetimeUsesInThisScenario?.toDouble() *
                                         parameters.numberSoldInReportingPeriod?.toDouble() *
                                        (
                                          (parameters.fuelConsumedPerUseInThisScenario?.toDouble() * parameters.EF_Fuel?.toDouble() )+
                                          (parameters.electricityConsumedPerUseInThisScenario?.toDouble() * parameters.EF_Electricity?.toDouble() )+
                                          (parameters.refrigerantLeakagePerUseInThisScenario?.toDouble() * parameters.EF_Refrigerant?.toDouble() ) +                      
                                          (parameters.GHG_EmittedIndirectly?.toDouble() * parameters.GWP_GHG?.toDouble() ) 
                                        )
                                                             
                     break

                case ["Functional Unit Method"] :                
                    combinedEmissions= parameters.totalLifetimeEmissions?.toDouble() /
                                       ( parameters.functionalUnitsPerformedPerProduct?.toDouble() *parameters.numberSoldInReportingPeriod?.toDouble())
                
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
