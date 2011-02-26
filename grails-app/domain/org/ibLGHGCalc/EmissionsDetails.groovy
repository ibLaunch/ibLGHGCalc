package org.ibLGHGCalc

class EmissionsDetails {
    static belongsTo = [stationaryCombustionInfo: StationaryCombustionInfo,
                        mobileCombustionInfo: MobileCombustionInfo,
                        refridgerationAirConditioningInfo:RefridgerationAirConditioningInfo,
                        purchasedElectricityInfo:PurchasedElectricityInfo,
                        purchasedSteamInfo:PurchasedSteamInfo,
                        wasteStreamCombustionInfo:WasteStreamCombustionInfo,
                        optionalSourceInfo:OptionalSourceInfo
                        ]
    
    Double CO2Emissions
    String CO2EmissionsUnit

    Double CH4Emissions
    String CH4EmissionsUnit

    Double N2OEmissions
    String N2OEmissionsUnit

    Double biomassCO2Emissions
    String biomassCO2EmissionsUnit

    String programType
    String emissionsType

    static constraints = {
        //organization(nullable:false)
        stationaryCombustionInfo (nullable:true)
        mobileCombustionInfo (nullable:true)
        refridgerationAirConditioningInfo(nullable:true)
        purchasedElectricityInfo(nullable:true)
        purchasedSteamInfo(nullable:true)
        wasteStreamCombustionInfo(nullable:true)
        optionalSourceInfo (nullable:true)
        
        CO2Emissions(nullable:true)
        CH4Emissions(nullable:true)
        N2OEmissions(nullable:true)
        biomassCO2Emissions(nullable:true, default:0)

        CO2EmissionsUnit(blank:true)
        CH4EmissionsUnit(blank:true)
	N2OEmissionsUnit(blank:true)
        biomassCO2EmissionsUnit(blank:true)

	programType(blank:true)
        emissionsType(blank:true)
    }
}
