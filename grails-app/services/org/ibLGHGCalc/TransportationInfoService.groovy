package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class TransportationInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    TransportationInfo findTransportationInfo(Long id) {
      TransportationInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def TransportationInfo[] findTransportationInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.transportationInfoList
      //TransportationInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return TransportationInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in transportationInfoListService.findtransportationInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def transportationInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Transportation sources which are within the inventory year
      int i = 0
      theOrganization?.transportationInfoList?.each {
         //---using streamType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            transportationInfoList[i] = it
            i++
         }
      }
      return transportationInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def TransportationInfo[] getTransportationInfos(Map<String, String> parameters) {
      //TransportationInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.transportationInfoList
      //TransportationInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def TransportationInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      TransportationInfo theTransportationInfo = TransportationInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Transportation"
      String emissionsType = parameters.streamType + " Transportation Emission" //Method

      def emissions =[:]

      
      if (!theTransportationInfo) {
        println "theTransportationInfo is Null"
        theTransportationInfo = new TransportationInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theTransportationInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theTransportationInfo is Not Null"
        theTransportationInfo.emissionsDetailsList.each{
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
            
      theTransportationInfo.dataBeginDate = dataBeginDate
      theTransportationInfo.dataEndDate = dataEndDate
      
      theTransportationInfo.sourceDescription=parameters.sourceDescription      
      theTransportationInfo.streamType=parameters.streamType
      theTransportationInfo.serviceProviderName=parameters.serviceProviderName
      theTransportationInfo.serviceProviderContact=parameters.serviceProviderContact
              
      switch (parameters.methodType){
        case ["Fuel Based Method - Transportation Emissions"] :
              theTransportationInfo.fuelDataType=parameters.fuelDataType      
              theTransportationInfo.fuelType=parameters.fuelType      
              theTransportationInfo.fuelConsumed=parameters.fuelConsumed?.toDouble()
              theTransportationInfo.fuelConsumed_Unit=parameters.fuelConsumed_Unit      
              theTransportationInfo.EF_Fuel=parameters.EF_Fuel?.toDouble()
              theTransportationInfo.EF_Fuel_Unit=parameters.EF_Fuel_Unit      

              theTransportationInfo.refrigerantType=parameters.refrigerantType      
              theTransportationInfo.refrigerantLeakage=parameters.refrigerantLeakage?.toDouble()
              theTransportationInfo.refrigerantLeakage_Unit=parameters.refrigerantLeakage_Unit      
              theTransportationInfo.EF_Refrigerant=parameters.EF_Refrigerant?.toDouble()
              theTransportationInfo.EF_Refrigerant_Unit=parameters.EF_Refrigerant_Unit      

              //theTransportationInfo.flag_calculateFuelConsumed=parameters.flag_calculateFuelConsumed.equals("true")? true: false
              theTransportationInfo.totalDistanceTravelled=parameters.totalDistanceTravelled?.toDouble()
              theTransportationInfo.totalDistanceTravelled_Unit=parameters.totalDistanceTravelled_Unit      
              theTransportationInfo.vehicleType=parameters.vehicleType
              theTransportationInfo.vehicleName=parameters.vehicleName
              theTransportationInfo.fuelEfficiencyOfVehicle=parameters.fuelEfficiencyOfVehicle?.toDouble()
              theTransportationInfo.fuelEfficiencyOfVehicle_Unit=parameters.fuelEfficiencyOfVehicle_Unit      

              //theTransportationInfo.flag_calculateAllocatedFuelUse=parameters.flag_calculateAllocatedFuelUse.equals("true")? true: false
              theTransportationInfo.allocatedFuelUse=parameters.allocatedFuelUse?.toDouble()
              theTransportationInfo.allocatedFuelUse_Unit=parameters.allocatedFuelUse_Unit
              theTransportationInfo.totalFuelConsumed=parameters.totalFuelConsumed?.toDouble()
              theTransportationInfo.totalFuelConsumed_Unit=parameters.totalFuelConsumed_Unit
              
              //theTransportationInfo.companyGoodsTransported = parameters.companyGoodsTransported
              theTransportationInfo.companyGoodsTransported=parameters.companyGoodsTransported?.toDouble()
              theTransportationInfo.companyGoodsTransported_Unit=parameters.companyGoodsTransported_Unit
              theTransportationInfo.companyGoodsTransported_MassType=parameters.companyGoodsTransported_MassType
              theTransportationInfo.totalGoodsTransportedByCarrier=parameters.totalGoodsTransportedByCarrier?.toDouble()
              theTransportationInfo.totalGoodsTransportedByCarrier_Unit=parameters.totalGoodsTransportedByCarrier_Unit
              theTransportationInfo.totalGoodsTransportedByCarrier_MassType=parameters.totalGoodsTransportedByCarrier_MassType
              
              //theTransportationInfo.massType=parameters.massType
              theTransportationInfo.flag_unladenBackhaul=parameters.flag_unladenBackhaul.equals("true")? true: false
         break
        case ["Distance Based Method - Transportation Emissions"] :                
              theTransportationInfo.transportModeOrVehicleType=parameters.transportModeOrVehicleType
              theTransportationInfo.massOfGoodsPurchased=parameters.massOfGoodsPurchased?.toDouble()
              theTransportationInfo.massOfGoodsPurchased_Unit=parameters.massOfGoodsPurchased_Unit
              theTransportationInfo.distanceTraveledInTransportLeg=parameters.distanceTraveledInTransportLeg?.toDouble()
              theTransportationInfo.distanceTraveledInTransportLeg_Unit=parameters.distanceTraveledInTransportLeg_Unit
              theTransportationInfo.EF_TransportModeOrVehicleType=parameters.EF_TransportModeOrVehicleType?.toDouble()
              theTransportationInfo.EF_TransportModeOrVehicleType_Unit=parameters.EF_TransportModeOrVehicleType_Unit

         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
                
      theTransportationInfo.userNotesOnData=parameters.userNotesOnData
      theTransportationInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theTransportationInfo.organization = theOrganization
      
      //--Save the user reference
      theTransportationInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theTransportationInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theTransportationInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theTransportationInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theTransportationInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //TransportationInfo.get(parameters.id)?.delete()
      def theTransportationInfo = TransportationInfo.get(parameters.id)

      if (theTransportationInfo){
        delete(theTransportationInfo)
      }
      else {
          println "No theTransportationInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theTransportationInfo, delete) or hasPermission(#theTransportationInfo, admin)")
   void delete(TransportationInfo theTransportationInfo) {
      theTransportationInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theTransportationInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0
        println "I am in..............calculateEmissions"
        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if (programType.equals("US EPA")) {
            println "I am in..............if program type"
            if (parameters.methodType.equals("Fuel Based Method - Transportation Emissions")){                 
                switch(parameters.fuelDataType){
                    case ["Fuel Consumed"]:
                            if ((parameters.fuelConsumed?.toDouble()> 0) && (parameters.EF_Fuel?.toDouble()>0) ) {
                                    combinedEmissions= parameters.fuelConsumed?.toDouble() * parameters.EF_Fuel?.toDouble()                                           
                            }
                            break			
                    case ["Distance/Vehicle Used"]:
                            if ( (parameters.totalDistanceTravelled?.toDouble() >0 ) &&(parameters.fuelEfficiencyOfVehicle?.toDouble()>0) && 
                                      (parameters.EF_Fuel?.toDouble() >0 )){

                                    combinedEmissions= parameters.totalDistanceTravelled?.toDouble() * 
                                                       parameters.fuelEfficiencyOfVehicle?.toDouble() *
                                                       parameters.EF_Fuel?.toDouble()
                            }

                            break			
                    case ["Calculate Allocated Fuel Used"]:
                            if ( (parameters.totalFuelConsumed?.toDouble() >0 ) &&(parameters.companyGoodsTransported?.toDouble()>0) && 
                                     (parameters.totalGoodsTransportedByCarrier?.toDouble() >0 ) &&(parameters.EF_Fuel?.toDouble() >0 )){

                                    combinedEmissions= parameters.totalFuelConsumed?.toDouble() * 
                                                       (parameters.companyGoodsTransported?.toDouble() / parameters.totalGoodsTransportedByCarrier?.toDouble()) *
                                                       parameters.EF_Fuel?.toDouble()
                            }						

                            break
                    default:
                            println "Invalid fuelDataType in TransportationInfoService"
                }
                if ((parameters.refrigerantLeakage?.toDouble()> 0) && (parameters.EF_Refrigerant?.toDouble()>0) && (parameters.flag_unladenBackhaul.equals("false")) ) {
                        combinedEmissions= combinedEmissions + (parameters.refrigerantLeakage?.toDouble() * parameters.EF_Refrigerant?.toDouble())                                          
                }
            } else if (parameters.methodType.equals("Distance Based Method - Transportation Emissions")){
                if ( (parameters.massOfGoodsPurchased?.toDouble() >0 ) &&(parameters.distanceTraveledInTransportLeg?.toDouble()>0) && 
                          (parameters.EF_TransportModeOrVehicleType?.toDouble() >0 )){

                        combinedEmissions= parameters.massOfGoodsPurchased?.toDouble() * 
                                           parameters.distanceTraveledInTransportLeg?.toDouble() *
                                           parameters.EF_TransportModeOrVehicleType?.toDouble()
                }                
            }
         }  // PorgramType if statement
         
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
