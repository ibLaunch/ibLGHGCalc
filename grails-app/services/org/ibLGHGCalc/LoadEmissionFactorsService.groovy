package org.ibLGHGCalc

import jxl.*

class LoadEmissionFactorsService {

  static transactional = true
  static expose = ["gwt:org.ibLGHGCalc.client"]

  String emissionFactorFileUpload() {
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

      for (int r=1; r< sheet.rows; r++) {
              def fuelType = sheet.getCell(0, r).contents
              def fuelUnit = sheet.getCell(1, r).contents
              def CO2MultiplierInKg = sheet.getCell(2, r).contents
              def CH4MultiplierInGram = sheet.getCell(3, r).contents
              def N20MultiplierInGram = sheet.getCell(4, r).contents

              def eF_StationaryCombustion_EPA = new EF_StationaryCombustion_EPA(
              "fuelType": fuelType,
              "fuelUnit": fuelUnit,
              "CO2MultiplierInKg": CO2MultiplierInKg.toDouble(),
              "CH4MultiplierInGram": CH4MultiplierInGram.toDouble(),
              "N20MultiplierInGram": N20MultiplierInGram.toDouble() )
              eF_StationaryCombustion_EPA.save()
      }
      returnString = "Emission Factors uploaded"
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
