package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
//import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
//import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils

import org.springframework.security.acls.domain.BasePermission
import org.codehaus.groovy.grails.commons.ConfigurationHolder



class EmissionsSummaryService {

    static transactional = true
    def aclUtilService
    def springSecurityService
    def jasperService
    //def grailsApplication
    //@PreAuthorize("hasRole('ROLE_USER')")
    //@PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    @PreAuthorize("hasPermission(#id, 'org.ibLGHGCalc.EmissionsSummary', read) or hasPermission(#id, 'org.ibLGHGCalc.EmissionsSummary', admin)")
    EmissionsSummary findEmissionsSummary(long id) {
      EmissionsSummary.get(id)
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def EmissionsSummary[] findEmissionsSummarys(Map<String, String> parameters) {
      //EmissionsSummary.list()
      //def theOrganization = Organization.get(parameters.organizationId)
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else if (parameters.id) {
           // User has provided the id of the Stationary comubstion source, so just provide that and return from here.
           return EmissionsSummary.get(parameters.id)
      } else {
          println "-----I don't know organization in emissionsSummaryService.findEmissionsSummarys"
      }

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
    def EmissionsSummary calculateEmissionsSummary(Map<String, String> parameters) {
      log.info "Calculate Emissions Sumary( ${parameters} )"
      println "parameters" + parameters
      println "parameters.emissionsBeginDate : " + parameters.emissionsBeginDate
      println "parameters.emissionsEndDate : " + parameters.emissionsEndDate
      println "parameters.programType : " + parameters.programType

      Date beginDate = new Date().parse('yyyy-MM-dd', parameters.emissionsBeginDate)
      Date endDate = new Date().parse('yyyy-MM-dd', parameters.emissionsEndDate)

      //String programType = parameters.programType.toString()
      //String programType = "US EPA"
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

      //Double totalEmissions = 0
      //Double totalOptionalEmissions = 0
      //Double totalDirectEmissions = 0
      //Double totalInDirectEmissions = 0
      theEmissionsSummary.totalEmissions = 0
      theEmissionsSummary.totalOptionalEmissions = 0
      theEmissionsSummary.totalDirectEmissions = 0
      theEmissionsSummary.totalInDirectEmissions = 0
      Integer totalNumberOfSources = 0

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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theStationaryCombustionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                //println "I am in theEmissionsDetailsList for Stationary Combustion Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.stationaryCombustionEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT                   

                   //theEmissionsSummary.stationaryCombustionEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            //println "I am in theMobileCombustionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                //println "I am in theEmissionsDetailsList for Mobile Combustion Info"
                if (it.programType.equals(programType)){

                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.mobileCombustionEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT                   
                   //theEmissionsSummary.mobileCombustionEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null
                   if (it.biomassCO2Emissions) {
                        //theEmissionsSummary.biomassMobileCombustionEmissions += it.biomassCO2Emissions
                        theEmissionsSummary.biomassMobileCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            //println "I am in theRefridgerationAirConditioningInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the Refridgeration Air Conditioning Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   if (it.emissionsType.contains("Refridgeration")){
                       //theEmissionsSummary.refridgerationAirConditioningEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                       theEmissionsSummary.refridgerationAirConditioningEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   } else if (it.emissionsType.contains("Fire")){
                       //theEmissionsSummary.fireSuppressantEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                       theEmissionsSummary.fireSuppressantEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   }
                   //--check if biomass emissions is not null -- Below is not required since emissions related to refridgeration and AC
                   if (it.biomassCO2Emissions) {
                        //theEmissionsSummary.biomassMobileCombustionEmissions += it.biomassCO2Emissions
                        theEmissionsSummary.biomassMobileCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

       //println "theEmissionsSummary.fireSuppressantEmissions : " + theEmissionsSummary.fireSuppressantEmissions

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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            //println "I am in thePurchasedElectricityInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                //println "I am in theEmissionsDetailsList for Purchased Electricity Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.purchasedElectricityEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   //theEmissionsSummary.purchasedElectricityEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null - Below is not required since it is about purchased electricity
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
                       //theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

       //println "theEmissionsSummary.purchasedElectricityEmissions : " + theEmissionsSummary.purchasedElectricityEmissions

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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            //println "I am in thePurchasedSteamInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                //println "I am in theEmissionsDetailsList for Purchased Steam Info"
                if (it.programType.equals(programType)){
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.purchasedSteamEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   //theEmissionsSummary.purchasedSteamEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null - Below is not required since it is about purchased Steam
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
                       //theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

       //println "theEmissionsSummary.purchasedSteamEmissions : " + theEmissionsSummary.purchasedSteamEmissions

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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            //println "I am in theWasteStreamCombustionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                //println "I am in theEmissionsDetailsList for Waste Stream Combustion Info"
                if (it.programType.equals(programType)){
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.wasteStreamCombustionEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   //theEmissionsSummary.wasteStreamCombustionEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   //--check if biomass emissions is not null - Below is not required since it is about WasteStreamCombustion
                   if (it.biomassCO2Emissions) {
                       theEmissionsSummary.biomassStationaryCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
                       //theEmissionsSummary.biomassStationaryCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType
                }
            }
          }
       }

       //println "theEmissionsSummary.wasteStreamCombustionEmissions : " + theEmissionsSummary.wasteStreamCombustionEmissions

// ------------------- Calculate Optional Source Info Emissions
// ------------------- Calculate purchasedProductInfoEmissions
//-- Temporary initialization of theEmissionsSummary.purchasedProductInfoEmissions- remove this in futre ??
      theEmissionsSummary.purchasedGoodsAndServicesEmissions = 0
      theEmissionsSummary.purchasedCapitalGoodsEmissions = 0

//-- Get a list of thePurchasedProductInfo
      def thePurchasedProductInfoList = theOrganization.purchasedProductInfoList

//--Iterate through thePurchasedProductInfoList and get theEmissionsDetails add it up
      def thePurchasedProductInfo
      //def theEmissionsDetailsList
      thePurchasedProductInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in thePurchasedProductInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the Purchased Product Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the Purchased Product Info"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   if (it.emissionsType.contains("Capital")){
                       theEmissionsSummary.purchasedCapitalGoodsEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       println "I am in Capital for the Purchased Product Info"
                   } else if (it.emissionsType.contains("Services") ||it.emissionsType.contains("Goods") ){                       
                       theEmissionsSummary.purchasedGoodsAndServicesEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   }
                   //--check if biomass emissions is not null -- Below is not required since emissions related to refridgeration and AC
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate purchasedEnergyInfoEmissions
//-- Temporary initialization of theEmissionsSummary.purchasedEnergyInfoEmissions- remove this in futre ??
      theEmissionsSummary.purchasedEnergyEmissions = 0

//-- Get a list of thePurchasedEnergyInfo
      def thePurchasedEnergyInfoList = theOrganization.purchasedEnergyInfoList

//--Iterate through thePurchasedEnergyInfoList and get theEmissionsDetails add it up
      def thePurchasedEnergyInfo
      //def theEmissionsDetailsList
      thePurchasedEnergyInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in thePurchasedEnergyInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the Purchased Energy Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the Purchased Energy Info"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.purchasedEnergyEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }
        
// ------------------- Calculate Optional Source Info Emissions
// ------------------- Calculate theTransportationInfoEmissions
//-- Temporary initialization - remove this in futre ??
      theEmissionsSummary.upstreamTransportationEmissions = 0
      theEmissionsSummary.downstreamTransportationEmissions = 0

//-- Get a list of theTransportationInfo
      def theTransportationInfoList = theOrganization.transportationInfoList	

//--Iterate through theTransportationInfoList and get theEmissionsDetails add it up
      def theTransportationInfo
      //def theEmissionsDetailsList
      theTransportationInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theTransportationInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the Purchased Transportation Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the Purchased Transportation Info"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   if (it.emissionsType.contains("Upstream")){
                       theEmissionsSummary.upstreamTransportationEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       println "I am in Capital for the Purchased Transportation Info"
                   } else if (it.emissionsType.contains("Downstream")){                       
                       theEmissionsSummary.downstreamTransportationEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   }
                   //--check if biomass emissions is not null -- Below is not required since emissions related to refridgeration and AC
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }


// ------------------- Calculate Optional Source Info Emissions
// ------------------- Calculate theDistributionInfoEmissions
//-- Temporary initialization - remove this in futre ??
      theEmissionsSummary.upstreamDistributionEmissions = 0
      theEmissionsSummary.downstreamDistributionEmissions = 0

//-- Get a list of theDistributionInfo
      def theDistributionInfoList = theOrganization.distributionInfoList	

//--Iterate through theDistributionInfoList and get theEmissionsDetails add it up
      def theDistributionInfo
      //def theEmissionsDetailsList
      theDistributionInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theDistributionInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the Purchased Distribution Info"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the Purchased Distribution Info"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   if (it.emissionsType.contains("Upstream")){
                       theEmissionsSummary.upstreamDistributionEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       println "I am in Capital for the Purchased Distribution Info"
                   } else if (it.emissionsType.contains("Downstream")){                       
                       theEmissionsSummary.downstreamDistributionEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   }
                   //--check if biomass emissions is not null -- Below is not required since emissions related to refridgeration and AC
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }
        
// ------------------- Calculate wasteGeneratedInOperationsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.wasteGeneratedInOperationsInfoEmissions- remove this in futre ??
      theEmissionsSummary.wasteGeneratedInOperationsEmissions = 0

//-- Get a list of theWasteGeneratedInOperationsInfo
      def theWasteGeneratedInOperationsInfoList = theOrganization.wasteGeneratedInOperationsInfoList

//--Iterate through theWasteGeneratedInOperationsInfoList and get theEmissionsDetails add it up
      def theWasteGeneratedInOperationsInfo
      //def theEmissionsDetailsList
      theWasteGeneratedInOperationsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theWasteGeneratedInOperationsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the wasteGeneratedInOperationsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the wasteGeneratedInOperationsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.wasteGeneratedInOperationsEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate businessTravelInfoEmissions
//-- Temporary initialization of theEmissionsSummary.businessTravelInfoEmissions- remove this in futre ??
      theEmissionsSummary.businessTravelEmissions = 0

//-- Get a list of theBusinessTravelInfo
      def theBusinessTravelInfoList = theOrganization.businessTravelInfoList

//--Iterate through theBusinessTravelInfoList and get theEmissionsDetails add it up
      def theBusinessTravelInfo
      //def theEmissionsDetailsList
      theBusinessTravelInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theBusinessTravelInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the businessTravelInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the businessTravelInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.businessTravelEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate leasedAssetsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.leasedAssetsInfoEmissions- remove this in futre ??
      theEmissionsSummary.upstreamLeasedAssetsEmissions = 0
      theEmissionsSummary.downstreamLeasedAssetsEmissions = 0
      
//-- Get a list of theLeasedAssetsInfo
      def theLeasedAssetsInfoList = theOrganization.leasedAssetsInfoList

//--Iterate through theLeasedAssetsInfoList and get theEmissionsDetails add it up
      def theLeasedAssetsInfo
      //def theEmissionsDetailsList
      theLeasedAssetsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theLeasedAssetsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the leasedAssetsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the leasedAssetsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   //theEmissionsSummary.leasedAssetsEmissions += emissionsInMT.combinedEmissionsMT				   
                   if (it.emissionsType.contains("Upstream")){
                       theEmissionsSummary.upstreamLeasedAssetsEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       println "I am in Capital for the Purchased Distribution Info"
                   } else if (it.emissionsType.contains("Downstream")){                       
                       theEmissionsSummary.downstreamLeasedAssetsEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   }                   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }
        
// ------------------- Calculate processingOfSoldProductsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.processingOfSoldProductsInfoEmissions- remove this in futre ??
      theEmissionsSummary.processingOfSoldProductsEmissions = 0

//-- Get a list of theProcessingOfSoldProductsInfo
      def theProcessingOfSoldProductsInfoList = theOrganization.processingOfSoldProductsInfoList

//--Iterate through theProcessingOfSoldProductsInfoList and get theEmissionsDetails add it up
      def theProcessingOfSoldProductsInfo
      //def theEmissionsDetailsList
      theProcessingOfSoldProductsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theProcessingOfSoldProductsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the processingOfSoldProductsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the processingOfSoldProductsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.processingOfSoldProductsEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }
        
