package org.ibLGHGCalc

class EF_ProductTransport_VehicleType_EPA {

    String vehicleType
    Double CO2Multiplier
    Double CH4Multiplier
    Double N2OMultiplier

    String CO2MultiplierUnit="?"
    String CH4MultiplierUnit="?"
    String N2OMultiplierUnit="?"

    static constraints = {
            vehicleType(blank:false)

            CO2Multiplier(nullable:false)
            CH4Multiplier(nullable:false)
            N2OMultiplier(nullable:false)

            CO2MultiplierUnit(blank:true)
            CH4MultiplierUnit(blank:true)
            N2OMultiplierUnit(blank:true)
    }
}
