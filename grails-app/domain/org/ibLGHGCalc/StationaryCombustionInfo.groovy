package org.ibLGHGCalc

class StationaryCombustionInfo {
    String fuelSourceDescription
    String fuelType
    Float fuelQuantity
    String fuelUnit

    Boolean isPublic = Boolean.TRUE

    static constraints = {
            fuelSourceDescription(blank:false, maxsize:255)
            fuelType(blank:false)
            fuelQuantity(blank:false)
            fuelUnit(blank:false)
    }
}
