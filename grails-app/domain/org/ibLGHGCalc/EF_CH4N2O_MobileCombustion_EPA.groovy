package org.ibLGHGCalc

class EF_CH4N2O_MobileCombustion_EPA {
    String vehicleType
    String vehicleYear
    Double CH4MultiplierInGram
    Double N2OMultiplierInGram
    String CH4MultiplierInGramFormula
    String N2OMultiplierInGramFormula

    static constraints = {
            vehicleType(blank:false)
            vehicleYear(nullable:true)
            CH4MultiplierInGram(nullable:false)
            N2OMultiplierInGram(nullable:false)            
            CH4MultiplierInGramFormula(nullable:true)
            N2OMultiplierInGramFormula(nullable:true)
    }
}