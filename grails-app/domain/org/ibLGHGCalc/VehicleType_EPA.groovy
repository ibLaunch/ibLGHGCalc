package org.ibLGHGCalc

class VehicleType_EPA {
    String vehicleType
    String fuelUnit
    String fuelType

    static constraints = {
            vehicleType(blank:false)
            fuelUnit(blank:false)
            fuelType(blank:false)
    }
}