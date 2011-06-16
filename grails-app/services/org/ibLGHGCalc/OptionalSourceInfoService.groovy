package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class OptionalSourceInfoService {

    static transactional = true
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    OptionalSourceInfo findOptionalSourceInfo(Long id) {
      OptionalSourceInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def OptionalSourceInfo[] findOptionalSourceInfos(Map<String, String> parameters) {
      /* Commented below
      def theOrganization = Organization.get(parameters.organizationId)
      //return theOrganization.optionalSourceInfoList
      //OptionalSourceInfo.findAllByOrganization(theOrganization)

      def OptionalSourceInfo[] optionalSourceInfoList
      optionalSourceInfoList = OptionalSourceInfo.findAllByOrganizationAndOptionalSourceType(theOrganization,parameters.optionalSourceType)
      return optionalSourceInfoList
      */
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return OptionalSourceInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in optionalSourceInfoService.findOptionalSourceInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def optionalSourceInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the optional sources which are within the inventory year
      int i = 0
      theOrganization.optionalSourceInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate) && (it.optionalSourceType.equals(parameters.optionalSourceType))) {
            optionalSourceInfoList[i] = it
            i++
         }
      }
      return optionalSourceInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def OptionalSourceInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      def theOptionalSourceInfo = OptionalSourceInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "EPA Climate Leaders"
      String emissionsType = parameters.optionalSourceType

      //-create parameters map to send to calcualteEmissions()
      def calculateEmissionParameters = [:]
      calculateEmissionParameters.put("vehicleType", parameters.vehicleType)
      calculateEmissionParameters.put("railType", parameters.railType)
      calculateEmissionParameters.put("busType", parameters.busType)
      calculateEmissionParameters.put("airTravelType", parameters.airTravelType)
      calculateEmissionParameters.put("transportType", parameters.transportType)
      if (parameters.passengerMiles){calculateEmissionParameters.put("passengerMiles",  parameters.passengerMiles.toDouble())}
      if (parameters.tonMiles){calculateEmissionParameters.put("tonMiles",  parameters.tonMiles.toDouble())}