// ------------------- Calculate directUsePhaseEmissionsForSoldProductsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.directUsePhaseEmissionsForSoldProductsInfoEmissions- remove this in futre ??
      theEmissionsSummary.directUsePhaseEmissionsForSoldProductsEmissions = 0

//-- Get a list of theDirectUsePhaseEmissionsForSoldProductsInfo
      def theDirectUsePhaseEmissionsForSoldProductsInfoList = theOrganization.directUsePhaseEmissionsForSoldProductsInfoList

//--Iterate through theDirectUsePhaseEmissionsForSoldProductsInfoList and get theEmissionsDetails add it up
      def theDirectUsePhaseEmissionsForSoldProductsInfo
      //def theEmissionsDetailsList
      theDirectUsePhaseEmissionsForSoldProductsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theDirectUsePhaseEmissionsForSoldProductsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the directUsePhaseEmissionsForSoldProductsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the directUsePhaseEmissionsForSoldProductsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.directUsePhaseEmissionsForSoldProductsEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate inDirectUsePhaseEmissionsForSoldProductsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.inDirectUsePhaseEmissionsForSoldProductsInfoEmissions- remove this in futre ??
      theEmissionsSummary.inDirectUsePhaseEmissionsForSoldProductsEmissions = 0

//-- Get a list of theInDirectUsePhaseEmissionsForSoldProductsInfo
      def theInDirectUsePhaseEmissionsForSoldProductsInfoList = theOrganization.inDirectUsePhaseEmissionsForSoldProductsInfoList

