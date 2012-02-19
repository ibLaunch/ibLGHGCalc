package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class DirectUsePhaseEmissionsForSoldProductsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    DirectUsePhaseEmissionsForSoldProductsInfo findDirectUsePhaseEmissionsForSoldProductsInfo(Long id) {
      DirectUsePhaseEmissionsForSoldProductsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def DirectUsePhaseEmissionsForSoldProductsInfo[] findDirectUsePhaseEmissionsForSoldProductsInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.directUsePhaseEmissionsForSoldProductsInfoList
      //DirectUsePhaseEmissionsForSoldProductsInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return DirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in directUsePhaseEmissionsForSoldProductsInfoListService.finddirectUsePhaseEmissionsForSoldProductsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def directUsePhaseEmissionsForSoldProductsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased DirectUsePhaseEmissionsForSoldProducts sources which are within the inventory year
      int i = 0
      theOrganization?.directUsePhaseEmissionsForSoldProductsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            directUsePhaseEmissionsForSoldProductsInfoList[i] = it
            i++
         }
      }
      return directUsePhaseEmissionsForSoldProductsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def DirectUsePhaseEmissionsForSoldProductsInfo[] getDirectUsePhaseEmissionsForSoldProductsInfos(Map<String, String> parameters) {
      //DirectUsePhaseEmissionsForSoldProductsInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.directUsePhaseEmissionsForSoldProductsInfoList
      //DirectUsePhaseEmissionsForSoldProductsInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def DirectUsePhaseEmissionsForSoldProductsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      DirectUsePhaseEmissionsForSoldProductsInfo theDirectUsePhaseEmissionsForSoldProductsInfo = DirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased DirectUsePhaseEmissionsForSoldProducts"
      String emissionsType = "DirectUsePhaseEmissionsForSoldProducts Emission" //Method

      def emissions =[:]
      
      if (!theDirectUsePhaseEmissionsForSoldProductsInfo) {
        println "theDirectUsePhaseEmissionsForSoldProductsInfo is Null"
        theDirectUsePhaseEmissionsForSoldProductsInfo = new DirectUsePhaseEmissionsForSoldProductsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theDirectUsePhaseEmissionsForSoldProductsInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theDirectUsePhaseEmissionsForSoldProductsInfo is Not Null"
        theDirectUsePhaseEmissionsForSoldProductsInfo.emissionsDetailsList.each{
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
            
      theDirectUsePhaseEmissionsForSoldProductsInfo.dataBeginDate = dataBeginDate
      theDirectUsePhaseEmissionsForSoldProductsInfo.dataEndDate = dataEndDate   
      
      theDirectUsePhaseEmissionsForSoldProductsInfo.sourceDescription=parameters.sourceDescription
      theDirectUsePhaseEmissionsForSoldProductsInfo.productType=parameters.productType      
      theDirectUsePhaseEmissionsForSoldProductsInfo.productName=parameters.productName
      theDirectUsePhaseEmissionsForSoldProductsInfo.numberSoldInReportingPeriod=parameters.numberSoldInReportingPeriod?.toDouble()
        
      switch (parameters.methodType){
        case ["Lifetime Uses Method"] :
               
              theDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeExpectedUsesOfProduct=parameters.totalLifetimeExpectedUsesOfProduct?.toDouble()
            
              theDirectUsePhaseEmissionsForSoldProductsInfo.fuelType=parameters.fuelType              
              theDirectUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUse=parameters.fuelConsumedPerUse?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUse_Unit=parameters.fuelConsumedPerUse_Unit      
              theDirectUsePhaseEmissionsForSoldProductsInfo.EF_Fuel=parameters.EF_Fuel?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.EF_Fuel_Unit=parameters.EF_Fuel_Unit

              theDirectUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUse=parameters.electricityConsumedPerUse?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUse_Unit=parameters.electricityConsumedPerUse_Unit
              theDirectUsePhaseEmissionsForSoldProductsInfo.EF_Electricity=parameters.EF_Electricity?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.EF_Electricity_Unit=parameters.EF_Electricity_Unit

              theDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantType=parameters.refrigerantType              
              theDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUse=parameters.refrigerantLeakagePerUse?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUse_Unit=parameters.refrigerantLeakagePerUse_Unit      
              theDirectUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant=parameters.EF_Refrigerant?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant_Unit=parameters.EF_Refrigerant_Unit

         break
         
        case ["Combustion Method"] :                
              theDirectUsePhaseEmissionsForSoldProductsInfo.totalQuantityOfFuelOrFeedstockSold=parameters.totalQuantityOfFuelOrFeedstockSold?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.totalQuantityOfFuelOrfeedstockSold_Unit=parameters.totalQuantityOfFuelOrfeedstockSold_Unit      
              theDirectUsePhaseEmissionsForSoldProductsInfo.combustion_EF_ForFuelOrFeedstock=parameters.combustion_EF_ForFuelOrFeedstock?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.combustion_EF_ForFuelOrFeedstock_Unit=parameters.combustion_EF_ForFuelOrFeedstock_Unit
              
         break
            
        case ["GHG released Method"] :                
              theDirectUsePhaseEmissionsForSoldProductsInfo.GHG_Name=parameters.GHG_Name
              theDirectUsePhaseEmissionsForSoldProductsInfo.GHG_PerProduct=parameters.GHG_PerProduct?.toDouble()
              theDirectUsePhaseEmissionsForSoldProductsInfo.percentOfGHGReleasedDuringLifetimeUseOfProduct=parameters.percentOfGHGReleasedDuringLifetimeUseOfProduct?.toDouble()      
              theDirectUsePhaseEmissionsForSoldProductsInfo.GWP_GHG=parameters.GWP_GHG?.toDouble()

              
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theDirectUsePhaseEmissionsForSoldProductsInfo.userNotesOnData=parameters.userNotesOnData
      theDirectUsePhaseEmissionsForSoldProductsInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theDirectUsePhaseEmissionsForSoldProductsInfo.organization = theOrganization
      
      //--Save the user reference
      theDirectUsePhaseEmissionsForSoldProductsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theDirectUsePhaseEmissionsForSoldProductsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theDirectUsePhaseEmissionsForSoldProductsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theDirectUsePhaseEmissionsForSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theDirectUsePhaseEmissionsForSoldProductsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //DirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)?.delete()
      def theDirectUsePhaseEmissionsForSoldProductsInfo = DirectUsePhaseEmissionsForSoldProductsInfo.get(parameters.id)

      if (theDirectUsePhaseEmissionsForSoldProductsInfo){
        delete(theDirectUsePhaseEmissionsForSoldProductsInfo)
      }
      else {
          println "No theDirectUsePhaseEmissionsForSoldProductsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theDirectUsePhaseEmissionsForSoldProductsInfo, delete) or hasPermission(#theDirectUsePhaseEmissionsForSoldProductsInfo, admin)")
   void delete(DirectUsePhaseEmissionsForSoldProductsInfo theDirectUsePhaseEmissionsForSoldProductsInfo) {
      theDirectUsePhaseEmissionsForSoldProductsInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theDirectUsePhaseEmissionsForSoldProductsInfo
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
                case ["Lifetime Uses Method"] :                                          
                     combinedEmissions=  parameters.totalLifetimeExpectedUsesOfProduct?.toDouble() * parameters.numberSoldInReportingPeriod?.toDouble() *
                                        (
                                          (parameters.fuelConsumedPerUse?.toDouble() * parameters.EF_Fuel?.toDouble() )+
                                          (parameters.electricityConsumedPerUse?.toDouble() * parameters.EF_Electricity?.toDouble() )+
                                          (parameters.refrigerantLeakagePerUse?.toDouble() * parameters.EF_Refrigerant?.toDouble() )                       
                                        )
                                                             
                     break

                case ["Combustion Method"] :                
                    combinedEmissions= parameters.totalQuantityOfFuelOrFeedstockSold?.toDouble() *
                                       parameters.combustion_EF_ForFuelOrFeedstock?.toDouble()
                
                     break

                case ["GHG released Method"] :                
                    combinedEmissions= parameters.numberSoldInReportingPeriod?.toDouble() *
                                       parameters.GHG_PerProduct?.toDouble() *
                                       parameters.percentOfGHGReleasedDuringLifetimeUseOfProduct?.toDouble() *
                                       parameters.GWP_GHG?.toDouble() 
                
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