/*
	//-create parameters map to send to calcualteEmissions()
	def calculateEmissionParameters = [:]
	calculateEmissionParameters.put("fuelType",parameters.fuelType)
	calculateEmissionParameters.put("boilerEfficiencyPercent", parameters.boilerEfficiencyPercent.toDouble())
	calculateEmissionParameters.put("optionalSource", parameters.optionalSource.toDouble())
	calculateEmissionParameters.put("optionalSourceUnit", parameters.optionalSourceUnit)
	//--All the supplier provided emission factors are suppose to have some value or zero
	calculateEmissionParameters.put("supplierCO2Multiplier", parameters.supplierCO2Multiplier.toDouble())
	calculateEmissionParameters.put("supplierCH4Multiplier", parameters.supplierCH4Multiplier.toDouble())
	calculateEmissionParameters.put("supplierN2OMultiplier", parameters.supplierN2OMultiplier.toDouble())
	calculateEmissionParameters.put("supplierCO2MultiplierUnit", parameters.supplierCO2MultiplierUnit)
	calculateEmissionParameters.put("supplierCH4MultiplierUnit", parameters.supplierCH4MultiplierUnit)
	calculateEmissionParameters.put("supplierN2OMultiplierUnit", parameters.supplierN2OMultiplierUnit)
*/
      //-define emissions map
      def Map<String,String> emissions

      if (!theOptionalSourceInfo) {
        println "theOptionalSourceInfo is Null"
        theOptionalSourceInfo = new OptionalSourceInfo()
        def theEmissionsDetails = new EmissionsDetails()

        emissions = calculateEmissions(calculateEmissionParameters, programType, emissionsType)

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

        if (theEmissionsDetails) println "Emissions Details is Not Empty"
        theOptionalSourceInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "theOptionalSourceInfo is Not Null"
        theOptionalSourceInfo.emissionsDetailsList.each{
          def theEmissionsDetails = it
          println "calling calculateEmissions"
          emissions = calculateEmissions(calculateEmissionParameters, programType, emissionsType)
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
        }
      }

      //Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedBeginDate)
      //Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedEndDate)
      Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedBeginDate)
      Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedEndDate)

      println "fuelUsedBeginDate : " + fuelUsedBeginDate
      println "fuelUsedEndDate : " + fuelUsedEndDate

      //It is expected that Double parameters will have values sent by the client
      theOptionalSourceInfo.optionalSourceType = parameters.optionalSourceType
      theOptionalSourceInfo.sourceDescription = parameters.sourceDescription
      theOptionalSourceInfo.vehicleType = parameters.vehicleType
      theOptionalSourceInfo.railType = parameters.railType
      theOptionalSourceInfo.busType = parameters.busType
      theOptionalSourceInfo.airTravelType = parameters.airTravelType
      theOptionalSourceInfo.transportType = parameters.transportType

      if(parameters.passengerMiles) { theOptionalSourceInfo.passengerMiles = parameters.passengerMiles.toDouble() }
      if(parameters.tonMiles) { theOptionalSourceInfo.tonMiles = parameters.tonMiles.toDouble() }

      theOptionalSourceInfo.fuelUsedBeginDate = fuelUsedBeginDate
      theOptionalSourceInfo.fuelUsedEndDate = fuelUsedEndDate

      println "theOptionalSourceInfo :----:  " +   theOptionalSourceInfo

      def theOrganization =  Organization.get(parameters.organizationId)
      println "The Organization : " + theOrganization

      theOptionalSourceInfo.organization = theOrganization

      //--Save the user reference
      theOptionalSourceInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser
      //--Save the data origin
      theOptionalSourceInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theOptionalSourceInfo.save(flush:true)
      //--Save the permissions on this object
      aclUtilService.addPermission(theOptionalSourceInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theOptionalSourceInfo

      //theOrganization.save()
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //OptionalSourceInfo.get(parameters.id)?.delete()
      def theOptionalSourceInfo = OptionalSourceInfo.get(parameters.id)

      if (theOptionalSourceInfo){
        delete(theOptionalSourceInfo)
      }
      else {
          println "No theOptionalSourceInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theOptionalSourceInfo, delete) or hasPermission(#theOptionalSourceInfo, admin)")
   void delete(OptionalSourceInfo theOptionalSourceInfo) {
      theOptionalSourceInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theOptionalSourceInfo
   }

    def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        def Double CO2Emissions
        def Double biomassCO2Emissions
        def Double CH4Emissions
        def Double N2OEmissions

        def theEF_OptionalSource_EPA

        if (programType.equals("EPA Climate Leaders"))
             switch (emissionsType) {
                case ["Employee Business Travel - By Vehicle","Employee Commuting - By Vehicle"] :
                    //- Get the emission factor object
                     theEF_OptionalSource_EPA = EF_VehicleType_EPA.findByVehicleType(parameters.vehicleType)

                     CO2Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CO2Multiplier
                     CH4Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CH4Multiplier
                     N2OEmissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.N2OMultiplier
                     break

                case"Employee Business Travel - By Air" :
                    //- Get the emission factor object
                     theEF_OptionalSource_EPA = EF_AirTravelType_EPA.findByAirTravelType(parameters.airTravelType)

                     CO2Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CO2Multiplier
                     CH4Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CH4Multiplier
                     N2OEmissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.N2OMultiplier
                     break

                case ["Employee Business Travel - By Bus","Employee Commuting - By Bus"] :
                    //- Get the emission factor object
                     theEF_OptionalSource_EPA = EF_BusType_EPA.findByBusType(parameters.busType)

                     CO2Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CO2Multiplier
                     CH4Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CH4Multiplier
                     N2OEmissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.N2OMultiplier
                     break

                case ["Employee Business Travel - By Rail","Employee Commuting - By Rail"] :
                    //- Get the emission factor object
                     theEF_OptionalSource_EPA = EF_RailType_EPA.findByRailType(parameters.railType)

                     CO2Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CO2Multiplier
                     CH4Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CH4Multiplier
                     N2OEmissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.N2OMultiplier
                     break
                 
                case ["Product Transport - By Vehicle"]:
                    //- Get the emission factor object
                     theEF_OptionalSource_EPA = EF_ProductTransport_VehicleType_EPA.findByVehicleType(parameters.vehicleType)

                     CO2Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CO2Multiplier
                     CH4Emissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.CH4Multiplier
                     N2OEmissions = parameters.passengerMiles.toDouble()*theEF_OptionalSource_EPA.N2OMultiplier
                     break

                case ["Product Transport - By Heavy Duty Trucks", "Product Transport - By Rail", "Product Transport - By Water or Air"] :
                    //- Get the emission factor object
                    /*
                     String transportType
                     if (emissionsType.equals("Product Transport - By Heavy Duty Trucks")) {transportType="On-Road Product Transport"}
                     else if (emissionsType.equals("Product Transport - By Rail")) {transportType="Rail Product Transport"}
                     else if (emissionsType.contains("Product Transport - By Water")) {transportType="Waterborne Craft"}
                     else if (emissionsType.equals("Product Transport - By Air")) {transportType="Aircraft"}
                     */
                     theEF_OptionalSource_EPA = EF_ProductTransportType_EPA.findByTransportType(parameters.transportType)

                     CO2Emissions = parameters.tonMiles.toDouble()*theEF_OptionalSource_EPA.CO2Multiplier
                     CH4Emissions = parameters.tonMiles.toDouble()*theEF_OptionalSource_EPA.CH4Multiplier
                     N2OEmissions = parameters.tonMiles.toDouble()*theEF_OptionalSource_EPA.N2OMultiplier
                     break

               default: 
                     println "No swich Match in OptionalSourceInfoService.."

             }
             //-- Make decision about Units of emissions here in future. For now it is hard coded. ??
             
             //--biomass CO2 is zero by default
             biomassCO2Emissions = 0
             emissions.put("CO2Emissions", CO2Emissions)
             emissions.put("biomassCO2Emissions", biomassCO2Emissions)
             emissions.put("CH4Emissions", CH4Emissions)
             emissions.put("N2OEmissions", N2OEmissions)
             emissions.put("CO2EmissionsUnit", 'Kg')
             //emissions.put("biomassCO2EmissionsUnit", theEF_OptionalSource_EPA.CO2MultiplierUnit)
             emissions.put("biomassCO2EmissionsUnit", '')
             emissions.put("CH4EmissionsUnit", 'gram')
             emissions.put("N2OEmissionsUnit", 'gram')
             emissions.put("emissionsType", emissionsType)
             emissions.put("programType", programType)

             return emissions
    }
}