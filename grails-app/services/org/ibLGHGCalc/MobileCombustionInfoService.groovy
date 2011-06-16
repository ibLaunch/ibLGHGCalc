package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class MobileCombustionInfoService {

    static transactional = true
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    MobileCombustionInfo findMobileCombustionInfo(Long id) {
      MobileCombustionInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def MobileCombustionInfo[] findMobileCombustionInfos(Map<String, String> parameters) {
      //MobileCombustionInfo.list()
      //def theOrganization = Organization.get(parameters.organizationId)
      //return theOrganization.mobileCombustionInfoList
      //MobileCombustionInfo.findAllByOrganization(theOrganization)

      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.           
           return MobileCombustionInfo.get(parameters.id)
      }else {
          println "-----I don't know organization in MobileCombustionInfoService.findMobileCombustionInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def mobileCombustionInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the mobile combustion sources which are within the inventory year
      int i = 0
      theOrganization.mobileCombustionInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate)) {
            mobileCombustionInfoList[i] = it
            i++
         }
      }
      return mobileCombustionInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def MobileCombustionInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      //-Convert parameters  fuelQuantity to Double
      Double  fuelQty = (parameters.fuelQuantity).toDouble()
      Float milesTravelled = (parameters.milesTravelled).toDouble()
      Float bioFuelPercent = parameters.bioFuelPercent.toDouble()
      Float ethanolPercent = parameters.ethanolPercent.toDouble()

      //--Implement proper code for programType-??
      String programType = "EPA Climate Leaders"
      String emissionsType = "Mobile Combustion"

      def Map<String,String> emissions
      
      def theMobileCombustionInfo = MobileCombustionInfo.get(parameters.id)
      //println "just got theMobileCombustionInfo"

      if (!theMobileCombustionInfo) {
        println "theMobileCombustionInfo is Null"
        theMobileCombustionInfo = new MobileCombustionInfo()
        def theEmissionsDetails = new EmissionsDetails()
        
//--Methods below not working properly due to default transactional nature of each method with in grails service
        //theEmissionsDetails = createEmissionsDetails(parameters.fuelType, fuelQty, programType, emissionType)
        //theEmissionsDetails = createCO2EmissionsDetails(parameters.fuelType, fuelQty, programType, emissionType,bioFuelPercent, ethanolPercent)
        //theEmissionsDetails.CO2Emissions = createCO2EmissionsDetails(parameters.fuelType, fuelQty, programType, emissionsType,bioFuelPercent, ethanolPercent)
        //println "got theEmissionsDetails.CO2Emissions : " + theEmissionsDetails.CO2Emissions
//--Create theEmissionsDetails
        println "Calling calculateEmissions !"
        emissions = calculateEmissions(parameters.vehicleType, parameters.vehicleYear,parameters.fuelType, fuelQty, milesTravelled, programType, emissionsType, bioFuelPercent, ethanolPercent)
        println "Called calculateEmissions !"
        if (theEmissionsDetails) println "Emissions Details is Not Empty"
        
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
	//--update theEmissionsDetails with CH4N2O Emissions
	//theEmissionsDetails = updateCH4N2OEmissionsDetails(theEmissionsDetails, parameters.vehicleType, parameters.vehicleYear,parameters.fuelType, milesTravelled, programType, emissionType, bioFuelPercent, ethanolPercent)
        println "theEmissionsDetails.CH4Emissions : " + theEmissionsDetails.CH4Emissions + "theEmissionsDetails.N2OEmissions : " + theEmissionsDetails.N2OEmissions
        theMobileCombustionInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "theMobileCombustionInfo is Not Null"
        theMobileCombustionInfo.emissionsDetailsList.each{
          def theEmissionsDetails = it
          //--Implement proper code for programType-??
          println "Calling calculateEmissions !"
          emissions = calculateEmissions(parameters.vehicleType, parameters.vehicleYear,parameters.fuelType, fuelQty, milesTravelled, programType, emissionsType, bioFuelPercent, ethanolPercent)
          println "Called calculateEmissions !"
          if (theEmissionsDetails) println "Emissions Details is Not Empty"

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
        }
      }

      //Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedBeginDate)
      //Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedEndDate)
      Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedBeginDate)
      Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedEndDate)

      println "fuelUsedBeginDate : " + fuelUsedBeginDate
      println "fuelUsedEndDate : " + fuelUsedEndDate

      //theMobileCombustionInfo.properties = parameters;
      // set the mobile combustion info properties
      theMobileCombustionInfo.fuelSourceDescription = parameters.fuelSourceDescription
      theMobileCombustionInfo.vehicleType = parameters.vehicleType
      //insert vehicleYear only if it is not null
      if (parameters.vehicleYear) {
          theMobileCombustionInfo.vehicleYear = parameters.vehicleYear
      }
      theMobileCombustionInfo.fuelType = parameters.fuelType
      theMobileCombustionInfo.fuelQuantity = fuelQty
      theMobileCombustionInfo.fuelUnit = parameters.fuelUnit
      theMobileCombustionInfo.milesTravelled = milesTravelled

      //theMobileCombustionInfo.bioFuelPercent = bioFuelPercent
      //theMobileCombustionInfo.ethanolPercent = ethanolPercent

      if (parameters.fuelType.contains("Biodiesel")){
          theMobileCombustionInfo.bioFuelPercent = bioFuelPercent
          theMobileCombustionInfo.ethanolPercent = 0
      } else if (parameters.fuelType.contains("Ethanol")){
          theMobileCombustionInfo.ethanolPercent = ethanolPercent
          theMobileCombustionInfo.bioFuelPercent = 0
      } else {
          theMobileCombustionInfo.ethanolPercent = 0
          theMobileCombustionInfo.bioFuelPercent = 0
      }

      theMobileCombustionInfo.fuelUsedBeginDate = fuelUsedBeginDate
      theMobileCombustionInfo.fuelUsedEndDate = fuelUsedEndDate

      def theOrganization =  Organization.get(parameters.organizationId)
      //theOrganization.addToMobileCombustionInfoList(theMobileCombustionInfo)
      println "The Organization : " + theOrganization

      //--Save the user reference
      theMobileCombustionInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser
      //--Save the data origin
      theMobileCombustionInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theMobileCombustionInfo.organization = theOrganization
      theMobileCombustionInfo.save(flush:true)
      aclUtilService.addPermission(theMobileCombustionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
      return theMobileCombustionInfo
      //theOrganization.save()
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //MobileCombustionInfo.get(parameters.id)?.delete()
      def theMobileCombustionInfo = MobileCombustionInfo.get(parameters.id)

      if (theMobileCombustionInfo){
        delete(theMobileCombustionInfo)
      }
      else {
          println "No theMobileCombustionInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theMobileCombustionInfo, delete) or hasPermission(#theMobileCombustionInfo, admin)")
   void delete(MobileCombustionInfo theMobileCombustionInfo) {
      theMobileCombustionInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theMobileCombustionInfo
   }

    def EmissionsDetails updateCO2EmissionsDetails(EmissionsDetails theEmissionsDetails, String fuelType, Double fuelQty, String programType, String emissionType, Double bioFuelPercent, Double biodieselFuelPercent){
        if ((theEmissionsDetails.programType.equals("EPA Climate Leaders")) &&
                (theEmissionsDetails.emissionsType.equals("Mobile Combustion")) ){
            def theEF_CO2_MobileCombustion_EPA = EF_CO2_MobileCombustion_EPA.findByFuelType(fuelType)

	    if ((!fuelType.contains("Biodiesel")) && (!fuelType.contains("Ethanol"))) {
            	theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg
	    }
	    else if (fuelType.contains("Biodiesel")) {
	    	println "Its a Biodiesel"
	    	theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-bioFuelPercent)/100
	    }
	    else if (fuelType.contains("Ethanol")) {
	        println "Its a Ethanol"
	        theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-biodieselFuelPercent)/100
	    }
        }
        return theEmissionsDetails
    }

    def Double createCO2EmissionsDetails(String fuelType, Double fuelQty, String programType, String emissionsType, Double bioFuelPercent, Double ethanolPercent){
        if ( (programType.equals("EPA Climate Leaders")) &&
             (emissionsType.equals("Mobile Combustion")) ) {
            //- Get the emission factor object
            def theEF_CO2_MobileCombustion_EPA = EF_CO2_MobileCombustion_EPA.findByFuelType(fuelType)
            //- Create theEmissionsDetails
            //def theEmissionsDetails = new EmissionsDetails()
            def Double CO2Emissions
  	    if ((!fuelType.contains("Biodiesel")) && (!fuelType.contains("Ethanol"))) {
            	//theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg
                CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg
                //println "CO2Emissions : "+ CO2Emissions
	    }
	    else if (fuelType.contains("Biodiesel")) {
	    	println "Its a Biodiesel"
	    	//theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-bioFuelPercent)/100
                CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-bioFuelPercent)/100
	    }
	    else if (fuelType.contains("Ethanol")) {
	        println "Its a Ethanol"
	        //theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-ethanolPercent)/100
                CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-ethanolPercent)/100
	    }
        }
        //return CO2Emissions
    }


    //def EmissionsDetails updateEmissionsDetails(EmissionsDetails theEmissionsDetails, String vehicleType,String vehicleYear, String fuelType, Double milesTravelled, String programType, String emissionType, Double bioFuelPercent, Double ethanolPercent){
    def Map<String, String> calculateEmissions( String vehicleType,String vehicleYear, String fuelType, Double fuelQty, Double milesTravelled, String programType, String emissionsType, Double bioFuelPercent, Double ethanolPercent){
        def emissions = [:]
        def Double CO2Emissions
        def Double biomassCO2Emissions
        def Double CH4Emissions
        def Double N2OEmissions

        println "vehicleType: " + vehicleType

        if ( (programType.equals("EPA Climate Leaders")) &&
             (emissionsType.equals("Mobile Combustion")) ) {
            //-- Getting CO2 emissions

            def theEF_CO2_MobileCombustion_EPA = EF_CO2_MobileCombustion_EPA.findByFuelType(fuelType)
            println "I am in first If"
  	    if ((!fuelType.contains("Biodiesel")) && (!fuelType.contains("Ethanol"))) {
            	//theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg
                CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg
                biomassCO2Emissions = 0
                //println "CO2Emissions : "+ CO2Emissions
	    }
	    else if (fuelType.contains("Biodiesel")) {
	    	println "Its a Biodiesel"
	    	//theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-bioFuelPercent)/100
                CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-bioFuelPercent)/100
                biomassCO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.biomassCO2MultiplierInKg*bioFuelPercent/100
	    }
	    else if (fuelType.contains("Ethanol")) {
	        println "Its a Ethanol"
	        //theEmissionsDetails.CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-ethanolPercent)/100
                CO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.CO2MultiplierInKg*(100-ethanolPercent)/100
                biomassCO2Emissions = fuelQty*theEF_CO2_MobileCombustion_EPA.biomassCO2MultiplierInKg*ethanolPercent/100
	    }
            emissions.put("CO2Emissions", CO2Emissions)
            //-- Getting CH4 and N2O emissions
            //- Get the emission factor object based on vehicleType
            //--Try with only one domain for all the emission factors for mobile combustions (Gasoline, NonGasoline, NonHighway and others)
            //def theEF_CH4N2O_MobileCombustion_EPA = EF_CH4N2O_MobileCombustion_EPA.findByVehicleTypeAndVehicleYear(vehicleType,vehicleYear)
            
            def theEF_CH4N2O_MobileCombustion_EPA

            if ((!vehicleYear) || (vehicleYear.equals(''))){
               theEF_CH4N2O_MobileCombustion_EPA = EF_CH4N2O_MobileCombustion_EPA.findByVehicleType(vehicleType)
               println "I am in second If"
            }
            else {
                /*Manually finding theEF_CH4N2O_MobileCombustion_EPA, rather than in DB
                def eF_CH4N2O_MobileCombustion_EPAList = EF_CH4N2O_MobileCombustion_EPA.findByVehicleType(vehicleType)
                for (i in eF_CH4N2O_MobileCombustion_EPAList){
                    if (i.vehicleYear.equals(vehicleYear)){
                        theEF_CH4N2O_MobileCombustion_EPA = i
                        break
                    }
                }
                */
                theEF_CH4N2O_MobileCombustion_EPA = EF_CH4N2O_MobileCombustion_EPA.findByVehicleTypeAndVehicleYear(vehicleType,vehicleYear)
                println "I am in second If else"
            }

            if (!theEF_CH4N2O_MobileCombustion_EPA) {
                println "!!!!theEF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram : " + theEF_CH4N2O_MobileCombustion_EPA
            } else {
                println "theEF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram : " + theEF_CH4N2O_MobileCombustion_EPA
            }
            println "vehicleType: "+ vehicleType + "vehicleYear : "+ vehicleYear

            if (theEF_CH4N2O_MobileCombustion_EPA) {
		    if (fuelType.contains("Biodiesel")) {
		    	println "Its a Biodiesel"
	            	CH4Emissions = theEF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	N2OEmissions = theEF_CH4N2O_MobileCombustion_EPA.N2OMultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
		    }
		    else if (fuelType.contains("Ethanol")) {
		        println "Its a Ethanol"
	            	CH4Emissions = theEF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	N2OEmissions = theEF_CH4N2O_MobileCombustion_EPA.N2OMultiplierInGram*milesTravelled*(100-ethanolPercent)/100
		    }
                    else if ((!fuelType.contains("Biodiesel")) && (!fuelType.contains("Ethanol"))) {
                        println "I am in Not Not logic"
                        CH4Emissions = theEF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram*milesTravelled
	            	N2OEmissions = theEF_CH4N2O_MobileCombustion_EPA.N2OMultiplierInGram*milesTravelled
                        biomassCO2Emissions = 0
		    }
                    emissions.put("CH4Emissions", CH4Emissions)
                    emissions.put("N2OEmissions", N2OEmissions)
                    emissions.put("biomassCO2Emissions", biomassCO2Emissions)
                    emissions.put("CO2EmissionsUnit", "Kg")
                    emissions.put("biomassCO2EmissionsUnit", "Kg")
                    emissions.put("CH4EmissionsUnit", "gram")
                    emissions.put("N2OEmissionsUnit", "gram")
                    emissions.put("emissionsType", emissionsType)
                    emissions.put("programType", programType)

                    return emissions
            }
            else
            {
                println "No emission factors found for mobile comubstion"
            }

           /*
            //- Check if vechicleType is Gasoline based
            def theEF_CH4N2O_MobileCombustionGasoline_EPA = EF_CH4N2O_MobileCombustionGasoline_EPA.findByVehicleTypeAndVehicleYear(vehicleType,vehicleYear)
            if (theEF_CH4N2O_MobileCombustionGasoline_EPA) {
            	println "I am in CH4N2O Gasoline"
            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.CH4MultiplierInGram*milesTravelled
            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.N2OMultiplierInGram*milesTravelled
            	//return theEmissionsDetails
            	CH4Emissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.CH4MultiplierInGram*milesTravelled
            	N2OEmissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.N2OMultiplierInGram*milesTravelled
                emissions.put("CH4Emissions", CH4Emissions)
                emissions.put("N2OEmissions", N2OEmissions)
                //emissions.put("CH4EmissionsUnit", "gram")
                //emissions.put("N2OEmissionsUnit", "gram")
                //emissions.put("emissionsType", emissionsType)
                //emissions.put("programType", programType)
            	return emissions
            }

            //Check if vechicleType is NonGasoline based
            def theEF_CH4N2O_MobileCombustionNonGasoline_EPA = EF_CH4N2O_MobileCombustionNonGasoline_EPA.findByVehicleTypeAndVehicleYear(vehicleType,vehicleYear)
            if (theEF_CH4N2O_MobileCombustionNonGasoline_EPA) {
                    println " I am in theEF_CH4N2O_MobileCombustionNonGasoline_EPA"
	  	    if ((!fuelType.contains("Biodiesel")) && (!fuelType.contains("Ethanol"))) {
	            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.CH4MultiplierInGram*milesTravelled
	            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.N2OMultiplierInGram*milesTravelled
                        println "I am in Not Not logic"
                        CH4Emissions = theEF_CH4N2O_MobileCombustionNonGasoline_EPA.CH4MultiplierInGram*milesTravelled
	            	N2OEmissions = theEF_CH4N2O_MobileCombustionNonGasoline_EPA.N2OMultiplierInGram*milesTravelled

		    }
		    else if (fuelType.contains("Biodiesel")) {
		    	println "Its a Biodiesel"
	            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.CH4MultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.N2OMultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	CH4Emissions = theEF_CH4N2O_MobileCombustionNonGasoline_EPA.CH4MultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	N2OEmissions = theEF_CH4N2O_MobileCombustionNonGasoline_EPA.N2OMultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
		    }
		    else if (fuelType.contains("Ethanol")) {
		        println "Its a Ethanol"
	            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.CH4MultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionGasoline_EPA.N2OMultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	CH4Emissions = theEF_CH4N2O_MobileCombustionNonGasoline_EPA.CH4MultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	N2OEmissions = theEF_CH4N2O_MobileCombustionNonGasoline_EPA.N2OMultiplierInGram*milesTravelled*(100-ethanolPercent)/100
		    }
                    emissions.put("CH4Emissions", CH4Emissions)
                    emissions.put("N2OEmissions", N2OEmissions)
                   
                    //emissions.put("CH4EmissionsUnit", "gram")
                    //emissions.put("N2OEmissionsUnit", "gram")
                    //emissions.put("emissionsType", emissionsType)
                    //emissions.put("programType", programType)
                    
                    return emissions
            }

            //Check if vechicleType is NonHighway based
            def theEF_CH4N2O_MobileCombustionNonHighway_EPA = EF_CH4N2O_MobileCombustionNonHighway_EPA.findByVehicleType(vehicleType)
            if (theEF_CH4N2O_MobileCombustionNonHighway_EPA) {
                    println " I am in theEF_CH4N2O_MobileCombustionNonHighway_EPA"
	  	    if ((!fuelType.contains("Biodiesel")) && (!fuelType.contains("Ethanol"))) {
	            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram*milesTravelled
	            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram*milesTravelled
	            	CH4Emissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram*milesTravelled
	            	N2OEmissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram*milesTravelled
		    }
		    else if (fuelType.contains("Biodiesel")) {
		    	println "Its a Biodiesel"
	            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	CH4Emissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram*milesTravelled*(100-bioFuelPercent)/100
	            	N2OEmissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram*milesTravelled*(100-bioFuelPercent)/100

		    }
		    else if (fuelType.contains("Ethanol")) {
		        println "Its a Ethanol"
	            	//theEmissionsDetails.CH4Emissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	//theEmissionsDetails.N2OEmissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	CH4Emissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram*milesTravelled*(100-ethanolPercent)/100
	            	N2OEmissions = theEF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram*milesTravelled*(100-ethanolPercent)/100
		    }
                    emissions.put("CH4Emissions", CH4Emissions)
                    emissions.put("N2OEmissions", N2OEmissions)
                   
                    //emissions.put("CH4EmissionsUnit", "gram")
                    //emissions.put("N2OEmissionsUnit", "gram")
                    //emissions.put("emissionsType", emissionsType)
                    //emissions.put("programType", programType)
                    
                    return emissions
            }
         */
        }
        //return theEmissionsDetails
        //--if statement for new programType and emissionsType
    }
}