//--Iterate through theInDirectUsePhaseEmissionsForSoldProductsInfoList and get theEmissionsDetails add it up
      def theInDirectUsePhaseEmissionsForSoldProductsInfo
      //def theEmissionsDetailsList
      theInDirectUsePhaseEmissionsForSoldProductsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theInDirectUsePhaseEmissionsForSoldProductsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the inDirectUsePhaseEmissionsForSoldProductsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the inDirectUsePhaseEmissionsForSoldProductsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.inDirectUsePhaseEmissionsForSoldProductsEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate endOfLifeTreatmentOfSoldProductsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.endOfLifeTreatmentOfSoldProductsInfoEmissions- remove this in futre ??
      theEmissionsSummary.endOfLifeTreatmentOfSoldProductsEmissions = 0

//-- Get a list of theEndOfLifeTreatmentOfSoldProductsInfo
      def theEndOfLifeTreatmentOfSoldProductsInfoList = theOrganization.endOfLifeTreatmentOfSoldProductsInfoList

//--Iterate through theEndOfLifeTreatmentOfSoldProductsInfoList and get theEmissionsDetails add it up
      def theEndOfLifeTreatmentOfSoldProductsInfo
      //def theEmissionsDetailsList
      theEndOfLifeTreatmentOfSoldProductsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theEndOfLifeTreatmentOfSoldProductsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the endOfLifeTreatmentOfSoldProductsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the endOfLifeTreatmentOfSoldProductsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.endOfLifeTreatmentOfSoldProductsEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate franchisesInfoEmissions
