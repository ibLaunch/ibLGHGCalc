package org.ibLGHGCalc

class EF_PurchasedElectricity_EPA {

    String eGRIDSubregion
    Double CO2Multiplier
    Double CH4Multiplier
    Double N2OMultiplier

    String CO2MultiplierUnit="lb CO2/MWh"
    String CH4MultiplierUnit="lb CH4/MWh"
    String N2OMultiplierUnit="lb N2O/MWh"

    String dataYear="2005"
    static constraints = {
            eGRIDSubregion(blank:false)

            CO2Multiplier(nullable:false)
            CH4Multiplier(nullable:false)
            N2OMultiplier(nullable:false)

            CO2MultiplierUnit(blank:true)
            CH4MultiplierUnit(blank:true)
            N2OMultiplierUnit(blank:true)

            dataYear(blank:true)
    }
}
