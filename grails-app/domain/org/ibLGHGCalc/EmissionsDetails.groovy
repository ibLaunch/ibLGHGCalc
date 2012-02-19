package org.ibLGHGCalc

class EmissionsDetails {
    static belongsTo = [stationaryCombustionInfo: StationaryCombustionInfo,
                        mobileCombustionInfo: MobileCombustionInfo,
                        refridgerationAirConditioningInfo:RefridgerationAirConditioningInfo,
                        purchasedElectricityInfo:PurchasedElectricityInfo,
                        purchasedSteamInfo:PurchasedSteamInfo,
                        wasteStreamCombustionInfo:WasteStreamCombustionInfo,
                        optionalSourceInfo:OptionalSourceInfo,
                        purchasedProductInfo:PurchasedProductInfo,
                        materialUsedBy_T1S_Info:MaterialUsedBy_T1S_Info,
                        materialTransportTo_T1S_Info:MaterialTransportTo_T1S_Info,
                        wasteOutputFrom_T1S_Info:WasteOutputFrom_T1S_Info,
                        purchasedEnergyInfo:PurchasedEnergyInfo,
                        //upstreamEmissionsFromPurchasedEnergyInfo:UpstreamEmissionsFromPurchasedEnergyInfo
                        //upstreamEmissionsFromPurchasedFuelInfo:UpstreamEmissionsFromPurchasedFuelInfo,
                        //upstreamEmissionsPurchasedElectricityInfo:UpstreamEmissionsPurchasedElectricityInfo,
                        //emissionsFromPowerPurchasedAndSoldInfo:EmissionsFromPowerPurchasedAndSoldInfo,
                        //emissionsFromTransAndDistLossesInfo:EmissionsFromTransAndDistLossesInfo                        
                        transportationInfo:TransportationInfo,
                        distributionInfo:DistributionInfo,
                        wasteGeneratedInOperationsInfo:WasteGeneratedInOperationsInfo,
                        businessTravelInfo:BusinessTravelInfo,
                        leasedAssetsInfo:LeasedAssetsInfo,
                        processingOfSoldProductsInfo:ProcessingOfSoldProductsInfo,
                        directUsePhaseEmissionsForSoldProductsInfo:DirectUsePhaseEmissionsForSoldProductsInfo,
                        inDirectUsePhaseEmissionsForSoldProductsInfo:InDirectUsePhaseEmissionsForSoldProductsInfo,
                        
                        endOfLifeTreatmentOfSoldProductsInfo:EndOfLifeTreatmentOfSoldProductsInfo,
                        franchisesInfo:FranchisesInfo,
                        investmentsInfo:InvestmentsInfo
                        ]
    
    Double CO2Emissions
    String CO2EmissionsUnit

    Double CH4Emissions
    String CH4EmissionsUnit

    Double N2OEmissions
    String N2OEmissionsUnit

    Double biomassCO2Emissions
    String biomassCO2EmissionsUnit

    Double combinedEmissions  //this field is typcally used when no CO2/CH4,N2O or other individual emissions of gases is available.
    String combinedEmissionsUnit

    Double anticipatedLifetimeProjectInvestmentsEmissions  //this field is  used for anticipated Lifetime Project Investments. This is separately reported.
    String anticipatedLifetimeProjectInvestmentsEmissionsUnit
    
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
        purchasedProductInfo(nullable:true)
        materialUsedBy_T1S_Info(nullable:true)
        materialTransportTo_T1S_Info(nullable:true)
        wasteOutputFrom_T1S_Info(nullable:true)
        
        purchasedEnergyInfo(nullable:true)        
        //upstreamEmissionsFromPurchasedFuelInfo(nullable:true)
        //upstreamEmissionsPurchasedElectricityInfo(nullable:true)
        //emissionsFromPowerPurchasedAndSoldInfo(nullable:true)
        //emissionsFromTransAndDistLossesInfo(nullable:true)
        
        transportationInfo(nullable:true)
        distributionInfo(nullable:true)
        wasteGeneratedInOperationsInfo(nullable:true)
        businessTravelInfo(nullable:true)
        leasedAssetsInfo(nullable:true)
        processingOfSoldProductsInfo(nullable:true)
        directUsePhaseEmissionsForSoldProductsInfo(nullable:true)
        inDirectUsePhaseEmissionsForSoldProductsInfo(nullable:true)
        
        endOfLifeTreatmentOfSoldProductsInfo(nullable:true)
        
        franchisesInfo(nullable:true)
        
        investmentsInfo(nullable:true)
        
        CO2Emissions(nullable:true)
        CH4Emissions(nullable:true)
        N2OEmissions(nullable:true)        
        biomassCO2Emissions(nullable:true, default:0)

        CO2EmissionsUnit(nullable:true)
        CH4EmissionsUnit(nullable:true)
        N2OEmissionsUnit(nullable:true)        
        biomassCO2EmissionsUnit(nullable:true)
        
        combinedEmissions(nullable:true)
        combinedEmissionsUnit(nullable:true)

        anticipatedLifetimeProjectInvestmentsEmissions(nullable:true)
        anticipatedLifetimeProjectInvestmentsEmissionsUnit(nullable:true)
        
	programType(nullable:true)
        emissionsType(nullable:true)
    }
}