//-- Temporary initialization of theEmissionsSummary.franchisesInfoEmissions- remove this in futre ??
      theEmissionsSummary.franchisesEmissions = 0

//-- Get a list of theFranchisesInfo
      def theFranchisesInfoList = theOrganization.franchisesInfoList

//--Iterate through theFranchisesInfoList and get theEmissionsDetails add it up
      def theFranchisesInfo
      //def theEmissionsDetailsList
      theFranchisesInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theFranchisesInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the franchisesInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the franchisesInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);
                   theEmissionsSummary.franchisesEmissions += emissionsInMT.combinedEmissionsMT				   
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }

// ------------------- Calculate investmentsInfoEmissions
//-- Temporary initialization of theEmissionsSummary.investmentsInfoEmissions- remove this in futre ??
      theEmissionsSummary.investmentsEmissions = 0
      theEmissionsSummary.anticipatedLifetimeProjectInvestmentsEmissions = 0
      theEmissionsSummary.optionalInvestmentsEmissions = 0
      
//-- Get a list of theInvestmentsInfo
      def theInvestmentsInfoList = theOrganization.investmentsInfoList

//--Iterate through theInvestmentsInfoList and get theEmissionsDetails add it up
      def theInvestmentsInfo
      //def theEmissionsDetailsList
      theInvestmentsInfoList.each{
          if((  it.dataBeginDate >= beginDate) && ( it.dataEndDate <= endDate ))
          {
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            println "I am in theInvestmentsInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                println "I am in theEmissionsDetailsList for the investmentsInfo"
                if (it.programType.equals(programType)){
                   //--Make sure all the emissions are in appropriate units if not convert them to proper units and Get emissions in MT
                   println "I am in Program Type for the investmentsInfoEmissions"
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);

                   if (it.emissionsType.contains("Optional")){                       
                       theEmissionsSummary.optionalInvestmentsEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   } else {                       
                       theEmissionsSummary.investmentsEmissions += emissionsInMT.combinedEmissionsMT //emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                   }                   

                   //--check if anticipatedLifetimeProjectInvestmentsEmissions is not null -- Below is not required?
                   if (it.anticipatedLifetimeProjectInvestmentsEmissions) {
                        theEmissionsSummary.anticipatedLifetimeProjectInvestmentsEmissions += emissionsInMT.anticipatedLifetimeProjectInvestmentsEmissionsMT
                   }
                        
                   //--check if biomass emissions is not null -- Below is not required?
                   if (it.biomassCO2Emissions) {
                        theEmissionsSummary.biomassEmissions += emissionsInMT.biomassCO2EmissionsMT
                   }
                   theEmissionsSummary.programType=programType
                }
             }
          }
       }
        
