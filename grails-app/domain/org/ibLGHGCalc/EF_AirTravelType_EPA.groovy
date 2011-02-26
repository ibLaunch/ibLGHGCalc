package org.ibLGHGCalc

class EF_AirTravelType_EPA {

    String airTravelType
    Double CO2Multiplier
    Double CH4Multiplier
    Double N2OMultiplier

    String CO2MultiplierUnit="?"
    String CH4MultiplierUnit="?"
    String N2OMultiplierUnit="?"

    static constraints = {
            airTravelType(blank:false)

            CO2Multiplier(nullable:false)
            CH4Multiplier(nullable:false)
            N2OMultiplier(nullable:false)

            CO2MultiplierUnit(blank:true)
            CH4MultiplierUnit(blank:true)
            N2OMultiplierUnit(blank:true)
    }
}
