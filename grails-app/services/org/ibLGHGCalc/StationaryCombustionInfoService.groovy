package org.ibLGHGCalc

class StationaryCombustionInfoService {

    static transactional = true

  
    StationaryCombustionInfo findStationaryCombustionInfo(Long id) {
      StationaryCombustionInfo.get(id)
    }

    def StationaryCombustionInfo[] findStationaryCombustionInfos() {
      StationaryCombustionInfo.list()
    }

    def StationaryCombustionInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      //- Getting the emission Factors
      def theEF_StationaryCombustion_EPA =  EF_StationaryCombustion_EPA.findByFuelType(parameters.fuelType)
      //-Convert parameters  fuelQuantity to Double
      Double  fuelQty = (parameters.fuelQuantity).toDouble()
      //-Define emissions object
      def theStationaryCombustionEmissions

      def theStationaryCombustionInfo = StationaryCombustionInfo.get(parameters.id)
      if (!theStationaryCombustionInfo) {

        theStationaryCombustionInfo = new StationaryCombustionInfo()
        //--Implement proper code for programType-??
        theStationaryCombustionEmissions = new StationaryCombustionEmissions(
              CO2Emissions: fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg,
              CH4Emissions: fuelQty*theEF_StationaryCombustion_EPA.CH4MultiplierInGram,
              N2OEmissions: fuelQty*theEF_StationaryCombustion_EPA.N2OMultiplierInGram,
              CO2EMissionsUnit:"Kg",
              CH4EMissionsUnit:"Gram",
              N2OEMissionsUnit:"Gram",
              programType:"EPA Climate Leaders"
        )
        theStationaryCombustionInfo.addToEmissionsByProgramTypes(theStationaryCombustionEmissions)
      }
      else
      {
        theStationaryCombustionInfo.emissionsByProgramTypes.each{
          theStationaryCombustionEmissions = it
          //--Implement proper code for programType-??
          if (theStationaryCombustionEmissions.programType.equals("EPA Climate Leaders")){
                theStationaryCombustionEmissions.CO2Emissions = fuelQty*theEF_StationaryCombustion_EPA.CO2MultiplierInKg
                theStationaryCombustionEmissions.CH4Emissions = fuelQty*theEF_StationaryCombustion_EPA.CH4MultiplierInGram
                theStationaryCombustionEmissions.N2OEmissions = fuelQty*theEF_StationaryCombustion_EPA.N2OMultiplierInGram
          }
        }
      }

      theStationaryCombustionInfo.properties = parameters;
      theStationaryCombustionInfo.isPublic =
         "true".equals(parameters.isPublic) ? true : false

      theStationaryCombustionInfo.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      StationaryCombustionInfo.get(parameters.id)?.delete()
    }
}
