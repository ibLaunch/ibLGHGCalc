package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission


class EmissionsSummaryService {

    static transactional = true
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    EmissionsSummary findEmissionsSummary(Long id) {
      EmissionsSummary.get(id)
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def EmissionsSummary[] findEmissionsSummarys(Map<String, String> parameters) {
      //EmissionsSummary.list()
      //def theOrganization = Organization.get(parameters.organizationId)
      def theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      //EmissionsSummary.findAllByOrganization(theOrganization)
      //def theOrganization = Organization.get(1)
      return theOrganization.emissionsSummaryList      
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def EmissionsSummary save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters
      
      def theEmissionsSummary = EmissionsSummary.get(parameters.id)
      if (!theEmissionsSummary) {
        theEmissionsSummary = new EmissionsSummary()
      }
      theEmissionsSummary.properties = parameters;
      theEmissionsSummary.save()
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //EmissionsSummary.get(parameters.id)?.delete()
      def theEmissionsSummary = EmissionsSummary.get(parameters.id)

      if (theEmissionsSummary){
        delete(theEmissionsSummary)
      }
      else {
          println "No theEmissionsSummary found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theEmissionsSummary, delete) or hasPermission(#theEmissionsSummary, admin)")
   void delete(EmissionsSummary theEmissionsSummary) {
      theEmissionsSummary.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theEmissionsSummary
   }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def calculateEmissionsSummary(Map<String, String> parameters) {
      log.info "Calculate Emissions Sumary( ${parameters} )"
      println "parameters" + parameters
      println "parameters.emissionsBeginDate : " + parameters.emissionsBeginDate
      println "parameters.emissionsEndDate : " + parameters.emissionsEndDate
      println "parameters.programType : " + parameters.programType

      Date beginDate = new Date().parse('yyyy-MM-dd', parameters.emissionsBeginDate)
      Date endDate = new Date().parse('yyyy-MM-dd', parameters.emissionsEndDate)

      //String programType = parameters.programType.toString()
      //String programType = "EPA Climate Leaders"
      String programType = parameters.programType

      //def theOrganization = Organization.get(parameters.organizationId)
      //def theEmissionsSummary = EmissionsSummary.get(parameters.id)

      def  theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      
      def theEmissionsSummaryList = theOrganization.emissionsSummaryList
      def theEmissionsSummary

      if (theEmissionsSummaryList){
        for (i in theEmissionsSummaryList){
            if ((i.emissionsBeginDate == beginDate) && ( i.emissionsEndDate == endDate)) {
                theEmissionsSummary = i
                //Quit the loop
                break
            }
        }
      }

      if (!theEmissionsSummary){
          theEmissionsSummary = new EmissionsSummary()
          theEmissionsSummary.organization = theOrganization
          theEmissionsSummary.emissionsBeginDate = beginDate
          theEmissionsSummary.emissionsEndDate = endDate
      }

      def Double totalEmissions = 0
// ------------------- Calculate stationaryCombustionEmissions
//-- Temporary initialization of theEmissionsSummary.stationaryCombustionEmissions- remove this in futre ??
      theEmissionsSummary.stationaryCombustionEmissions = 0
      theEmissionsSummary.biomassStationaryCombustionEmissions = 0

//-- Se the proper programType
     theEmissionsSummary.programType=programType
     
//-- Get a list of theStationaryCombustionInfo
      def theStationaryCombustionInfoList = theOrganization.stationaryCombustionInfoList

//--Ierate through theStationaryCombustionInfoList and get theEmissionsDetails add it up
      def theStationaryCombustionInfo
      def theEmissionsDetailsList
      theStationaryCombustionInfoList.each{          
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theStationaryCombustionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for Stationary Combustion Info"
                if (it.programType.equals(programType)){
                   theEmissionsSummary.stationaryCombustionEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }                   
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

// ------------------- Calculate mobileSourceEmissions
//-- Temporary initialization of theEmissionsSummary.mobileSourceEmissions- remove this in futre ??
      theEmissionsSummary.mobileCombustionEmissions = 0
      theEmissionsSummary.biomassMobileCombustionEmissions = 0
//-- Get a list of theMobileCombustionInfoList
      def theMobileCombustionInfoList = theOrganization.mobileCombustionInfoList

//--Ierate through theMobileCombustionInfoList and get theEmissionsDetails add it up
      def theMobileCombustionInfo
      //def theEmissionsDetailsList
      theMobileCombustionInfoList.each{
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theMobileCombustionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for Mobile Combustion Info"
                if (it.programType.equals(programType)){
                   theEmissionsSummary.mobileCombustionEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassMobileCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

// ------------------- Calculate refridgerationAirConditioningEmissions & fireSuppressantEmissions
//-- Temporary initialization of theEmissionsSummary.refridgerationAirConditioningEmissions- remove this in futre ??
      theEmissionsSummary.refridgerationAirConditioningEmissions = 0
      theEmissionsSummary.fireSuppressantEmissions = 0


//-- Get a list of theRefridgerationAndAcEmissionsInfo
      def theRefridgerationAirConditioningInfoList = theOrganization.refridgerationAirConditioningInfoList

//--Ierate through theRefridgerationAndAcEmissionsInfoList and get theEmissionsDetails add it up
      def theRefridgerationAirConditioningInfo
      //def theEmissionsDetailsList
      theRefridgerationAirConditioningInfoList.each{
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theRefridgerationAirConditioningInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the Refridgeration Air Conditioning Info"
                if (it.programType.equals(programType)){
                   if (it.emissionsType.contains("Refridgeration")){
                       theEmissionsSummary.refridgerationAirConditioningEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.contains("Fire")){
                       theEmissionsSummary.fireSuppressantEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   }
                   //--check if biomass emissions is not null -- Below is not required since emissions related to refridgeration and AC
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassMobileCombustionEmissions += it.biomassCO2Emissions                   
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

       println "theEmissionsSummary.fireSuppressantEmissions : " + theEmissionsSummary.fireSuppressantEmissions

// ------------------- Calculate PurchasedElectricity Emissions
//-- Temporary initialization of theEmissionsSummary.purchasedElectricityEmissions- remove this in futre ??
      theEmissionsSummary.purchasedElectricityEmissions = 0

//-- Get a list of thePurchasedElectricityInfo
      def thePurchasedElectricityInfoList = theOrganization.purchasedElectricityInfoList

//--Ierate through thePurchasedElectricityInfoList and get theEmissionsDetails add it up
      def thePurchasedElectricityInfo
      //def theEmissionsDetailsList
      thePurchasedElectricityInfoList.each{
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in thePurchasedElectricityInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for Purchased Electricity Info"
                if (it.programType.equals(programType)){
                   theEmissionsSummary.purchasedElectricityEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null - Below is not required since it is about purchased electricity
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

       println "theEmissionsSummary.purchasedElectricityEmissions : " + theEmissionsSummary.purchasedElectricityEmissions

// ------------------- Calculate PurchasedSteam Emissions
//-- Temporary initialization of theEmissionsSummary.purchasedSteamEmissions- remove this in futre ??
      theEmissionsSummary.purchasedSteamEmissions = 0

//-- Get a list of thePurchasedSteamInfo
      def thePurchasedSteamInfoList = theOrganization.purchasedSteamInfoList

//--Ierate through thePurchasedSteamInfoList and get theEmissionsDetails add it up
      def thePurchasedSteamInfo
      //def theEmissionsDetailsList
      thePurchasedSteamInfoList.each{
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in thePurchasedSteamInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for Purchased Steam Info"
                if (it.programType.equals(programType)){
                   theEmissionsSummary.purchasedSteamEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null - Below is not required since it is about purchased Steam
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

       println "theEmissionsSummary.purchasedSteamEmissions : " + theEmissionsSummary.purchasedSteamEmissions

// ------------------- Calculate WasteStreamCombustion Emissions
//-- Temporary initialization of theEmissionsSummary.wasteStreamCombustionEmissions- remove this in futre ??
      theEmissionsSummary.wasteStreamCombustionEmissions = 0

//-- Get a list of theWasteStreamCombustionInfo
      def theWasteStreamCombustionInfoList = theOrganization.wasteStreamCombustionInfoList

//--Ierate through theWasteStreamCombustionInfoList and get theEmissionsDetails add it up
      def theWasteStreamCombustionInfo
      //def theEmissionsDetailsList
      theWasteStreamCombustionInfoList.each{
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theWasteStreamCombustionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for Waste Stream Combustion Info"
                if (it.programType.equals(programType)){
                   theEmissionsSummary.wasteStreamCombustionEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null - Below is not required since it is about WasteStreamCombustion
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

       println "theEmissionsSummary.wasteStreamCombustionEmissions : " + theEmissionsSummary.wasteStreamCombustionEmissions

// ------------------- Calculate Optional Source Info Emissions
//-- Temporary initialization of theEmissionsSummary.employeeBusinessTravel Emissions- remove this in futre ??
      theEmissionsSummary.employeeBusinessTravelByVehicleEmissions = 0
      theEmissionsSummary.employeeBusinessTravelByRailEmissions = 0
      theEmissionsSummary.employeeBusinessTravelByBusEmissions = 0
      theEmissionsSummary.employeeBusinessTravelByAirEmissions = 0
      theEmissionsSummary.employeeCommutingByVehicleEmissions = 0
      theEmissionsSummary.employeeCommutingByRailEmissions = 0
      theEmissionsSummary.employeeCommutingByBusEmissions = 0
      theEmissionsSummary.productTransportByVehicleEmissions = 0
      theEmissionsSummary.productTransportByHeavyDutyTrucksEmissions = 0
      theEmissionsSummary.productTransportByRailEmissions = 0
      theEmissionsSummary.productTransportByWaterAirEmissions = 0

//-- Get a list of the Optional Source Info EmissionsInfo
      def theOptionalSourceInfoList = theOrganization.optionalSourceInfoList

//--Ierate through optionalSourceInfoList and get theEmissionsDetails add it up
      def theOptionalSourceInfo
      //def theEmissionsDetailsList
      theOptionalSourceInfoList.each{
          if((  it.fuelUsedBeginDate >= beginDate) && ( it.fuelUsedEndDate <= endDate ))
          {
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theOptionalSourceInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the optional Source Info"
                if (it.programType.equals(programType)){
                   if (it.emissionsType.equals("Employee Business Travel - By Vehicle")){
                       theEmissionsSummary.employeeBusinessTravelByVehicleEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - By Rail")){
                       theEmissionsSummary.employeeBusinessTravelByRailEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - By Bus")){
                       theEmissionsSummary.employeeBusinessTravelByBusEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - By Air")){
                       theEmissionsSummary.employeeBusinessTravelByAirEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Commuting - By Vehicle")){
                       theEmissionsSummary.employeeCommutingByVehicleEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Commuting - By Rail")){
                       theEmissionsSummary.employeeCommutingByRailEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Commuting - By Bus")){
                       theEmissionsSummary.employeeCommutingByBusEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Vehicle")){
                       theEmissionsSummary.productTransportByVehicleEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Heavy Duty Trucks")){
                       theEmissionsSummary.productTransportByHeavyDutyTrucksEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Rail")){
                       theEmissionsSummary.productTransportByRailEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Water or Air")){
                       theEmissionsSummary.productTransportByWaterAirEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   }
                   //--check if biomass emissions is not null --
                   else if (it.biomassCO2Emissions != 0) {
                        theEmissionsSummary.biomassMobileCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

       println "theEmissionsSummary.productTransportByWaterAirEmissions : " + theEmissionsSummary.productTransportByWaterAirEmissions

//- total emissions
       theEmissionsSummary.totalEmissions = theEmissionsSummary.stationaryCombustionEmissions +
                                            theEmissionsSummary.mobileCombustionEmissions +
                                            theEmissionsSummary.refridgerationAirConditioningEmissions +
                                            theEmissionsSummary.fireSuppressantEmissions +
                                            theEmissionsSummary.purchasedElectricityEmissions +
                                            theEmissionsSummary.purchasedSteamEmissions +
                                            theEmissionsSummary.wasteStreamCombustionEmissions +
                                            theEmissionsSummary.employeeBusinessTravelByVehicleEmissions+
                                            theEmissionsSummary.employeeBusinessTravelByRailEmissions +
                                            theEmissionsSummary.employeeBusinessTravelByBusEmissions +
                                            theEmissionsSummary.employeeBusinessTravelByAirEmissions +
                                            theEmissionsSummary.employeeCommutingByVehicleEmissions +
                                            theEmissionsSummary.employeeCommutingByRailEmissions +
                                            theEmissionsSummary.employeeCommutingByBusEmissions +
                                            theEmissionsSummary.productTransportByVehicleEmissions +
                                            theEmissionsSummary.productTransportByHeavyDutyTrucksEmissions +
                                            theEmissionsSummary.productTransportByRailEmissions +
                                            theEmissionsSummary.productTransportByWaterAirEmissions                                            

        //-- Save that in the theEmissionsSummary
       println "theEmissionsSummary.totalEmissions : " + theEmissionsSummary.totalEmissions
       println "theEmissionsSummary.programType : " + theEmissionsSummary.programType
       theEmissionsSummary.save()
        //-- temporary approach below for now, need more better approach??
       aclUtilService.addPermission(theEmissionsSummary, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
       //println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
}
