package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class BusinessTravelInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    BusinessTravelInfo findBusinessTravelInfo(Long id) {
      BusinessTravelInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def BusinessTravelInfo[] findBusinessTravelInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.businessTravelInfoList
      //BusinessTravelInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return BusinessTravelInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in businessTravelInfoListService.findbusinessTravelInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def businessTravelInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Distribution sources which are within the inventory year
      int i = 0
      theOrganization?.businessTravelInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate)&& (it.methodType.equals(parameters.methodType))) {
            businessTravelInfoList[i] = it
            i++
         }
      }
      return businessTravelInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def BusinessTravelInfo[] getBusinessTravelInfos(Map<String, String> parameters) {
      //BusinessTravelInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.businessTravelInfoList
      //BusinessTravelInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def BusinessTravelInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      BusinessTravelInfo theBusinessTravelInfo = BusinessTravelInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Distribution"
      String emissionsType = "Business Travel" //Method

      def emissions =[:]
      
      if (!theBusinessTravelInfo) {
        println "theBusinessTravelInfo is Null"
        theBusinessTravelInfo = new BusinessTravelInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theBusinessTravelInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theBusinessTravelInfo is Not Null"
        theBusinessTravelInfo.emissionsDetailsList.each{
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
            
      theBusinessTravelInfo.dataBeginDate = dataBeginDate
      theBusinessTravelInfo.dataEndDate = dataEndDate   
      
      theBusinessTravelInfo.sourceDescription=parameters.sourceDescription
      theBusinessTravelInfo.activityType=parameters.activityType

      
      switch (parameters.activityType){
        case ["Travel"] :    
             theBusinessTravelInfo.vehicleType=parameters.vehicleType
             theBusinessTravelInfo.distanceTravelledByVehicleType=parameters.distanceTravelledByVehicleType?.toDouble()
             theBusinessTravelInfo.distanceTravelledByVehicleType_Unit=parameters.distanceTravelledByVehicleType_Unit        
             theBusinessTravelInfo.EF_VehicleType=parameters.EF_VehicleType?.toDouble()
             theBusinessTravelInfo.EF_VehicleType_Unit=parameters.EF_VehicleType_Unit      
             
             break

        case ["Hotel"] :                
            theBusinessTravelInfo.annualNumberOfHotelNights=parameters.annualNumberOfHotelNights?.toDouble()   
            theBusinessTravelInfo.EF_Hotel=parameters.EF_Hotel?.toDouble()
            theBusinessTravelInfo.EF_Hotel_Unit=parameters.EF_Hotel_Unit
        
             break

        default:                 
             println "In-correct activityType for Info service"                        
      } // Switch                        
      

                      
      theBusinessTravelInfo.userNotesOnData=parameters.userNotesOnData
      theBusinessTravelInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theBusinessTravelInfo.organization = theOrganization
      
      //--Save the user reference
      theBusinessTravelInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theBusinessTravelInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theBusinessTravelInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theBusinessTravelInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theBusinessTravelInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //BusinessTravelInfo.get(parameters.id)?.delete()
      def theBusinessTravelInfo = BusinessTravelInfo.get(parameters.id)

      if (theBusinessTravelInfo){
        delete(theBusinessTravelInfo)
      }
      else {
          println "No theBusinessTravelInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theBusinessTravelInfo, delete) or hasPermission(#theBusinessTravelInfo, admin)")
   void delete(BusinessTravelInfo theBusinessTravelInfo) {
      theBusinessTravelInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theBusinessTravelInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0
        println "I am in..............calculateEmissions"
        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if (programType.equals("US EPA")) {
            println "I am in..............if program type"
            switch (parameters.activityType){
              case ["Travel"] :    
                 combinedEmissions=  parameters.distanceTravelledByVehicleType?.toDouble() *
                                    parameters.EF_VehicleType?.toDouble()                   

                 break
              case ["Hotel"] :                
                combinedEmissions= parameters.annualNumberOfHotelNights?.toDouble() *
                                   parameters.EF_Hotel?.toDouble() 

                 break
              default:                 
                 println "In-correct activityType for Info service"                        
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
