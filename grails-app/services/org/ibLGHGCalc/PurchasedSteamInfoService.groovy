package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class PurchasedSteamInfoService {
    static transactional = true
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    PurchasedSteamInfo findPurchasedSteamInfo(Long id) {
      PurchasedSteamInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def PurchasedSteamInfo[] findPurchasedSteamInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedSteamInfoList
      //PurchasedSteamInfo.findAllByOrganization(theOrganization)
      */
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return PurchasedSteamInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in purchasedSteamInfoListService.findpurchasedSteamInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def purchasedSteamInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Steam sources which are within the inventory year
      int i = 0
      theOrganization.purchasedSteamInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate)) {
            purchasedSteamInfoList[i] = it
            i++
         }
      }
      return purchasedSteamInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def PurchasedSteamInfo[] getPurchasedSteamInfos(Map<String, String> parameters) {
      //PurchasedSteamInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedSteamInfoList
      //PurchasedSteamInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def PurchasedSteamInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      def thePurchasedSteamInfo = PurchasedSteamInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      String emissionsType = "Purchased Steam"

	//-create parameters map to send to calcualteEmissions()
	def calculateEmissionParameters = [:]
	calculateEmissionParameters.put("fuelType",parameters.fuelType)
	calculateEmissionParameters.put("boilerEfficiencyPercent", parameters.boilerEfficiencyPercent.toDouble())
	calculateEmissionParameters.put("purchasedSteam", parameters.purchasedSteam.toDouble())
	calculateEmissionParameters.put("purchasedSteamUnit", parameters.purchasedSteamUnit)
	//--All the supplier provided emission factors are suppose to have some value or zero
	calculateEmissionParameters.put("supplierCO2Multiplier", parameters.supplierCO2Multiplier.toDouble())
	calculateEmissionParameters.put("supplierCH4Multiplier", parameters.supplierCH4Multiplier.toDouble())
	calculateEmissionParameters.put("supplierN2OMultiplier", parameters.supplierN2OMultiplier.toDouble())
	calculateEmissionParameters.put("supplierCO2MultiplierUnit", parameters.supplierCO2MultiplierUnit)
	calculateEmissionParameters.put("supplierCH4MultiplierUnit", parameters.supplierCH4MultiplierUnit)
	calculateEmissionParameters.put("supplierN2OMultiplierUnit", parameters.supplierN2OMultiplierUnit)

      //-define emissions map
      def Map<String,String> emissions

      if (!thePurchasedSteamInfo) {
        println "thePurchasedSteamInfo is Null"
        thePurchasedSteamInfo = new PurchasedSteamInfo()
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
        println "----CH4EMissionUnit------" + emissions.get("CH4EmissionsUnit")
        if (theEmissionsDetails) println "EMissions Details is Not Empty"
        thePurchasedSteamInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "thePurchasedSteamInfo is Not Null"
        thePurchasedSteamInfo.emissionsDetailsList.each{
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
      thePurchasedSteamInfo.sourceDescription = parameters.sourceDescription
      thePurchasedSteamInfo.fuelType = parameters.fuelType

      if(parameters.boilerEfficiencyPercent) {
          thePurchasedSteamInfo.boilerEfficiencyPercent = parameters.boilerEfficiencyPercent.toDouble()
      }

      if(parameters.purchasedSteam) {
      	thePurchasedSteamInfo.purchasedSteam = parameters.purchasedSteam.toDouble()
      	thePurchasedSteamInfo.purchasedSteamUnit= parameters.purchasedSteamUnit
      }

      if(parameters.supplierCO2Multiplier) {
      	thePurchasedSteamInfo.supplierCO2Multiplier = parameters.supplierCO2Multiplier.toDouble()
      	thePurchasedSteamInfo.supplierCO2MultiplierUnit= parameters.supplierCO2MultiplierUnit
      }

      if(parameters.supplierCH4Multiplier) {
      	thePurchasedSteamInfo.supplierCH4Multiplier = parameters.supplierCH4Multiplier.toDouble()
      	thePurchasedSteamInfo.supplierCH4MultiplierUnit= parameters.supplierCH4MultiplierUnit
      }

      if(parameters.supplierN2OMultiplier) {
      	thePurchasedSteamInfo.supplierN2OMultiplier = parameters.supplierN2OMultiplier.toDouble()
      	thePurchasedSteamInfo.supplierN2OMultiplierUnit= parameters.supplierN2OMultiplierUnit
      }

      thePurchasedSteamInfo.fuelUsedBeginDate = fuelUsedBeginDate
      thePurchasedSteamInfo.fuelUsedEndDate = fuelUsedEndDate

      //println "theEmissionsDetails :----:  " +   theEmissionsDetails
      println "thePurchasedSteamInfo :----:  " +   thePurchasedSteamInfo

      def theOrganization =  Organization.get(parameters.organizationId)
      //theOrganization.addToPurchasedSteamInfoList(thePurchasedSteamInfo)
      println "The Organization : " + theOrganization

      thePurchasedSteamInfo.organization = theOrganization
      //--Save the user reference
      thePurchasedSteamInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser
      //--Save the data origin
      thePurchasedSteamInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      thePurchasedSteamInfo.save(flush:true)
      //--Save the permissions on this object
      aclUtilService.addPermission(thePurchasedSteamInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return thePurchasedSteamInfo

      //theOrganization.save()
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //PurchasedSteamInfo.get(parameters.id)?.delete()
      def thePurchasedSteamInfo = PurchasedSteamInfo.get(parameters.id)

      if (thePurchasedSteamInfo){
        delete(thePurchasedSteamInfo)
      }
      else {
          println "No thePurchasedSteamInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#thePurchasedSteamInfo, delete) or hasPermission(#thePurchasedSteamInfo, admin)")
   void delete(PurchasedSteamInfo thePurchasedSteamInfo) {
      thePurchasedSteamInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl thePurchasedSteamInfo
   }

    def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        def Double CO2Emissions
        def Double biomassCO2Emissions
        def Double CH4Emissions
        def Double N2OEmissions

        if ( (programType.equals("US EPA")) &&
             (emissionsType.equals("Purchased Steam")) ) {
             //- Get the emission factor object
             def theEF_PurchasedSteam_EPA = EF_PurchasedSteam_EPA.findByFuelType(parameters.fuelType)

             if (parameters.supplierCO2Multiplier > 0) {
             	CO2Emissions = parameters.purchasedSteam*parameters.supplierCO2Multiplier/(parameters.boilerEfficiencyPercent/100)
             	//emissions.put("CO2EmissionsUnit", parameters.supplierCO2MultiplierUnit)
             } else {
             	CO2Emissions = parameters.purchasedSteam*theEF_PurchasedSteam_EPA.CO2Multiplier/(parameters.boilerEfficiencyPercent/100)
             	//emissions.put("CO2EmissionsUnit", theEF_PurchasedSteam_EPA.CO2MultiplierUnit)
             }

             if (parameters.supplierCH4Multiplier > 0) {
             	CH4Emissions = parameters.purchasedSteam*parameters.supplierCH4Multiplier/(parameters.boilerEfficiencyPercent/100)
             	//emissions.put("CH4EmissionsUnit", parameters.supplierCH4MultiplierUnit)
             } else {
             	CH4Emissions = parameters.purchasedSteam*theEF_PurchasedSteam_EPA.CH4Multiplier/(parameters.boilerEfficiencyPercent/100)
             	//emissions.put("CH4EmissionsUnit", parameters.CH4MultiplierUnit)
             }

             if (parameters.supplierN2OMultiplier > 0) {
             	N2OEmissions = parameters.purchasedSteam*parameters.supplierN2OMultiplier/(parameters.boilerEfficiencyPercent/100)
             	//emissions.put("N2OEmissionsUnit",  parameters.supplierN2OMultiplierUnit)
             } else {
             	N2OEmissions = parameters.purchasedSteam*theEF_PurchasedSteam_EPA.N2OMultiplier/(parameters.boilerEfficiencyPercent/100)
             	//emissions.put("N2OEmissionsUnit", parameters.N2OMultiplierUnit)
             }

	     //--biomass CO2 is zero by default
             biomassCO2Emissions = 0
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
             println "I finished calculating emission details in purchased steam"
             return emissions
        }
    }
}