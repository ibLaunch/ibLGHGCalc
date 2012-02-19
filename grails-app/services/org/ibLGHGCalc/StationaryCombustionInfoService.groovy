package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class StationaryCombustionInfoService {

    static transactional = true
    //def stationaryCombustionInfoService
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    StationaryCombustionInfo findStationaryCombustionInfo(Long id) {
      StationaryCombustionInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def StationaryCombustionInfo[] findStationaryCombustionInfos(Map<String, String> parameters) {
      /*Commmented below to implement slection based on Inventory Year
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.stationaryCombustionInfoList
      */
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else if (parameters.id) {
           // User has provided the id of the Stationary comubstion source, so just provide that and return from here.
           return StationaryCombustionInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in StationaryCombustionInfoService.findStationaryCombustionInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def stationaryCombustionInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the stationary combustion sources which are within the inventory year
      int i = 0
      theOrganization.stationaryCombustionInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate)) {
            stationaryCombustionInfoList[i] = it
            i++
         }
      }
      return stationaryCombustionInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def StationaryCombustionInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      //- Getting the emission Factors
      def theEF_StationaryCombustion_EPA =  EF_StationaryCombustion_EPA.findByFuelType(parameters.fuelType)

      //-Convert parameters  fuelQuantity to Double
      Double  fuelQty = (parameters.fuelQuantity).toDouble()
      //-Define emissions object
      //def theEmissionsDetails
      def theStationaryCombustionInfo = StationaryCombustionInfo.get(parameters.id)

      //--Implement proper code for programType-??
      //String programType = parameters.programType
      String programType = "US EPA"
      String emissionsType = "Stationary Combustion"

      //-define emissions map
      def Map<String,String> emissions
      
      if (!theStationaryCombustionInfo) {
        println "theStationaryCombustionInfo is Null"
        theStationaryCombustionInfo = new StationaryCombustionInfo()
        def theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters.fuelType, fuelQty, programType, emissionsType)

        //set the emissions details
        theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions").toDouble()
        theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions").toDouble()
        theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions").toDouble()
        theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions").toDouble()
        theEmissionsDetails.CO2EmissionsUnit = emissions.get("CO2EmissionsUnit")
        theEmissionsDetails.biomassCO2EmissionsUnit = emissions.get("biomassCO2EmissionsUnit")
        theEmissionsDetails.CH4EmissionsUnit = emissions.get("CH4EmissionsUnit")
        theEmissionsDetails.N2OEmissionsUnit = emissions.get("N2OEmissionsUnit")
        theEmissionsDetails.emissionsType = emissions.get("emissionsType")
        theEmissionsDetails.programType = emissions.get("programType")
        
        /*
        theEmissionsDetails = new EmissionsDetails(
              CO2Emissions: fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg,
              CH4Emissions: fuelQty*theEF_StationaryCombustion_EPA.CH4MultiplierInGram,
              N2OEmissions: fuelQty*theEF_StationaryCombustion_EPA.N2OMultiplierInGram,
              CO2EMissionsUnit:"Kg",
              CH4EMissionsUnit:"Gram",
              N2OEMissionsUnit:"Gram",
              programType:"US EPA",
              emissionsType:"Stationary Combustion"
        )
        */
        if (theEmissionsDetails) println "EMissions Details is Not Empty"
        theStationaryCombustionInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "theStationaryCombustionInfo is Not Null"
        theStationaryCombustionInfo.emissionsDetailsList.each{
          def theEmissionsDetails = it
          println "calling calculateEmissions"
          emissions = calculateEmissions(parameters.fuelType, fuelQty, programType, emissionsType)
          println "called calculateEmissions"
          //--update the emissionsDetails
          theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions").toDouble()
          theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions").toDouble()
          theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions").toDouble()
          theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions").toDouble()
          theEmissionsDetails.CO2EmissionsUnit = emissions.get("CO2EmissionsUnit")
          theEmissionsDetails.biomassCO2EmissionsUnit = emissions.get("biomassCO2EmissionsUnit")
          theEmissionsDetails.CH4EmissionsUnit = emissions.get("CH4EmissionsUnit")
          theEmissionsDetails.N2OEmissionsUnit = emissions.get("N2OEmissionsUnit")
          theEmissionsDetails.emissionsType = emissions.get("emissionsType")
          theEmissionsDetails.programType = emissions.get("programType")

          /*
          theEmissionsDetails = it
          //--Implement proper code for programType-??
          if (theEmissionsDetails.programType.equals("US EPA")){
                theEmissionsDetails.CO2Emissions = fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg
                theEmissionsDetails.CH4Emissions = fuelQty*theEF_StationaryCombustion_EPA.CH4MultiplierInGram
                theEmissionsDetails.N2OEmissions = fuelQty*theEF_StationaryCombustion_EPA.N2OMultiplierInGram
          }
          */
        }
      }

      //Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedBeginDate)
      //Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedEndDate)
      Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedBeginDate)
      Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedEndDate)
      //Date fuelUsedBeginDate = fuelUsedBeginDateTemp.format("yyyy-MM-dd")
      //Date fuelUsedEndDate = fuelUsedEndDateTemp.format("yyyy-MM-dd")
      //Date fuelUsedBeginDate = new Date( new Date().parse("yyyy-MM-dd", parameters.fuelUsedBeginDate).format("yyyy-MM-dd"))
      //Date fuelUsedEndDate = new Date( new Date().parse("yyyy-MM-dd", parameters.fuelUsedEndDate).format("yyyy-MM-dd"))

      println "fuelUsedBeginDate : " + fuelUsedBeginDate
      println "fuelUsedEndDate : " + fuelUsedEndDate

      //theStationaryCombustionInfo.properties = parameters;
      theStationaryCombustionInfo.fuelQuantity = fuelQty
      theStationaryCombustionInfo.fuelSourceDescription = parameters.fuelSourceDescription
      theStationaryCombustionInfo.fuelType = parameters.fuelType
      theStationaryCombustionInfo.fuelUnit = parameters.fuelUnit
      theStationaryCombustionInfo.fuelUsedBeginDate = fuelUsedBeginDate
      theStationaryCombustionInfo.fuelUsedEndDate = fuelUsedEndDate
      //theStationaryCombustionInfo.isPublic = parameters.isPublic

      theStationaryCombustionInfo.isPublic =
         "true".equals(parameters.isPublic) ? true : false

      //println "theEmissionsDetails :----:  " +   theEmissionsDetails
      println "theStationaryCombustionInfo :----:  " +   theStationaryCombustionInfo

      def theOrganization =  Organization.get(parameters.organizationId)
      //theOrganization.addToStationaryCombustionInfoList(theStationaryCombustionInfo)
      println "The Organization : " + theOrganization

      theStationaryCombustionInfo.organization = theOrganization

      //--Save the user reference
      theStationaryCombustionInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser
      //--Save the data origin
      theStationaryCombustionInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theStationaryCombustionInfo.save(flush:true)

      //--Save the permissions on this object
      aclUtilService.addPermission(theStationaryCombustionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theStationaryCombustionInfo
      //theOrganization.save()
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      def theStationaryCombustionInfo = StationaryCombustionInfo.get(parameters.id)

      if (theStationaryCombustionInfo){
        delete(theStationaryCombustionInfo)
      }
      else {
          println "No theStationaryCombustionInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#stationaryCombustionInfo, delete) or hasPermission(#stationaryCombustionInfo, admin)")
   void delete(StationaryCombustionInfo stationaryCombustionInfo) {
      stationaryCombustionInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl stationaryCombustionInfo
   }

/*
  //--remove the method below
    def EmissionsDetails updateEmissionsDetails(EmissionsDetails theEmissionsDetails,
                                                                           String fuelType, Double fuelQty){
        if ((theEmissionsDetails.programType.equals("US EPA")) &&
                (theEmissionsDetails.emissionsType.equals("Stationary Combustion")) ){
            def theEF_StationaryCombustion_EPA = EF_StationaryCombustion_EPA.findByFuelType(parameters.fuelType)
            theEmissionsDetails.CO2Emissions = fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg
            theEmissionsDetails.CH4Emissions = fuelQty*theEF_StationaryCombustion_EPA.CH4MultiplierInGram
            theEmissionsDetails.N2OEmissions = fuelQty*theEF_StationaryCombustion_EPA.N2OMultiplierInGram
        }
        //return theEmissionsDetails
    }
*/
    def Map<String,String> calculateEmissions(String fuelType, Double fuelQty, String programType, String emissionsType){

        def emissions = [:]
        def Double CO2Emissions
        def Double biomassCO2Emissions
        def Double CH4Emissions
        def Double N2OEmissions

        if ( (programType.equals("US EPA")) &&
             (emissionsType.equals("Stationary Combustion")) ) {
             //- Get the emission factor object
             def theEF_StationaryCombustion_EPA = EF_StationaryCombustion_EPA.findByFuelType(fuelType)

            if (fuelType.equals("Wood and Wood Waste") || fuelType.equals("Landfill Gas (50%CH4, 50%CO2)")) {
                //--CO2EMissions =0, calculate biomassCO2Emissions
                CO2Emissions = 0
                biomassCO2Emissions = fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg
             }
             else {
                //--biomassCO2EMissions = 0
                CO2Emissions = fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg
                biomassCO2Emissions = 0
             }

             CH4Emissions= fuelQty*theEF_StationaryCombustion_EPA.CH4MultiplierInGram
             N2OEmissions= fuelQty*theEF_StationaryCombustion_EPA.N2OMultiplierInGram

             emissions.put("CO2Emissions", CO2Emissions)
             emissions.put("biomassCO2Emissions", biomassCO2Emissions)
             emissions.put("CH4Emissions", CH4Emissions)
             emissions.put("N2OEmissions", N2OEmissions)
             emissions.put("CO2EmissionsUnit", "Kg")
             emissions.put("biomassCO2EmissionsUnit", "Kg")
             emissions.put("CH4EmissionsUnit", "gram")
             emissions.put("N2OEmissionsUnit", "gram")
             emissions.put("emissionsType", emissionsType)
             emissions.put("programType", programType)

             return emissions
        }
	
    }

}