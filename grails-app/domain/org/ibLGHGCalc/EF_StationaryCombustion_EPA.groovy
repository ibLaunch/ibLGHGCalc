package org.ibLGHGCalc

class EF_StationaryCombustion_EPA {

    String fuelType
    String fuelUnit
    Double CO2MultiplierInKg
    Double CH4MultiplierInGram
    Double N20MultiplierInGram

    Boolean isPublic = Boolean.TRUE
  
    static constraints = {
            fuelType(blank:false)
            fuelUnit(blank:false)
            CO2MultiplierInKg(blank:false)
            CH4MultiplierInGram(blank:false)
            N20MultiplierInGram(blank:false)
    }
}