// ------------------- Calculate Optional Source Info Emissions
//-- Temporary initialization of theEmissionsSummary.employeeBusinessTravel Emissions- remove this in futre ??
      theEmissionsSummary.employeeBusinessTravelByVehicleEmissions = 0
      theEmissionsSummary.employeeBusinessTravelByRailEmissions = 0
      theEmissionsSummary.employeeBusinessTravelByBusEmissions = 0
      theEmissionsSummary.employeeBusinessTravelByAirEmissions = 0
      theEmissionsSummary.employeeBusinessTravelHotelStayEmissions = 0      
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
            totalNumberOfSources++
            theEmissionsDetailsList = it.emissionsDetailsList
            //println "I am in theOptionalSourceInfoList"
            theEmissionsDetailsList.each{
                //theEmissionsDetails =  it
                //--Implement proper code for programType-??
                //println "I am in theEmissionsDetailsList for the optional Source Info"
                if (it.programType.equals(programType)){
                   def emissionsInMT = [:]
                   emissionsInMT = getEmissionsInMT(it);

                   if (it.emissionsType.equals("Employee Business Travel - By Vehicle")){
                       theEmissionsSummary.employeeBusinessTravelByVehicleEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeBusinessTravelByVehicleEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - By Rail")){
                       theEmissionsSummary.employeeBusinessTravelByRailEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeBusinessTravelByRailEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - By Bus")){
                       theEmissionsSummary.employeeBusinessTravelByBusEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeBusinessTravelByBusEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - By Air")){
                       theEmissionsSummary.employeeBusinessTravelByAirEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeBusinessTravelByAirEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Business Travel - Hotel Stay")){
                       theEmissionsSummary.employeeBusinessTravelHotelStayEmissions += emissionsInMT.combinedEmissionsMT
                       //theEmissionsSummary.employeeBusinessTravelByAirEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Commuting - By Vehicle")){
                       theEmissionsSummary.employeeCommutingByVehicleEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeCommutingByVehicleEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Commuting - By Rail")){
                       theEmissionsSummary.employeeCommutingByRailEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeCommutingByRailEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Employee Commuting - By Bus")){
                       theEmissionsSummary.employeeCommutingByBusEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.employeeCommutingByBusEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } 
                   
                  /* Below is commented after Scope 3 implementation
                  else if (it.emissionsType.equals("Product Transport - By Vehicle")){
                       theEmissionsSummary.productTransportByVehicleEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.productTransportByVehicleEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Heavy Duty Trucks")){
                       theEmissionsSummary.productTransportByHeavyDutyTrucksEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.productTransportByHeavyDutyTrucksEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Rail")){
                       theEmissionsSummary.productTransportByRailEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.productTransportByRailEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   } else if (it.emissionsType.equals("Product Transport - By Water or Air")){
                       theEmissionsSummary.productTransportByWaterAirEmissions += emissionsInMT.CO2EmissionsMT + emissionsInMT.CH4EmissionsMT + emissionsInMT.N2OEmissionsMT
                       //theEmissionsSummary.productTransportByWaterAirEmissions += it.CO2Emissions + it.CH4Emissions + it.N2OEmissions
                   }
                   */
                  
                   //--check if biomass emissions is not null --
                   else if (it.biomassCO2Emissions != 0) {
                        theEmissionsSummary.biomassMobileCombustionEmissions += emissionsInMT.biomassCO2EmissionsMT
                        //theEmissionsSummary.biomassMobileCombustionEmissions += it.biomassCO2Emissions
                   }
                   theEmissionsSummary.programType=programType                   
                }
             }
          }
       }

       //println "theEmissionsSummary.productTransportByWaterAirEmissions : " + theEmissionsSummary.productTransportByWaterAirEmissions

