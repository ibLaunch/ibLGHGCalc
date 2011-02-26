package org.ibLGHGCalc

import jxl.*

class LoadEmissionFactorsService {

  static transactional = true
  static expose = ["gwt:org.ibLGHGCalc.client"]
  def stationaryCombustionInfoService
  def mobileCombustionInfoService
  def refridgerationAirConditioningInfoService
  def purchasedElectricityInfoService
  def purchasedSteamInfoService
  def optionalSourceInfoService
  
  String emissionFactorFileUpload(Map<String, String> parameters) {
      WorkbookSettings workbookSettings
      Workbook workbook
      String returnString = ""

      File excelFile = new File('C:/GHG/uploadedFiles/NewfileName3.xls')
      println "I am in service method"
      def downloadedfile = request.getFile("fileUpload")
      downloadedfile.transferTo(excelFile)

      workbookSettings = new WorkbookSettings()
      workbookSettings.setLocale(new Locale("en", "EN"))
      workbook = Workbook.getWorkbook(excelFile, workbookSettings)

      Sheet sheet = workbook.getSheet(0)
      println "parameters file Type is:  " + parameters.fileType
      //String fileType = parameters.fileType.toString()

      //-temporary placeholder for programType -- This needs to be automated ??
      String programType = "EPA Climate Leaders"

      String emissionsType
      if (parameters.fileType.contains("Refridgeration Air Conditioning")){
            emissionsType = "Refridgeration And Air Conditioning"
      } else if (parameters.fileType.contains("Fire Suppression")){
            emissionsType = "Fire Suppression"
      } else if (parameters.fileType.contains("Mobile Combustion")){
            emissionsType = "Mobile Combustion"
      } else if (parameters.fileType.contains("Stationary Combustion")){
            emissionsType = "Stationary Combustion"
      } else if (parameters.fileType.contains("Purchased Electricity")){
            emissionsType = "Purchased Electricity"
      } else if (parameters.fileType.contains("Purchased Steam")){
            emissionsType = "Purchased Steam"
      } else if (parameters.fileType.contains("Employee Business Travel - By Vehicle")){
            emissionsType = "Employee Business Travel - By Vehicle"
      } else if (parameters.fileType.contains("Employee Business Travel - By Rail")){
            emissionsType = "Employee Business Travel - By Rail"
      } else if (parameters.fileType.contains("Employee Business Travel - By Bus")){
            emissionsType = "Employee Business Travel - By Bus"
      } else if (parameters.fileType.contains("Employee Business Travel - By Air")){
            emissionsType = "Employee Business Travel - By Air"
      } else if (parameters.fileType.contains("Employee Commuting - By Vehicle")){
            emissionsType = "Employee Commuting - By Vehicle"
      } else if (parameters.fileType.contains("Employee Commuting - By Rail")){
            emissionsType = "Employee Commuting - By Rail"
      } else if (parameters.fileType.contains("Employee Commuting - By Bus")){
            emissionsType = "Employee Commuting - By Bus"
      } else if (parameters.fileType.contains("Product Transport - By Vehicle")){
            emissionsType = "Product Transport - By Vehicle"
      } else if (parameters.fileType.contains("Product Transport - By Heavy Duty Trucks")){
            emissionsType = "Product Transport - By Heavy Duty Trucks"
      } else if (parameters.fileType.contains("Product Transport - By Rail")){
            emissionsType = "Product Transport - By Rail"
      } else if (parameters.fileType.contains("Product Transport - By Water or Air")){
            emissionsType = "Product Transport - By Water or Air"
      } else {
          emissionsType = "I don't know"
      }
      
      println "emissionsType : " + emissionsType
      switch (parameters.fileType){
          case "EF - EPA Stationary Combustion":
                  for (int r=1; r< sheet.rows; r++) {
                      def fuelType = sheet.getCell(0, r).contents
                      def fuelUnit = sheet.getCell(1, r).contents
                      def CO2MultiplierInKg = sheet.getCell(2, r).contents
                      def CH4MultiplierInGram = sheet.getCell(3, r).contents
                      def N2OMultiplierInGram = sheet.getCell(4, r).contents

                      def eF_StationaryCombustion_EPA = new EF_StationaryCombustion_EPA(
                                                          "fuelType": fuelType,
                                                          "fuelUnit": fuelUnit,
                                                          "CO2MultiplierInKg": CO2MultiplierInKg.toDouble(),
                                                          "CH4MultiplierInGram": CH4MultiplierInGram.toDouble(),
                                                          "N2OMultiplierInGram": N2OMultiplierInGram.toDouble() )
                       eF_StationaryCombustion_EPA.save()
                    }
                    returnString = "Emission Factors uploaded"
                    break

          case "EF - EPA Purchased Electricity":
                  for (int r=3; r< sheet.rows; r++) {
                      def eGRIDSubregion = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents
                      def dataYear = sheet.getCell(4, r).contents

                      def eF_PurchasedElectricity_EPA = new EF_PurchasedElectricity_EPA(
                                                          "eGRIDSubregion": eGRIDSubregion,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble(),
                                                          "dataYear": dataYear)
                       eF_PurchasedElectricity_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Waste Stream Combustion":
                  for (int r=2; r< sheet.rows; r++) {
                      def wasteStreamComponent = sheet.getCell(0, r).contents
                      def wasteStreamComponentChemicalFormula = sheet.getCell(1, r).contents
                      def molecularWeight = sheet.getCell(2, r).contents
                      def percentCarbon = sheet.getCell(3, r).contents

                      def eF_WasteStreamCombustion_EPA = new EF_WasteStreamCombustion_EPA(
                                                          "wasteStreamComponent": wasteStreamComponent,
                                                          "wasteStreamComponentChemicalFormula": wasteStreamComponentChemicalFormula,
                                                          "molecularWeight": molecularWeight.toDouble(),
                                                          "percentCarbon": percentCarbon.toDouble())
                       eF_WasteStreamCombustion_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break
          
          case "Data - EPA Purchased Electricity":
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def eGRIDSubregion = sheet.getCell(1, r).contents
                      def purchasedElectricity = sheet.getCell(2, r).contents
                      def purchasedElectricityUnit = sheet.getCell(3, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(5, r).contents)
                      def organizationId = sheet.getCell(6, r).contents

                      def purchasedElectricityInfo = new PurchasedElectricityInfo()
                      purchasedElectricityInfo.sourceDescription = sourceDescription
                      purchasedElectricityInfo.eGRIDSubregion = eGRIDSubregion
                      purchasedElectricityInfo.purchasedElectricity = purchasedElectricity.toDouble()
                      purchasedElectricityInfo.purchasedElectricityUnit = purchasedElectricityUnit
                      purchasedElectricityInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      purchasedElectricityInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!purchasedElectricityInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ purchasedElectricityInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      purchasedElectricityInfo.organization =  theOrganization

                      def Map <String, String> emissions = purchasedElectricityInfoService.calculateEmissions(eGRIDSubregion, purchasedElectricity.toDouble(), programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to purchasedElectricityInfo
                      purchasedElectricityInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      purchasedElectricityInfo.save(flush:true)

                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Purchased Steam":
                  for (int r=4; r< sheet.rows; r++) {
                      def fuelType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_PurchasedSteam_EPA = new EF_PurchasedSteam_EPA(
                                                          "fuelType": fuelType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_PurchasedSteam_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "Data - EPA Purchased Steam":
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def fuelType = sheet.getCell(1, r).contents
                      def boilerEfficiencyPercent = sheet.getCell(2, r).contents
                      def purchasedSteam = sheet.getCell(3, r).contents
                      def purchasedSteamUnit = sheet.getCell(4, r).contents

                      def supplierCO2Multiplier = sheet.getCell(5, r).contents
                      def supplierCO2MultiplierUnit = sheet.getCell(6, r).contents
                      def supplierCH4Multiplier = sheet.getCell(7, r).contents
                      def supplierCH4MultiplierUnit = sheet.getCell(8, r).contents
                      def supplierN2OMultiplier = sheet.getCell(9, r).contents
                      def supplierN2OMultiplierUnit = sheet.getCell(10, r).contents

                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(11, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(12, r).contents)
                      def organizationId = sheet.getCell(13, r).contents

                      def purchasedSteamInfo = new PurchasedSteamInfo()
                      purchasedSteamInfo.sourceDescription = sourceDescription
                      purchasedSteamInfo.fuelType = fuelType
                      purchasedSteamInfo.boilerEfficiencyPercent = boilerEfficiencyPercent.toDouble()
                      purchasedSteamInfo.purchasedSteam = purchasedSteam.toDouble()
                      purchasedSteamInfo.purchasedSteamUnit= purchasedSteamUnit
                      purchasedSteamInfo.supplierCO2Multiplier = supplierCO2Multiplier.toDouble()
                      purchasedSteamInfo.supplierCO2MultiplierUnit= supplierCO2MultiplierUnit
                      purchasedSteamInfo.supplierCH4Multiplier = supplierCH4Multiplier.toDouble()
                      purchasedSteamInfo.supplierCH4MultiplierUnit= supplierCH4MultiplierUnit
                      purchasedSteamInfo.supplierN2OMultiplier = supplierN2OMultiplier.toDouble()
                      purchasedSteamInfo.supplierN2OMultiplierUnit= supplierN2OMultiplierUnit

                      purchasedSteamInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      purchasedSteamInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!purchasedSteamInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ purchasedSteamInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      purchasedSteamInfo.organization =  theOrganization

                      //-create parameters map to send to calcualteEmissions()
                      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("fuelType", fuelType)
                      calculateEmissionParameters.put("boilerEfficiencyPercent",  boilerEfficiencyPercent.toDouble())
                      calculateEmissionParameters.put("purchasedSteam",  purchasedSteam.toDouble())
                      calculateEmissionParameters.put("purchasedSteamUnit",  purchasedSteamUnit)
                      //--All the supplier provided emission factors are suppose to have some value or zero
                      calculateEmissionParameters.put("supplierCO2Multiplier",  supplierCO2Multiplier.toDouble())
                      calculateEmissionParameters.put("supplierCH4Multiplier",  supplierCH4Multiplier.toDouble())
                      calculateEmissionParameters.put("supplierN2OMultiplier",  supplierN2OMultiplier.toDouble())
                      calculateEmissionParameters.put("supplierCO2MultiplierUnit",  supplierCO2MultiplierUnit)
                      calculateEmissionParameters.put("supplierCH4MultiplierUnit",  supplierCH4MultiplierUnit)
                      calculateEmissionParameters.put("supplierN2OMultiplierUnit",  supplierN2OMultiplierUnit)

                      def Map <String, String> emissions = purchasedSteamInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to purchasedSteamInfo
                      purchasedSteamInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      purchasedSteamInfo.save(flush:true)
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Vehicle Type":
                  for (int r=2; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_VehicleType_EPA = new EF_VehicleType_EPA(
                                                          "vehicleType": vehicleType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_VehicleType_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Rail Type":
                  for (int r=2; r< sheet.rows; r++) {
                      def railType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_RailType_EPA = new EF_RailType_EPA(
                                                          "railType": railType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_RailType_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Bus Type":
                  for (int r=2; r< sheet.rows; r++) {
                      def busType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_BusType_EPA = new EF_BusType_EPA(
                                                          "busType": busType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_BusType_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Air Travel Type":
                  for (int r=2; r< sheet.rows; r++) {
                      def airTravelType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_AirTravelType_EPA = new EF_AirTravelType_EPA(
                                                          "airTravelType": airTravelType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_AirTravelType_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Product Transport Vehicle Type":
                  for (int r=2; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_ProductTransport_VehicleType_EPA = new EF_ProductTransport_VehicleType_EPA(
                                                          "vehicleType": vehicleType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_ProductTransport_VehicleType_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "EF - EPA Product Transport Type":
                  for (int r=2; r< sheet.rows; r++) {
                      def transportType = sheet.getCell(0, r).contents
                      def CO2Multiplier = sheet.getCell(1, r).contents
                      def CH4Multiplier = sheet.getCell(2, r).contents
                      def N2OMultiplier = sheet.getCell(3, r).contents

                      println "CO2Multiplier: " + CO2Multiplier
                      def eF_ProductTransportType_EPA = new EF_ProductTransportType_EPA(
                                                          "transportType": transportType,
                                                          "CO2Multiplier": CO2Multiplier.toDouble(),
                                                          "CH4Multiplier": CH4Multiplier.toDouble(),
                                                          "N2OMultiplier": N2OMultiplier.toDouble()
                                                          )
                       eF_ProductTransportType_EPA.save()
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case ["Data - EPA Employee Business Travel - By Vehicle",
                "Data - EPA Employee Commuting - By Vehicle",
                "Data - EPA Product Transport - By Vehicle"]:
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def vehicleType = sheet.getCell(1, r).contents
                      def passengerMiles = sheet.getCell(2, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(3, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      def organizationId = sheet.getCell(5, r).contents

//----------Set optionalSourceType by default
                      def optionalSourceType = emissionsType

                      def theOptionalSourceInfo = new OptionalSourceInfo()
                      theOptionalSourceInfo.optionalSourceType = optionalSourceType
                      theOptionalSourceInfo.sourceDescription = sourceDescription
                      theOptionalSourceInfo.vehicleType = vehicleType
                      theOptionalSourceInfo.passengerMiles = passengerMiles.toDouble()

                      theOptionalSourceInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      theOptionalSourceInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!theOptionalSourceInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ theOptionalSourceInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theOptionalSourceInfo.organization =  theOrganization

                      //-create parameters map to send to calcualteEmissions()
                      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("vehicleType", vehicleType)
                      calculateEmissionParameters.put("passengerMiles",  passengerMiles.toDouble())

                      def Map <String, String> emissions = optionalSourceInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to theOptionalSourceInfo
                      theOptionalSourceInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theOptionalSourceInfo.save(flush:true)
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case ["Data - EPA Employee Business Travel - By Rail","Data - EPA Employee Commuting - By Rail"]:
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def railType = sheet.getCell(1, r).contents
                      def passengerMiles = sheet.getCell(2, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(3, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      def organizationId = sheet.getCell(5, r).contents

//----------Set optionalSourceType by default
                      def optionalSourceType = emissionsType

                      def theOptionalSourceInfo = new OptionalSourceInfo()
                      theOptionalSourceInfo.optionalSourceType = optionalSourceType
                      theOptionalSourceInfo.sourceDescription = sourceDescription
                      theOptionalSourceInfo.railType = railType
                      theOptionalSourceInfo.passengerMiles = passengerMiles.toDouble()

                      theOptionalSourceInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      theOptionalSourceInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!theOptionalSourceInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ theOptionalSourceInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theOptionalSourceInfo.organization =  theOrganization

                      //-create parameters map to send to calcualteEmissions()
                      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("railType", railType)
                      calculateEmissionParameters.put("passengerMiles",  passengerMiles.toDouble())

                      def Map <String, String> emissions = optionalSourceInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to theOptionalSourceInfo
                      theOptionalSourceInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theOptionalSourceInfo.save(flush:true)
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case ["Data - EPA Employee Business Travel - By Bus","Data - EPA Employee Commuting - By Bus"]:
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def busType = sheet.getCell(1, r).contents
                      def passengerMiles = sheet.getCell(2, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(3, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      def organizationId = sheet.getCell(5, r).contents

//----------Set optionalSourceType by default
                      def optionalSourceType = emissionsType

                      def theOptionalSourceInfo = new OptionalSourceInfo()
                      theOptionalSourceInfo.optionalSourceType = optionalSourceType
                      theOptionalSourceInfo.sourceDescription = sourceDescription
                      theOptionalSourceInfo.busType = busType
                      theOptionalSourceInfo.passengerMiles = passengerMiles.toDouble()

                      theOptionalSourceInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      theOptionalSourceInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!theOptionalSourceInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ theOptionalSourceInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theOptionalSourceInfo.organization =  theOrganization

                      //-create parameters map to send to calcualteEmissions()
                      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("busType", busType)
                      calculateEmissionParameters.put("passengerMiles",  passengerMiles.toDouble())

                      def Map <String, String> emissions = optionalSourceInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to theOptionalSourceInfo
                      theOptionalSourceInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theOptionalSourceInfo.save(flush:true)
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case "Data - EPA Employee Business Travel - By Air":
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def airTravelType = sheet.getCell(1, r).contents
                      def passengerMiles = sheet.getCell(2, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(3, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      def organizationId = sheet.getCell(5, r).contents

//----------Set optionalSourceType by default
                      def optionalSourceType = emissionsType

                      def theOptionalSourceInfo = new OptionalSourceInfo()
                      theOptionalSourceInfo.optionalSourceType = optionalSourceType
                      theOptionalSourceInfo.sourceDescription = sourceDescription
                      theOptionalSourceInfo.airTravelType = airTravelType
                      theOptionalSourceInfo.passengerMiles = passengerMiles.toDouble()

                      theOptionalSourceInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      theOptionalSourceInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!theOptionalSourceInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ theOptionalSourceInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theOptionalSourceInfo.organization =  theOrganization

                      //-create parameters map to send to calcualteEmissions()
                      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("airTravelType", airTravelType)
                      calculateEmissionParameters.put("passengerMiles",  passengerMiles.toDouble())

                      def Map <String, String> emissions = optionalSourceInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to theOptionalSourceInfo
                      theOptionalSourceInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theOptionalSourceInfo.save(flush:true)
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

          case ["Data - EPA Product Transport - By Heavy Duty Trucks",
                "Data - EPA Product Transport - By Rail",
                "Data - EPA Product Transport - By Water or Air"]:
                  for (int r=4; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
                      def transportType = sheet.getCell(1, r).contents
                      def tonMiles = sheet.getCell(2, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(3, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      def organizationId = sheet.getCell(5, r).contents

//----------Set optionalSourceType by default
                      def optionalSourceType = emissionsType

                      def theOptionalSourceInfo = new OptionalSourceInfo()
                      theOptionalSourceInfo.optionalSourceType = optionalSourceType
                      theOptionalSourceInfo.sourceDescription = sourceDescription
                      theOptionalSourceInfo.transportType = transportType
                      theOptionalSourceInfo.tonMiles = tonMiles.toDouble()

                      theOptionalSourceInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      theOptionalSourceInfo.fuelUsedEndDate = fuelUsedEndDate

                      if (!theOptionalSourceInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ theOptionalSourceInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theOptionalSourceInfo.organization =  theOrganization

                      //-create parameters map to send to calcualteEmissions()
                      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("transportType", transportType)
                      calculateEmissionParameters.put("tonMiles",  tonMiles.toDouble())

                      def Map <String, String> emissions = optionalSourceInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to theOptionalSourceInfo
                      theOptionalSourceInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theOptionalSourceInfo.save(flush:true)
                    }
                    returnString = parameters.fileType + " uploaded!"
                    break

            case "Stationary Combustion Info":
                  for (int r=1; r< sheet.rows; r++) {
                      def fuelQuantity = sheet.getCell(0, r).contents
                      def fuelSourceDescription = sheet.getCell(1, r).contents
                      def fuelType = sheet.getCell(2, r).contents
                      def fuelUnit = sheet.getCell(3, r).contents
                      def tempDate1 = sheet.getCell(4, r).contents
                      def tempDate2 = sheet.getCell(5, r).contents

                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(5, r).contents)

                      println "UnFormated Begin date: "+tempDate1
                      println "UnFormated End date: "+tempDate2

                      println "Formated Begin date: "+ fuelUsedBeginDate
                      println "Formated End date: "+ fuelUsedEndDate

                      def isPublic = sheet.getCell(6, r).contents
                      def organizationId = sheet.getCell(7, r).contents
                      Double fuelQty = fuelQuantity.toDouble()

                      def stationaryCombustionInfo = new StationaryCombustionInfo()
                      stationaryCombustionInfo.fuelQuantity = fuelQty
                      stationaryCombustionInfo.fuelSourceDescription = fuelSourceDescription
                      stationaryCombustionInfo.fuelType = fuelType
                      stationaryCombustionInfo.fuelUnit = fuelUnit
                      stationaryCombustionInfo.fuelUsedBeginDate = fuelUsedBeginDate
                      stationaryCombustionInfo.fuelUsedEndDate = fuelUsedEndDate
                      stationaryCombustionInfo.isPublic = isPublic

                      if (!stationaryCombustionInfo) {
                          println "It is  null"
                      }
                      else{
                           println "object is: "+ stationaryCombustionInfo
                      }
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      stationaryCombustionInfo.organization =  theOrganization 
                      //-temporary placeholder for programType -- This needs to be automated ??
                      //String programType = "EPA Climate Leaders"
                      //String emissionsType = "Stationary Combustion"
                      //def theEmissionsDetails = stationaryCombustionInfoService.createEmissionsDetails(fuelType, fuelQty, programType)
                      def Map <String, String> emissions = stationaryCombustionInfoService.calculateEmissions(fuelType, fuelQty, programType, emissionsType)
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to stationaryCombustionInfo
                      stationaryCombustionInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      stationaryCombustionInfo.save(flush:true)
                  }                    
                  returnString = "Stationary Combustion Info uploaded"
                  break

           case "Mobile Combustion Info":
                  for (int r=1; r< sheet.rows; r++) {
                      def fuelSourceDescription = sheet.getCell(0, r).contents
		      def vehicleType = sheet.getCell(1, r).contents
		      def vehicleYear = sheet.getCell(2, r).contents
                      def fuelType = sheet.getCell(3, r).contents
                      def fuelUnit = sheet.getCell(4, r).contents
                      def fuelQuantity = sheet.getCell(5, r).contents
                      def milesTravelled = sheet.getCell(6, r).contents
                      def bioFuelPercent = sheet.getCell(7, r).contents
                      def ethanolPercent = sheet.getCell(8, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(9, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(10, r).contents)
                      def organizationId = sheet.getCell(11, r).contents

                      Double fuelQuantityDouble = fuelQuantity.toDouble()
		      Double milesTravelledDouble = milesTravelled.toDouble()
		      Double bioFuelPercentDouble = bioFuelPercent.toDouble()
		      Double ethanolPercentDouble = ethanolPercent.toDouble()

                      //Create mobileCombustionInfo object
                      def theMobileCombustionInfo = new MobileCombustionInfo()
		      theMobileCombustionInfo.fuelSourceDescription = fuelSourceDescription
		      theMobileCombustionInfo.vehicleType = vehicleType
		      theMobileCombustionInfo.vehicleYear = vehicleYear
		      theMobileCombustionInfo.fuelType = fuelType
		      theMobileCombustionInfo.fuelQuantity = fuelQuantityDouble
		      theMobileCombustionInfo.fuelUnit = fuelUnit
		      theMobileCombustionInfo.milesTravelled = milesTravelledDouble
		      theMobileCombustionInfo.bioFuelPercent = bioFuelPercentDouble
		      theMobileCombustionInfo.ethanolPercent = ethanolPercentDouble
		      theMobileCombustionInfo.fuelUsedBeginDate = fuelUsedBeginDate
		      theMobileCombustionInfo.fuelUsedEndDate = fuelUsedEndDate

                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theMobileCombustionInfo.organization =  theOrganization

                      //-temporary placeholder for programType -- This needs to be automated ??
                      //String programType = "EPA Climate Leaders"
                      //String emissionsType = "Mobile Combustion"

                      //Calculate Emissions
          	      def Map <String, String> emissions = mobileCombustionInfoService.calculateEmissions(vehicleType, vehicleYear, fuelType, fuelQuantityDouble, milesTravelledDouble, programType, emissionsType, bioFuelPercentDouble, ethanolPercentDouble)
		      println "Called calculateEmissions !"
                      
                      //Store emissions details
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to stationaryCombustionInfo
                      theMobileCombustionInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theMobileCombustionInfo.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info uploaded"
                  break

          case "Mobile Combustion Info - EF_CO2_MobileCombustion_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def fuelType = sheet.getCell(0, r).contents
                      def fuelNameInVehicleType = sheet.getCell(1, r).contents
                      def fuelUnit = sheet.getCell(2, r).contents
                      def CO2MultiplierInKg = sheet.getCell(3, r).contents
                      def biomassCO2MultiplierInKg = sheet.getCell(4, r).contents

                      def eF_CO2_MobileCombustion_EPA = new EF_CO2_MobileCombustion_EPA()
                      eF_CO2_MobileCombustion_EPA.fuelType = fuelType
                      eF_CO2_MobileCombustion_EPA.fuelNameInVehicleType = fuelNameInVehicleType
                      eF_CO2_MobileCombustion_EPA.fuelUnit = fuelUnit
                      eF_CO2_MobileCombustion_EPA.CO2MultiplierInKg = CO2MultiplierInKg.toDouble()
                      if (biomassCO2MultiplierInKg){
                          eF_CO2_MobileCombustion_EPA.biomassCO2MultiplierInKg = biomassCO2MultiplierInKg.toDouble()
                      }

                      eF_CO2_MobileCombustion_EPA.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info - EF_CO2_MobileCombustion_EPA uploaded"
                  break

          case "Mobile Combustion Info - VehicleType_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def fuelUnit = sheet.getCell(1, r).contents
		      def fuelType = sheet.getCell(2, r).contents

                      def vehicleType_EPA = new VehicleType_EPA()
                      vehicleType_EPA.vehicleType = vehicleType
                      vehicleType_EPA.fuelUnit = fuelUnit
                      vehicleType_EPA.fuelType = fuelType

                      vehicleType_EPA.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info - VehicleType_EPA uploaded"
                  break

          case "Mobile Combustion Info - EF_CH4N2O_MobileCombustionGasoline_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def vehicleYear = sheet.getCell(1, r).contents
                      def N2OMultiplierInGram = sheet.getCell(2, r).contents
                      def CH4MultiplierInGram = sheet.getCell(3, r).contents

                      def eF_CH4N2O_MobileCombustionGasoline_EPA = new EF_CH4N2O_MobileCombustionGasoline_EPA()
                      eF_CH4N2O_MobileCombustionGasoline_EPA.vehicleType = vehicleType
                      eF_CH4N2O_MobileCombustionGasoline_EPA.vehicleYear = vehicleYear
                      eF_CH4N2O_MobileCombustionGasoline_EPA.CH4MultiplierInGram = CH4MultiplierInGram.toDouble()
                      eF_CH4N2O_MobileCombustionGasoline_EPA.N2OMultiplierInGram = N2OMultiplierInGram.toDouble()

                      eF_CH4N2O_MobileCombustionGasoline_EPA.save()
                  }
                  returnString = "Mobile Combustion Info - EF_CH4N2O_MobileCombustionGasoline_EPA uploaded"
                  break

          case "Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonGasoline_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def vehicleYear = sheet.getCell(1, r).contents
                      def N2OMultiplierInGram = sheet.getCell(2, r).contents
                      def CH4MultiplierInGram = sheet.getCell(3, r).contents
                      //println vehicleType +" "+vehicleYear + " " + N2OMultiplierInGram+ " "  + CH4MultiplierInGram

                      def eF_CH4N2O_MobileCombustionNonGasoline_EPA = new EF_CH4N2O_MobileCombustionNonGasoline_EPA()
                      eF_CH4N2O_MobileCombustionNonGasoline_EPA.vehicleType = vehicleType
                      eF_CH4N2O_MobileCombustionNonGasoline_EPA.vehicleYear = vehicleYear
                      eF_CH4N2O_MobileCombustionNonGasoline_EPA.CH4MultiplierInGram = CH4MultiplierInGram.toDouble()
                      eF_CH4N2O_MobileCombustionNonGasoline_EPA.N2OMultiplierInGram = N2OMultiplierInGram.toDouble()

                      try {
                          println "I am about to save"
                          eF_CH4N2O_MobileCombustionNonGasoline_EPA.save()
                          println "I just saved" + eF_CH4N2O_MobileCombustionNonGasoline_EPA

                      }
                      catch (Exception e){
                          println "----------Not Saved------------"
                          println e
                      }

                  }
                  returnString = "Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonGasoline_EPA uploaded"
                  break

          case "Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonHighway_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def N2OMultiplierInGram = sheet.getCell(1, r).contents
                      def CH4MultiplierInGram = sheet.getCell(2, r).contents
                      def N2OMultiplierInGramFormula = sheet.getCell(3, r).contents
                      def CH4MultiplierInGramFormula = sheet.getCell(4, r).contents

                      //println vehicleType + " " + N2OMultiplierInGram+ " "  + CH4MultiplierInGram + " " + N2OMultiplierInGramFormula + " "  + CH4MultiplierInGramFormula

                      def eF_CH4N2O_MobileCombustionNonHighway_EPA = new EF_CH4N2O_MobileCombustionNonHighway_EPA()
                      eF_CH4N2O_MobileCombustionNonHighway_EPA.vehicleType = vehicleType
                      eF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGram = CH4MultiplierInGram.toDouble()
                      eF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGram = N2OMultiplierInGram.toDouble()
                      eF_CH4N2O_MobileCombustionNonHighway_EPA.CH4MultiplierInGramFormula = CH4MultiplierInGramFormula
                      eF_CH4N2O_MobileCombustionNonHighway_EPA.N2OMultiplierInGramFormula = N2OMultiplierInGramFormula

                      eF_CH4N2O_MobileCombustionNonHighway_EPA.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonHighway_EPA uploaded"
                  break

          case "Mobile Combustion Info - EF_CH4N2O_MobileCombustion_EPA":
                  println "I am in......."
                  for (int r=1; r< sheet.rows; r++) {
                      def vehicleType = sheet.getCell(0, r).contents
                      def vehicleYear = sheet.getCell(1, r).contents
                      def N2OMultiplierInGram = sheet.getCell(2, r).contents
                      def CH4MultiplierInGram = sheet.getCell(3, r).contents
                      def N2OMultiplierInGramFormula = sheet.getCell(4, r).contents
                      def CH4MultiplierInGramFormula = sheet.getCell(5, r).contents
                      println vehicleType + " " + vehicleYear

                      def EF_CH4N2O_MobileCombustion_EPA = new EF_CH4N2O_MobileCombustion_EPA()
                      EF_CH4N2O_MobileCombustion_EPA.vehicleType = vehicleType
                      EF_CH4N2O_MobileCombustion_EPA.vehicleYear = vehicleYear
                      EF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram = CH4MultiplierInGram.toDouble()
                      EF_CH4N2O_MobileCombustion_EPA.N2OMultiplierInGram = N2OMultiplierInGram.toDouble()
                      EF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGramFormula = CH4MultiplierInGramFormula
                      EF_CH4N2O_MobileCombustion_EPA.N2OMultiplierInGramFormula = N2OMultiplierInGramFormula
                      EF_CH4N2O_MobileCombustion_EPA.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info - EF_CH4N2O_MobileCombustion_EPA uploaded"
                  break

          case "RefridgerationAirConditioning - GWP_RefridgerationAirConditioning_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def gasType = sheet.getCell(0, r).contents
                      def gasTypeGWP = sheet.getCell(1, r).contents

                      def gWP_RefridgerationAirConditioning_EPA = new GWP_RefridgerationAirConditioning_EPA()
                      gWP_RefridgerationAirConditioning_EPA.gasType = gasType
                      gWP_RefridgerationAirConditioning_EPA.gasTypeGWP = gasTypeGWP.toDouble()

                      gWP_RefridgerationAirConditioning_EPA.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info - GWP_RefridgerationAirConditioning_EPA uploaded"
                  break



          case "RefridgerationAirConditioning - EquipmentCapacityRange_EPA":
                  for (int r=1; r< sheet.rows; r++) {
                      def typeOfEquipment = sheet.getCell(0, r).contents
                      def capacityRange = sheet.getCell(1, r).contents
		      def kFactor = sheet.getCell(2, r).contents
		      def xFactor = sheet.getCell(3, r).contents
		      def yFactor = sheet.getCell(4, r).contents
		      def zFactor = sheet.getCell(5, r).contents

                      def equipmentCapacityRange_EPA = new EquipmentCapacityRange_EPA()
                      equipmentCapacityRange_EPA.typeOfEquipment = typeOfEquipment
                      equipmentCapacityRange_EPA.capacityRange = capacityRange
                      equipmentCapacityRange_EPA.kFactor = kFactor.toDouble()
                      equipmentCapacityRange_EPA.xFactor = xFactor.toDouble()
                      equipmentCapacityRange_EPA.yFactor = yFactor.toDouble()
                      equipmentCapacityRange_EPA.zFactor = zFactor.toDouble()

                      equipmentCapacityRange_EPA.save(flush:true)
                  }
                  returnString = "Mobile Combustion Info - EquipmentCapacityRange_EPA uploaded"
                  break

           case ["Refridgeration Air Conditioning - Company-Wide Material Balance Method","Fire Suppression - Company-Wide Material Balance Method" ]:

                  for (int r=4; r< sheet.rows; r++) {
                      println "I am inside RefridgerationAirConditioning - Material Balance"
                      def gasType = sheet.getCell(0, r).contents
		      def inventoryChange = sheet.getCell(1, r).contents
		      def transferredAmount = sheet.getCell(2, r).contents
                      def capacityChange = sheet.getCell(3, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(4, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(5, r).contents)
                      def organizationId = sheet.getCell(6, r).contents

                      //Create theRefridgerationAirConditioningInfo object
                      def theRefridgerationAirConditioningInfo = new RefridgerationAirConditioningInfo()
		      theRefridgerationAirConditioningInfo.gasType = gasType
		      theRefridgerationAirConditioningInfo.inventoryChange = inventoryChange.toDouble()
		      theRefridgerationAirConditioningInfo.transferredAmount = transferredAmount.toDouble()
		      theRefridgerationAirConditioningInfo.capacityChange = capacityChange.toDouble()
		      theRefridgerationAirConditioningInfo.fuelUsedBeginDate = fuelUsedBeginDate
		      theRefridgerationAirConditioningInfo.fuelUsedEndDate = fuelUsedEndDate

                      //String methodType = "Company-Wide Material Balance Method"
                      String methodType = parameters.fileType
		      theRefridgerationAirConditioningInfo.methodType = methodType

                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theRefridgerationAirConditioningInfo.organization =  theOrganization

		      //-create parameters map to send to calcualteEmissions()
		      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("gasType",gasType)
                      calculateEmissionParameters.put("methodType",methodType)
		      calculateEmissionParameters.put("inventoryChange",inventoryChange)
		      calculateEmissionParameters.put("transferredAmount",transferredAmount)
		      calculateEmissionParameters.put("capacityChange",capacityChange)

                      //Calculate Emissions
          	      def Map <String, String> emissions = refridgerationAirConditioningInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
		      println "Called calculateEmissions !"

                      //Store emissions details
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to stationaryCombustionInfo
                      theRefridgerationAirConditioningInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theRefridgerationAirConditioningInfo.save(flush:true)
                  }
                  returnString = parameters.fileType + " uploaded!"
                  break

           case ["Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method", "Fire Suppression - Company-Wide Simplified Material Balance Method"]:
                  for (int r=4; r< sheet.rows; r++) {
                      def gasType = sheet.getCell(0, r).contents
		      def newUnitsCharge = sheet.getCell(1, r).contents
		      def newUnitsCapacity = sheet.getCell(2, r).contents
                      def existingUnitsRecharge = sheet.getCell(3, r).contents
                      def disposedUnitsCapacity = sheet.getCell(4, r).contents
                      def disposedUnitsRecovered = sheet.getCell(5, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(6, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(7, r).contents)
                      def organizationId = sheet.getCell(8, r).contents

                      //Create theRefridgerationAirConditioningInfo object
                      def theRefridgerationAirConditioningInfo = new RefridgerationAirConditioningInfo()
		      theRefridgerationAirConditioningInfo.gasType = gasType
		      theRefridgerationAirConditioningInfo.newUnitsCharge = newUnitsCharge.toDouble()
		      theRefridgerationAirConditioningInfo.newUnitsCapacity = newUnitsCapacity.toDouble()
		      theRefridgerationAirConditioningInfo.existingUnitsRecharge = existingUnitsRecharge.toDouble()
		      theRefridgerationAirConditioningInfo.disposedUnitsCapacity = disposedUnitsCapacity.toDouble()
		      theRefridgerationAirConditioningInfo.disposedUnitsRecovered = disposedUnitsRecovered.toDouble()
		      theRefridgerationAirConditioningInfo.fuelUsedBeginDate = fuelUsedBeginDate
		      theRefridgerationAirConditioningInfo.fuelUsedEndDate = fuelUsedEndDate

                      //String methodType ="Company-Wide Simplified Material Balance Method"
                      String methodType = parameters.fileType
                      theRefridgerationAirConditioningInfo.methodType = methodType
                      println " This is method Type form File Type : " + methodType
                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theRefridgerationAirConditioningInfo.organization =  theOrganization

                      //-temporary placeholder for programType -- This needs to be automated ??
                      //String programType = "EPA Climate Leaders"
                      //String emissionsType = "Refridgeration And Air Conditioning"

		      //-create parameters map to send to calcualteEmissions()
		      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("gasType",gasType)
                      calculateEmissionParameters.put("methodType",methodType)
		      calculateEmissionParameters.put("newUnitsCharge",newUnitsCharge)
		      calculateEmissionParameters.put("newUnitsCapacity",newUnitsCapacity)
		      calculateEmissionParameters.put("existingUnitsRecharge",existingUnitsRecharge)
		      calculateEmissionParameters.put("disposedUnitsCapacity",disposedUnitsCapacity)
		      calculateEmissionParameters.put("disposedUnitsRecovered",disposedUnitsRecovered)

                      //Calculate Emissions
          	      def Map <String, String> emissions = refridgerationAirConditioningInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
		      println "Called calculateEmissions !"

                      //Store emissions details
                      def theEmissionsDetails = new EmissionsDetails()
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

                      println "emissions.get(CH4EmissionsUnit) " + emissions.get("CH4EmissionsUnit")
                      println "emissions.get(CH4EmissionsUnit) " + emissions.get("N2OEmissionsUnit")
                      //add the emisssions to stationaryCombustionInfo
                      theRefridgerationAirConditioningInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theRefridgerationAirConditioningInfo.save(flush:true)
                  }
                  returnString = parameters.fileType + " uploaded!"
                  break

           case "Refridgeration Air Conditioning - Source Level Screening Method":
                  for (int r=5; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
		      def typeOfEquipment = sheet.getCell(1, r).contents
		      def gasType = sheet.getCell(2, r).contents
                      def sourceNewUnitsCharge = sheet.getCell(3, r).contents
                      def operatingUnitsCapacity = sheet.getCell(4, r).contents
                      def sourceDisposedUnitsCapacity = sheet.getCell(5, r).contents
                      def timeInYearsUsed = sheet.getCell(6, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(7, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(8, r).contents)
                      def organizationId = sheet.getCell(9, r).contents

                      //Create theRefridgerationAirConditioningInfo object
                      def theRefridgerationAirConditioningInfo = new RefridgerationAirConditioningInfo()
		      theRefridgerationAirConditioningInfo.sourceDescription = sourceDescription
		      theRefridgerationAirConditioningInfo.typeOfEquipment = typeOfEquipment
		      theRefridgerationAirConditioningInfo.gasType = gasType
		      theRefridgerationAirConditioningInfo.sourceNewUnitsCharge = sourceNewUnitsCharge.toDouble()
		      theRefridgerationAirConditioningInfo.operatingUnitsCapacity = operatingUnitsCapacity.toDouble()
		      theRefridgerationAirConditioningInfo.sourceDisposedUnitsCapacity = sourceDisposedUnitsCapacity.toDouble()
		      theRefridgerationAirConditioningInfo.timeInYearsUsed = timeInYearsUsed.toDouble()
		      theRefridgerationAirConditioningInfo.fuelUsedBeginDate = fuelUsedBeginDate
		      theRefridgerationAirConditioningInfo.fuelUsedEndDate = fuelUsedEndDate

                      //String methodType = "Source Level Screening Method"
                      String methodType = parameters.fileType

		      theRefridgerationAirConditioningInfo.methodType = methodType

                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theRefridgerationAirConditioningInfo.organization =  theOrganization

                      //-temporary placeholder for programType -- This needs to be automated ??

                      //String programType = "EPA Climate Leaders"
                      //String emissionsType = "Refridgeration And Air Conditioning"

		      //-create parameters map to send to calcualteEmissions()
		      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("gasType",gasType)
                      calculateEmissionParameters.put("methodType",methodType)
                      calculateEmissionParameters.put("typeOfEquipment",typeOfEquipment)
		      calculateEmissionParameters.put("sourceNewUnitsCharge",sourceNewUnitsCharge)
		      calculateEmissionParameters.put("operatingUnitsCapacity",operatingUnitsCapacity)
		      calculateEmissionParameters.put("sourceDisposedUnitsCapacity",sourceDisposedUnitsCapacity)
		      calculateEmissionParameters.put("timeInYearsUsed",timeInYearsUsed)

                      //Calculate Emissions
          	      def Map <String, String> emissions = refridgerationAirConditioningInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
		      println "Called calculateEmissions !"

                      //Store emissions details
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to stationaryCombustionInfo
                      theRefridgerationAirConditioningInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theRefridgerationAirConditioningInfo.save(flush:true)
                  }
                  returnString = parameters.fileType + " uploaded!"
                  break

           case "Fire Suppression - Source Level Screening Method":
                  for (int r=5; r< sheet.rows; r++) {
                      def sourceDescription = sheet.getCell(0, r).contents
		      def typeOfEquipment = sheet.getCell(1, r).contents
		      def gasType = sheet.getCell(2, r).contents
                      def sourceNewUnitsCharge = sheet.getCell(3, r).contents
                      def operatingUnitsCapacity = sheet.getCell(4, r).contents
                      Date fuelUsedBeginDate = new Date().parse("MM/dd/yyyy", sheet.getCell(5, r).contents)
                      Date fuelUsedEndDate =  new Date().parse("MM/dd/yyyy", sheet.getCell(6, r).contents)
                      def organizationId = sheet.getCell(7, r).contents

                      //Create theRefridgerationAirConditioningInfo object
                      def theRefridgerationAirConditioningInfo = new RefridgerationAirConditioningInfo()
		      theRefridgerationAirConditioningInfo.sourceDescription = sourceDescription
		      theRefridgerationAirConditioningInfo.typeOfEquipment = typeOfEquipment
		      theRefridgerationAirConditioningInfo.gasType = gasType
		      theRefridgerationAirConditioningInfo.sourceNewUnitsCharge = sourceNewUnitsCharge.toDouble()
		      theRefridgerationAirConditioningInfo.operatingUnitsCapacity = operatingUnitsCapacity.toDouble()
		      theRefridgerationAirConditioningInfo.fuelUsedBeginDate = fuelUsedBeginDate
		      theRefridgerationAirConditioningInfo.fuelUsedEndDate = fuelUsedEndDate

                      //String methodType = "Source Level Screening Method"
                      String methodType = parameters.fileType                      
		      theRefridgerationAirConditioningInfo.methodType = methodType

                      //-Get the organization
                      def theOrganization = Organization.get(organizationId)
                      theRefridgerationAirConditioningInfo.organization =  theOrganization

                      //-temporary placeholder for programType -- This needs to be automated ??
                      //String programType = "EPA Climate Leaders"
                      //String emissionsType = "Refridgeration And Air Conditioning"

		      //-create parameters map to send to calcualteEmissions()
		      def calculateEmissionParameters = [:]
                      calculateEmissionParameters.put("gasType",gasType)
                      calculateEmissionParameters.put("methodType",methodType)
                      calculateEmissionParameters.put("typeOfEquipment",typeOfEquipment)
		      calculateEmissionParameters.put("sourceNewUnitsCharge",sourceNewUnitsCharge)
		      calculateEmissionParameters.put("operatingUnitsCapacity",operatingUnitsCapacity)

                      //Calculate Emissions
          	      def Map <String, String> emissions = refridgerationAirConditioningInfoService.calculateEmissions(calculateEmissionParameters, programType, emissionsType)
		      println "Called calculateEmissions !"

                      //Store emissions details
                      def theEmissionsDetails = new EmissionsDetails()
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

                      //add the emisssions to stationaryCombustionInfo
                      theRefridgerationAirConditioningInfo.addToEmissionsDetailsList(theEmissionsDetails)
                      theRefridgerationAirConditioningInfo.save(flush:true)
                  }
                  returnString = parameters.fileType + " uploaded!"
                  break

             default:
                returnString = "Incorrect File type to upload - Nothing uploaded"
      }
      return returnString
  }

    EF_StationaryCombustion_EPA findEF_StationaryCombustion_EPA(Long id) {
      EF_StationaryCombustion_EPA.get(id)
    }

    def EF_StationaryCombustion_EPA[] findEF_StationaryCombustion_EPAs() {
      EF_StationaryCombustion_EPA.list()
    }

    def EF_StationaryCombustion_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_StationaryCombustion_EPA = EF_StationaryCombustion_EPA.get(parameters.id)
      if (!theEF_StationaryCombustion_EPA) {
        theEF_StationaryCombustion_EPA = new EF_StationaryCombustion_EPA()
      }
      theEF_StationaryCombustion_EPA.properties = parameters;
      theEF_StationaryCombustion_EPA.isPublic =
         "true".equals(parameters.isPublic) ? true : false
      theEF_StationaryCombustion_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_StationaryCombustion_EPA.get(parameters.id)?.delete()
    }
}
