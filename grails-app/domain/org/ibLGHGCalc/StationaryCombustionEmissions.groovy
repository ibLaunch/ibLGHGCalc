package org.ibLGHGCalc

class StationaryCombustionEmissions {

    static belongsTo = [stationaryCombustionInfo: StationaryCombustionInfo]

    Double CO2Emissions
    String CO2EMissionsUnit

    Double CH4Emissions
    String CH4EMissionsUnit

    Double N2OEmissions
    String N2OEMissionsUnit

    String programType

    static constraints = {
        stationaryCombustionInfo (nullable:true)
        CO2Emissions(nullable:true)
        CH4Emissions(nullable:true)
        N2OEmissions(nullable:true)
        CO2EMissionsUnit(nullable:true)
        CH4EMissionsUnit(nullable:true)
	N2OEMissionsUnit(nullable:true)
	programType(nullable:true)
    }
}