//- total emissions
       theEmissionsSummary.totalEmissions = theEmissionsSummary.stationaryCombustionEmissions +
                                            theEmissionsSummary.mobileCombustionEmissions +
                                            theEmissionsSummary.refridgerationAirConditioningEmissions +
                                            theEmissionsSummary.fireSuppressantEmissions +
                                            theEmissionsSummary.purchasedElectricityEmissions +
                                            theEmissionsSummary.purchasedSteamEmissions +
                                            theEmissionsSummary.wasteStreamCombustionEmissions
                                            
       theEmissionsSummary.totalBusinessTravelEmissions = theEmissionsSummary.employeeBusinessTravelByVehicleEmissions +
                                            theEmissionsSummary.employeeBusinessTravelByRailEmissions +
                                            theEmissionsSummary.employeeBusinessTravelByBusEmissions +
                                            theEmissionsSummary.employeeBusinessTravelByAirEmissions +
                                            theEmissionsSummary.employeeBusinessTravelHotelStayEmissions
                                            
       theEmissionsSummary.totalEmployeeCommutingEmissions = theEmissionsSummary.employeeCommutingByVehicleEmissions +
                                            theEmissionsSummary.employeeCommutingByRailEmissions +
                                            theEmissionsSummary.employeeCommutingByBusEmissions         
        
