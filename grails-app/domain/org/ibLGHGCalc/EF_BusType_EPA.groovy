package org.ibLGHGCalc

class EF_BusType_EPA {

    String busType
    Double CO2Multiplier
    Double CH4Multiplier
    Double N2OMultiplier

    String CO2MultiplierUnit="?"
    String CH4MultiplierUnit="?"
    String N2OMultiplierUnit="?"

    static constraints = {
            busType(blank:false)

            CO2Multiplier(nullable:false)
            CH4Multiplier(nullable:false)
            N2OMultiplier(nullable:false)

            CO2MultiplierUnit(blank:true)
            CH4MultiplierUnit(blank:true)
            N2OMultiplierUnit(blank:true)
    }
}
