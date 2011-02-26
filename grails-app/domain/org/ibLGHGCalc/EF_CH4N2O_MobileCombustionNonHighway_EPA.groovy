package org.ibLGHGCalc

class EF_CH4N2O_MobileCombustionNonHighway_EPA {
    String vehicleType
    Double CH4MultiplierInGram
    Double N2OMultiplierInGram
    String CH4MultiplierInGramFormula
    String N2OMultiplierInGramFormula

    static constraints = {
            vehicleType(blank:false)            
            CH4MultiplierInGram(nullable:false)
            N2OMultiplierInGram(nullable:false)
            CH4MultiplierInGramFormula(blank:false)
            N2OMultiplierInGramFormula(blank:false)
    }
}