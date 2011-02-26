package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

class PurchasedElectricityInfoService {
    
    static transactional = true
    def aclUtilService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    PurchasedElectricityInfo findPurchasedElectricityInfo(Long id) {
      PurchasedElectricityInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def PurchasedElectricityInfo[] findPurchasedElectricityInfos(Map<String, String> parameters) {
      /* commented below
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedElectricityInfoList
      //PurchasedElectricityInfo.findAllByOrganization(theOrganization)
      */
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else {
          println "-----I don't know organization in purchasedElectricityInfoListService.findpurchasedElectricityInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def purchasedElectricityInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Electricity sources which are within the inventory year
      int i = 0
      theOrganization.purchasedElectricityInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate)) {
            purchasedElectricityInfoList[i] = it
            i++
         }
      }
      return purchasedElectricityInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def PurchasedElectricityInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      //- Getting the emission Factors
      def theEF_PurchasedElectricity_EPA =  EF_PurchasedElectricity_EPA.findByEGRIDSubregion(parameters.eGRIDSubregion)

      //-Convert parameters  fuelQuantity to Double
      //Double  fuelQty = (parameters.fuelQuantity).toDouble()
      //-Define emissions object
      //def theEmissionsDetails
      def thePurchasedElectricityInfo = PurchasedElectricityInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "EPA Climate Leaders"
      String emissionsType = "Purchased Electricity"

      //-define emissions map
      def Map<String,String> emissions

      if (!thePurchasedElectricityInfo) {
        println "thePurchasedElectricityInfo is Null"
        thePurchasedElectricityInfo = new PurchasedElectricityInfo()
        def theEmissionsDetails = new EmissionsDetails()

        emissions = calculateEmissions(parameters.eGRIDSubregion, parameters.purchasedElectricity.toDouble(), programType, emissionsType)

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
        thePurchasedElectricityInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "thePurchasedElectricityInfo is Not Null"
        thePurchasedElectricityInfo.emissionsDetailsList.each{
          def theEmissionsDetails = it
          println "calling calculateEmissions"
          emissions = calculateEmissions(parameters.eGRIDSubregion, parameters.purchasedElectricity.toDouble(), programType, emissionsType)
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

      //thePurchasedElectricityInfo.properties = parameters;
      thePurchasedElectricityInfo.sourceDescription = parameters.sourceDescription
      thePurchasedElectricityInfo.eGRIDSubregion = parameters.eGRIDSubregion
      thePurchasedElectricityInfo.purchasedElectricity = parameters.purchasedElectricity.toDouble()
      thePurchasedElectricityInfo.purchasedElectricityUnit = parameters.purchasedElectricityUnit
      thePurchasedElectricityInfo.fuelUsedBeginDate = fuelUsedBeginDate
      thePurchasedElectricityInfo.fuelUsedEndDate = fuelUsedEndDate

      //println "theEmissionsDetails :----:  " +   theEmissionsDetails
      println "thePurchasedElectricityInfo :----:  " +   thePurchasedElectricityInfo

      def theOrganization =  Organization.get(parameters.organizationId)
      //theOrganization.addToPurchasedElectricityInfoList(thePurchasedElectricityInfo)
      println "The Organization : " + theOrganization

      thePurchasedElectricityInfo.organization = theOrganization
      thePurchasedElectricityInfo.save(flush:true)
      //theOrganization.save()
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //PurchasedElectricityInfo.get(parameters.id)?.delete()
      def thePurchasedElectricityInfo = PurchasedElectricityInfo.get(parameters.id)

      if (thePurchasedElectricityInfo){
        delete(thePurchasedElectricityInfo)
      }
      else {
          println "No thePurchasedElectricityInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#thePurchasedElectricityInfo, delete) or hasPermission(#thePurchasedElectricityInfo, admin)")
   void delete(PurchasedElectricityInfo thePurchasedElectricityInfo) {
      thePurchasedElectricityInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl thePurchasedElectricityInfo
   }

    def Map<String,String> calculateEmissions(String eGRIDSubregion, Double purchasedElectricity, String programType, String emissionsType){
        def emissions = [:]
        def Double CO2Emissions
        def Double biomassCO2Emissions
        def Double CH4Emissions
        def Double N2OEmissions

        if ( (programType.equals("EPA Climate Leaders")) &&
             (emissionsType.equals("Purchased Electricity")) ) {
             //- Get the emission factor object
             def theEF_PurchasedElectricity_EPA = EF_PurchasedElectricity_EPA.findByEGRIDSubregion(eGRIDSubregion)

             CO2Emissions = purchasedElectricity*theEF_PurchasedElectricity_EPA.CO2Multiplier
             biomassCO2Emissions = 0
             CH4Emissions= purchasedElectricity*theEF_PurchasedElectricity_EPA.CH4Multiplier
             N2OEmissions= purchasedElectricity*theEF_PurchasedElectricity_EPA.N2OMultiplier

             emissions.put("CO2Emissions", CO2Emissions)
             emissions.put("biomassCO2Emissions", biomassCO2Emissions)
             emissions.put("CH4Emissions", CH4Emissions)
             emissions.put("N2OEmissions", N2OEmissions)
             emissions.put("CO2EmissionsUnit", "lb/MWh")
             emissions.put("biomassCO2EmissionsUnit", "lb/MWh")
             emissions.put("CH4EmissionsUnit", "lb/MWh")
             emissions.put("N2OEmissionsUnit", "lb/MWh")
             emissions.put("emissionsType", emissionsType)
             emissions.put("programType", programType)

             return emissions
        }
    }
}