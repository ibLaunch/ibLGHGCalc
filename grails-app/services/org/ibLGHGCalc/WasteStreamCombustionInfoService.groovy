package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

class WasteStreamCombustionInfoService {

    static transactional = true
    //def wasteStreamCombustionInfoService
    def aclUtilService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    WasteStreamCombustionInfo findWasteStreamCombustionInfo(Long id) {
      WasteStreamCombustionInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def WasteStreamCombustionInfo[] findWasteStreamCombustionInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.wasteStreamCombustionInfoList
      */
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else {
          println "-----I don't know organization in wasteStreamCombustionInfoService.findWasteStreamCombustionInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def wasteStreamCombustionInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the WasteStreamCombustion sources which are within the inventory year
      int i = 0
      theOrganization.wasteStreamCombustionInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate) ) {
            wasteStreamCombustionInfoList[i] = it
            i++
         }
      }
      return wasteStreamCombustionInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def WasteStreamCombustionInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      //def theEmissionsDetails
      def theWasteStreamCombustionInfo = WasteStreamCombustionInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "EPA Climate Leaders"
      String emissionsType = "Waste Stream Combustion"
      println " I am before Double conversion"
      //--get the values fromm parameters
      //--Assuming that client will always send some data in number values below, hence not checking for null
      String fuelSourceDescription = parameters.fuelSourceDescription
      Double amountOfWasterStreamGasCombusted = parameters.amountOfWasterStreamGasCombusted.toDouble()
      String amountOfWasterStreamGasCombustedUnit = parameters.amountOfWasterStreamGasCombustedUnit
      Double totalNumberOfMolesPerUnitVolument = parameters.totalNumberOfMolesPerUnitVolument.toDouble()
      String totalNumberOfMolesPerUnitVolumentUnit = parameters.totalNumberOfMolesPerUnitVolumentUnit
      Double carbonMonoxideMolarFractionPercent = parameters.carbonMonoxideMolarFractionPercent.toDouble()
      Double carbonDioxideMolarFractionPercent = parameters.carbonDioxideMolarFractionPercent.toDouble()
      Double methaneMolarFractionPercent = parameters.methaneMolarFractionPercent.toDouble()
      Double cetyleneMolarFractionPercent = parameters.cetyleneMolarFractionPercent.toDouble()
      Double ethyleneMolarFractionPercent = parameters.ethyleneMolarFractionPercent.toDouble()
      Double ethaneMolarFractionPercent = parameters.ethaneMolarFractionPercent.toDouble()
      Double propyleneMolarFractionPercent = parameters.propyleneMolarFractionPercent.toDouble()
      Double propaneMolarFractionPercent = parameters.propaneMolarFractionPercent.toDouble()
      Double n_ButaneMolarFractionPercent = parameters.n_ButaneMolarFractionPercent.toDouble()
      Double benzeneMolarFractionPercent = parameters.benzeneMolarFractionPercent.toDouble()
      Double bexaneMolarFractionPercent = parameters.bexaneMolarFractionPercent.toDouble()
      Double tolueneMolarFractionPercent = parameters.tolueneMolarFractionPercent.toDouble()
      Double octaneMolarFractionPercent = parameters.octaneMolarFractionPercent.toDouble()
      Double ethanolMolarFractionPercent = parameters.ethanolMolarFractionPercent.toDouble()
      Double acetoneMolarFractionPercent = parameters.acetoneMolarFractionPercent.toDouble()
      Double tetrahydrofuranMolarFractionPercent = parameters.tetrahydrofuranMolarFractionPercent.toDouble()
      Double otherNon_CMolarFractionPercent = parameters.otherNon_CMolarFractionPercent.toDouble()
      Double oxidationFactorPercent = parameters.oxidationFactorPercent.toDouble()

      //-create parameters map to send to calcualteEmissions()
      def calculateEmissionParameters = [:]
      //calculateEmissionParameters.put("wasteStreamComponent",parameters.wasteStreamComponent)
      calculateEmissionParameters.put("amountOfWasterStreamGasCombusted",amountOfWasterStreamGasCombusted)
      calculateEmissionParameters.put("amountOfWasterStreamGasCombustedUnit",amountOfWasterStreamGasCombustedUnit)
      calculateEmissionParameters.put("totalNumberOfMolesPerUnitVolument",totalNumberOfMolesPerUnitVolument)
      calculateEmissionParameters.put("totalNumberOfMolesPerUnitVolumentUnit",totalNumberOfMolesPerUnitVolumentUnit)
      calculateEmissionParameters.put("carbonMonoxideMolarFractionPercent",carbonMonoxideMolarFractionPercent)
      calculateEmissionParameters.put("carbonDioxideMolarFractionPercent",carbonDioxideMolarFractionPercent)
      calculateEmissionParameters.put("methaneMolarFractionPercent",methaneMolarFractionPercent)
      calculateEmissionParameters.put("cetyleneMolarFractionPercent",cetyleneMolarFractionPercent)
      calculateEmissionParameters.put("ethyleneMolarFractionPercent",ethyleneMolarFractionPercent)
      calculateEmissionParameters.put("ethaneMolarFractionPercent",ethaneMolarFractionPercent)
      calculateEmissionParameters.put("propyleneMolarFractionPercent",propyleneMolarFractionPercent)
      calculateEmissionParameters.put("propaneMolarFractionPercent",propaneMolarFractionPercent)
      calculateEmissionParameters.put("n_ButaneMolarFractionPercent",n_ButaneMolarFractionPercent)
      calculateEmissionParameters.put("benzeneMolarFractionPercent",benzeneMolarFractionPercent)
      calculateEmissionParameters.put("bexaneMolarFractionPercent",bexaneMolarFractionPercent)
      calculateEmissionParameters.put("tolueneMolarFractionPercent",tolueneMolarFractionPercent)
      calculateEmissionParameters.put("octaneMolarFractionPercent",octaneMolarFractionPercent)
      calculateEmissionParameters.put("ethanolMolarFractionPercent",ethanolMolarFractionPercent)
      calculateEmissionParameters.put("acetoneMolarFractionPercent",acetoneMolarFractionPercent)
      calculateEmissionParameters.put("tetrahydrofuranMolarFractionPercent",tetrahydrofuranMolarFractionPercent)
      calculateEmissionParameters.put("otherNon_CMolarFractionPercent",otherNon_CMolarFractionPercent)
      calculateEmissionParameters.put("oxidationFactorPercent",oxidationFactorPercent)

      //-define emissions map
      def Map<String,String> emissions

      if (!theWasteStreamCombustionInfo) {
        println "theWasteStreamCombustionInfo is Null"
        theWasteStreamCombustionInfo = new WasteStreamCombustionInfo()
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

        if (theEmissionsDetails) println "EMissions Details is Not Empty"
        theWasteStreamCombustionInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "theWasteStreamCombustionInfo is Not Null"
        theWasteStreamCombustionInfo.emissionsDetailsList.each{
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

      Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedBeginDate)
      Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedEndDate)
      println "fuelUsedBeginDate : " + fuelUsedBeginDate
      println "fuelUsedEndDate : " + fuelUsedEndDate

      //--theWasteStreamCombustionInfo.properties = parameters;
      theWasteStreamCombustionInfo.fuelSourceDescription = fuelSourceDescription
      theWasteStreamCombustionInfo.amountOfWasterStreamGasCombusted = amountOfWasterStreamGasCombusted
      theWasteStreamCombustionInfo.amountOfWasterStreamGasCombustedUnit = amountOfWasterStreamGasCombustedUnit
      theWasteStreamCombustionInfo.totalNumberOfMolesPerUnitVolument = totalNumberOfMolesPerUnitVolument
      theWasteStreamCombustionInfo.totalNumberOfMolesPerUnitVolumentUnit = totalNumberOfMolesPerUnitVolumentUnit
      theWasteStreamCombustionInfo.carbonMonoxideMolarFractionPercent = carbonMonoxideMolarFractionPercent
      theWasteStreamCombustionInfo.carbonDioxideMolarFractionPercent = carbonDioxideMolarFractionPercent
      theWasteStreamCombustionInfo.methaneMolarFractionPercent = methaneMolarFractionPercent
      theWasteStreamCombustionInfo.cetyleneMolarFractionPercent = cetyleneMolarFractionPercent
      theWasteStreamCombustionInfo.ethyleneMolarFractionPercent = ethyleneMolarFractionPercent
      theWasteStreamCombustionInfo.ethaneMolarFractionPercent = ethaneMolarFractionPercent
      theWasteStreamCombustionInfo.propyleneMolarFractionPercent = propyleneMolarFractionPercent
      theWasteStreamCombustionInfo.propaneMolarFractionPercent = propaneMolarFractionPercent
      theWasteStreamCombustionInfo.n_ButaneMolarFractionPercent = n_ButaneMolarFractionPercent
      theWasteStreamCombustionInfo.benzeneMolarFractionPercent = benzeneMolarFractionPercent
      theWasteStreamCombustionInfo.bexaneMolarFractionPercent = bexaneMolarFractionPercent
      theWasteStreamCombustionInfo.tolueneMolarFractionPercent = tolueneMolarFractionPercent
      theWasteStreamCombustionInfo.octaneMolarFractionPercent = octaneMolarFractionPercent
      theWasteStreamCombustionInfo.ethanolMolarFractionPercent = ethanolMolarFractionPercent
      theWasteStreamCombustionInfo.acetoneMolarFractionPercent = acetoneMolarFractionPercent
      theWasteStreamCombustionInfo.tetrahydrofuranMolarFractionPercent = tetrahydrofuranMolarFractionPercent
      theWasteStreamCombustionInfo.otherNon_CMolarFractionPercent = otherNon_CMolarFractionPercent
      theWasteStreamCombustionInfo.oxidationFactorPercent = oxidationFactorPercent

      theWasteStreamCombustionInfo.fuelUsedBeginDate = fuelUsedBeginDate
      theWasteStreamCombustionInfo.fuelUsedEndDate = fuelUsedEndDate
      //theWasteStreamCombustionInfo.isPublic = parameters.isPublic
      //println "theEmissionsDetails :----:  " +   theEmissionsDetails
      println "theWasteStreamCombustionInfo :----:  " +   theWasteStreamCombustionInfo

      def theOrganization =  Organization.get(parameters.organizationId)
      println "The Organization : " + theOrganization

      theWasteStreamCombustionInfo.organization = theOrganization
      theWasteStreamCombustionInfo.save(flush:true)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //WasteStreamCombustionInfo.get(parameters.id)?.delete()
      def theWasteStreamCombustionInfo = WasteStreamCombustionInfo.get(parameters.id)

      if (theWasteStreamCombustionInfo){
        delete(theWasteStreamCombustionInfo)
      }
      else {
          println "No theWasteStreamCombustionInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theWasteStreamCombustionInfo, delete) or hasPermission(#theWasteStreamCombustionInfo, admin)")
   void delete(WasteStreamCombustionInfo theWasteStreamCombustionInfo) {
      theWasteStreamCombustionInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theWasteStreamCombustionInfo
   }

    def Map<String,String> calculateEmissions(Map<String, Double> parameters, String programType, String emissionsType){
        def emissions = [:]
        def Double CO2Emissions
        def Double biomassCO2Emissions
        def Double CH4Emissions
        def Double N2OEmissions
        def Double CO2MolecularWeight
        //--Use the technique below to potentially not use table to get molecular weight and percent carbon for waste stream components
        //def wasteStreamComponentMolecularWeightPercentCarbon = ["wasteStreamComponent"]["molecularWeight"]["percentCarbon"]
        //wasteStreamComponentMolecularWeightPercentCarbon.put("wasteStreamComponent":"test","molecularWeight":20,"percentCarbon":20)

        if ( (programType.equals("EPA Climate Leaders")) &&
             (emissionsType.equals("Waste Stream Combustion")) ) {
             //- Get the list of waste stream components along with molecular weight and percent carbon. This step can be replaced with hashmap
             def theEF_WasteStreamCombustion_EPAList =  EF_WasteStreamCombustion_EPA.list()
             println " I am in calculateEmissions() of wasteStreamCombustionService "
             CO2Emissions = 0
             biomassCO2Emissions = 0
             CH4Emissions = 0
             N2OEmissions = 0

             //Fix the calculations below
             theEF_WasteStreamCombustion_EPAList.each{
                    if (it.wasteStreamComponent.equals("Carbon Monoxide")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Carbon Dioxide")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                        CO2MolecularWeight=it.molecularWeight
                    } else if (it.wasteStreamComponent.equals("Methane")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Acetylene")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Ethylene")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Ethane")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Propylene")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Propane")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("n-Butane")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Benzene")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Hexane")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Toluene")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                    } else if (it.wasteStreamComponent.equals("Octane")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                     } else if (it.wasteStreamComponent.equals("Ethanol")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                     } else if (it.wasteStreamComponent.equals("Acetone")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                     } else if (it.wasteStreamComponent.equals("Tetrahydrofuran")){
                        CO2Emissions += (parameters.carbonMonoxideMolarFractionPercent/100) * parameters.totalNumberOfMolesPerUnitVolument * it.molecularWeight * (it.percentCarbon/100)
                     } else {
                        println "Can't find the waste Stream Component in the EF_WasteStreamCombustion_EPA"
                    }
             }

             CO2Emissions =  CO2Emissions*(parameters.oxidationFactorPercent/100)*parameters.amountOfWasterStreamGasCombusted*CO2MolecularWeight/12.011

             emissions.put("CO2Emissions", CO2Emissions)
             emissions.put("biomassCO2Emissions", biomassCO2Emissions)
             emissions.put("CH4Emissions", CH4Emissions)
             emissions.put("N2OEmissions", N2OEmissions)
             emissions.put("CO2EmissionsUnit", "lb")
             emissions.put("biomassCO2EmissionsUnit", "?")
             emissions.put("CH4EmissionsUnit", "?")
             emissions.put("N2OEmissionsUnit", "?")
             emissions.put("emissionsType", emissionsType)
             emissions.put("programType", programType)

             return emissions
        }

    }

}