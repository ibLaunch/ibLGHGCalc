package org.ibLGHGCalc

class EF_CO2_MobileCombustion_EPA {
    //static searchable = true
    String fuelType
    String fuelNameInVehicleType
    String fuelUnit
    Double CO2MultiplierInKg
    Double biomassCO2MultiplierInKg

    static constraints = {
            fuelType(blank:false)
            fuelNameInVehicleType(blank:false)
            fuelUnit(blank:false)
            CO2MultiplierInKg(nullable:false)
            biomassCO2MultiplierInKg(nullable:true)
    }
}