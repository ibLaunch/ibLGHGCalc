package org.ibLGHGCalc

class EF_StationaryCombustion_EPA {
    //static searchable = true
    String fuelType
    String fuelUnit
    Double CO2MultiplierInKg
    Double CH4MultiplierInGram
    Double N2OMultiplierInGram

    Boolean isPublic = Boolean.TRUE
  
    static constraints = {
            fuelType(blank:false)
            fuelUnit(blank:false)
            CO2MultiplierInKg(nullable:false)
            CH4MultiplierInGram(nullable:false)
            N2OMultiplierInGram(nullable:false)
    }
}
