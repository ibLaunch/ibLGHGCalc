package org.ibLGHGCalc

class EF_CH4N2O_MobileCombustionGasoline_EPA {
    String vehicleType
    String vehicleYear
    Double CH4MultiplierInGram
    Double N2OMultiplierInGram

    static constraints = {
            vehicleType(blank:false)
            vehicleYear(blank:false)
            CH4MultiplierInGram(nullable:false)
            N2OMultiplierInGram(nullable:false)
    }
}