/*                                            
       theEmissionsSummary.totalOptionalEmissions = theEmissionsSummary.employeeBusinessTravelByVehicleEmissions+
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
*/

       theEmissionsSummary.totalOptionalEmissions = theEmissionsSummary.purchasedGoodsAndServicesEmissions+
                                                 theEmissionsSummary.purchasedCapitalGoodsEmissions +
                                                 theEmissionsSummary.purchasedEnergyEmissions +
                                                 theEmissionsSummary.upstreamTransportationEmissions +
                                                 theEmissionsSummary.downstreamTransportationEmissions +
                                                 theEmissionsSummary.upstreamDistributionEmissions +
                                                 theEmissionsSummary.downstreamDistributionEmissions +
                                                 theEmissionsSummary.wasteGeneratedInOperationsEmissions +
                                                 theEmissionsSummary.totalBusinessTravelEmissions +
                                                 theEmissionsSummary.totalEmployeeCommutingEmissions +
                                                 theEmissionsSummary.upstreamLeasedAssetsEmissions +
                                                 theEmissionsSummary.downstreamLeasedAssetsEmissions +
                                                 theEmissionsSummary.processingOfSoldProductsEmissions +
                                                 theEmissionsSummary.directUsePhaseEmissionsForSoldProductsEmissions +
                                                 theEmissionsSummary.inDirectUsePhaseEmissionsForSoldProductsEmissions +
                                                 theEmissionsSummary.endOfLifeTreatmentOfSoldProductsEmissions +
                                                 theEmissionsSummary.franchisesEmissions +                                                 
                                                 theEmissionsSummary.investmentsEmissions +
                                                 theEmissionsSummary.optionalInvestmentsEmissions        
                                                 
       //Removed theEmissionsSummary.anticipatedLifetimeProjectInvestmentsEmissions from above 
       
       theEmissionsSummary.totalDirectEmissions = theEmissionsSummary.stationaryCombustionEmissions +
                                            theEmissionsSummary.mobileCombustionEmissions +
                                            theEmissionsSummary.refridgerationAirConditioningEmissions +
                                            theEmissionsSummary.fireSuppressantEmissions +
                                            theEmissionsSummary.wasteStreamCombustionEmissions 

       theEmissionsSummary.totalInDirectEmissions = theEmissionsSummary.purchasedElectricityEmissions +
                                            theEmissionsSummary.purchasedSteamEmissions

       theEmissionsSummary.totalNumberOfSources = totalNumberOfSources
        //-- Save that in the theEmissionsSummary
       println "theEmissionsSummary.totalEmissions : " + theEmissionsSummary.totalEmissions
       println "theEmissionsSummary.programType : " + theEmissionsSummary.programType
               
       //-Save the user reference who generated the summary
       theEmissionsSummary.summaryGeneratedByUserReference = (SecUser) springSecurityService.currentUser

       //--Save the summary again with report file name and summary generated by user reference.
       theEmissionsSummary.save(flush:true)
        
        //-- temporary approach below for now, need more better approach??
       aclUtilService.addPermission(theEmissionsSummary, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
       return theEmissionsSummary
    }

    //-- Following method calculate emissions in Metric Tonnes (?)
    def Map<String,String> getEmissionsInMT(def emissionsInfoObject){

        def emissionsInMT = [:]

        //be careful with this initialization. May want to manage this differently
        Double CO2EmissionsMT = 0
        Double CH4EmissionsMT = 0
        Double N2OEmissionsMT = 0
        Double biomassCO2EmissionsMT = 0
        Double combinedEmissionsMT = 0        
        Double anticipatedLifetimeProjectInvestmentsEmissionsMT = 0
       
        //--GWP for now hard coded, in future get it from DB based on the program type
        def CH4GwpPotential = 21
        def N2OGwpPotential = 310

	if (emissionsInfoObject.combinedEmissionsUnit.equals("Kg")){
                //-Convert to MT
               combinedEmissionsMT = (emissionsInfoObject.combinedEmissions)/1000
	} else if (emissionsInfoObject.combinedEmissionsUnit.equals("lb")){
                //--Convert lb to Kg
                def combinedEmissionsKg = (emissionsInfoObject.combinedEmissions)*0.4536
                //-Convert to MT
                combinedEmissionsMT = combinedEmissionsKg/1000
        }

	if (emissionsInfoObject.anticipatedLifetimeProjectInvestmentsEmissionsUnit.equals("Kg")){
                //-Convert to MT
               anticipatedLifetimeProjectInvestmentsEmissionsMT = (emissionsInfoObject.anticipatedLifetimeProjectInvestmentsEmissions)/1000
	} else if (emissionsInfoObject.anticipatedLifetimeProjectInvestmentsEmissionsUnit.equals("lb")){
                //--Convert lb to Kg
                def anticipatedLifetimeProjectInvestmentsEmissionsKg = (emissionsInfoObject.anticipatedLifetimeProjectInvestmentsEmissions)*0.4536
                //-Convert to MT
                anticipatedLifetimeProjectInvestmentsEmissionsMT = anticipatedLifetimeProjectInvestmentsEmissionsKg/1000
        }
        
	if (emissionsInfoObject.CH4EmissionsUnit.equals("gram")|| emissionsInfoObject.CH4EmissionsUnit.equals("Gram")){
		def CH4EmissionsKg = (emissionsInfoObject.CH4Emissions/1000)*CH4GwpPotential
                //-Convert to MT
                CH4EmissionsMT = CH4EmissionsKg/1000
	} else if (emissionsInfoObject.CH4EmissionsUnit.equals("lb")){
		def CH4EmissionsKg = (emissionsInfoObject.CH4Emissions)*0.4536*CH4GwpPotential
                //-Convert to MT
                CH4EmissionsMT = CH4EmissionsKg/1000
	}

	if (emissionsInfoObject.N2OEmissionsUnit.equals("gram")||emissionsInfoObject.N2OEmissionsUnit.equals("Gram") ){
		def N2OEmissionsKg = (emissionsInfoObject.N2OEmissions/1000)*N2OGwpPotential
                //-Convert to MT
                N2OEmissionsMT = N2OEmissionsKg/1000
	} else if (emissionsInfoObject.N2OEmissionsUnit.equals("lb")) {
		def N2OEmissionsKg = (emissionsInfoObject.N2OEmissions)*0.4536*N2OGwpPotential
                //-Convert to MT
                N2OEmissionsMT = N2OEmissionsKg/1000
        }

	if (emissionsInfoObject.CO2EmissionsUnit.equals("Kg")){
               //-Convert to MT
               CO2EmissionsMT = (emissionsInfoObject.CO2Emissions)/1000
	} else if (emissionsInfoObject.CO2EmissionsUnit.equals("lb")){
                def CO2EmissionsKg = (emissionsInfoObject.CO2Emissions)*0.4536
               //-Convert to MT
               CO2EmissionsMT = CO2EmissionsKg/1000
	}

	if (emissionsInfoObject.biomassCO2EmissionsUnit.equals("Kg")){
                //-Convert to MT
               biomassCO2EmissionsMT = (emissionsInfoObject.biomassCO2Emissions)/1000
	} else if (emissionsInfoObject.biomassCO2EmissionsUnit.equals("lb")){
                //--Convert lb to Kg
                def biomassCO2EmissionsKg = (emissionsInfoObject.biomassCO2Emissions)*0.4536
                //-Convert to MT
                biomassCO2EmissionsMT = biomassCO2EmissionsKg/1000
        }

        
	emissionsInMT.put("CH4EmissionsMT", CH4EmissionsMT)
	//emissionsInProperUnits.put("CH4EmissionsUnit", CH4EmissionsUnit)
	emissionsInMT.put("N2OEmissionsMT", N2OEmissionsMT)
	//emissionsInProperUnits.put("N2OEmissionsUnit", N2OEmissionsUnit)
	emissionsInMT.put("CO2EmissionsMT", CO2EmissionsMT)
	//emissionsInProperUnits.put("CO2EmissionsUnit", CO2EmissionsUnit)
	emissionsInMT.put("biomassCO2EmissionsMT", biomassCO2EmissionsMT)
	//emissionsInProperUnits.put("biomassCO2EmissionsUnit", biomassCO2EmissionsUnit)
	emissionsInMT.put("combinedEmissionsMT", combinedEmissionsMT)
	//emissionsInProperUnits.put("biomassCO2EmissionsUnit", biomassCO2EmissionsUnit)
        emissionsInMT.put("anticipatedLifetimeProjectInvestmentsEmissionsMT", anticipatedLifetimeProjectInvestmentsEmissionsMT)
        
	return emissionsInMT
    }
}
