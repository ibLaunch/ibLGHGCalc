package org.ibLGHGCalc

class EF_PurchasedSteam_EPA {

    String fuelType
    Double CO2Multiplier
    Double CH4Multiplier
    Double N2OMultiplier

    String CO2MultiplierUnit="Kg/mmBtu"
    String CH4MultiplierUnit="g/mmBtu"
    String N2OMultiplierUnit="g/mmBtu"

    static constraints = {
            fuelType(blank:false)

            CO2Multiplier(nullable:false)
            CH4Multiplier(nullable:false)
            N2OMultiplier(nullable:false)

            CO2MultiplierUnit(blank:true)
            CH4MultiplierUnit(blank:true)
            N2OMultiplierUnit(blank:true)
    }
}
