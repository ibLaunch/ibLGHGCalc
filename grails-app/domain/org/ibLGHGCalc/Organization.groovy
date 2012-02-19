package org.ibLGHGCalc

class Organization {

    //static searchable = true

    String organizationName
    String organizationStreetAddress1
    String organizationStreetAddress2
    String organizationCity
    String organizationState
    String organizationZipCode
    String organizationCountry
    String organizationWebsite
    String organizationHQ
    String pointOfContact
    Date currentInventoryBeginDate
    Date currentInventoryEndDate
    String consolidationApproach //"Equity share" "Financial control" "Operational control"
    String programType
    Date dateCreated
    Date lastUpdated
    
    static hasMany = [stationaryCombustionInfoList:StationaryCombustionInfo, 
                      emissionsSummaryList:EmissionsSummary,
                      mobileCombustionInfoList:MobileCombustionInfo,
                      refridgerationAirConditioningInfoList:RefridgerationAirConditioningInfo,
                      purchasedElectricityInfoList:PurchasedElectricityInfo,
                      purchasedSteamInfoList:PurchasedSteamInfo,
                      optionalSourceInfoList:OptionalSourceInfo,
                      wasteStreamCombustionInfoList:WasteStreamCombustionInfo,
                      
                      purchasedProductInfoList:PurchasedProductInfo,
                      materialUsedBy_T1S_InfoList:MaterialUsedBy_T1S_Info,
                      materialTransportTo_T1S_InfoList:MaterialTransportTo_T1S_Info,
                      wasteOutputFrom_T1S_InfoList:WasteOutputFrom_T1S_Info,
                      
                      purchasedEnergyInfoList:PurchasedEnergyInfo,
                      //upstreamEmissionsFromPurchasedEnergyInfoList:UpstreamEmissionsFromPurchasedEnergyInfo,
                      //upstreamEmissionsFromPurchasedFuelInfoList:UpstreamEmissionsFromPurchasedFuelInfo,
                      //upstreamEmissionsPurchasedElectricityInfoList:UpstreamEmissionsPurchasedElectricityInfo,
                      //emissionsBusinessTravelInfoFromPowerPurchasedAndSoldInfoList:EmissionsFromPowerPurchasedAndSoldInfo,                      
                      //emissionsFromTransAndDistLossesInfoList:EmissionsFromTransAndDistLossesInfo,        
                      
                      transportationInfoList:TransportationInfo,
                      distributionInfoList:DistributionInfo,
                      wasteGeneratedInOperationsInfoList:WasteGeneratedInOperationsInfo,
                      businessTravelInfoList:BusinessTravelInfo,
                      leasedAssetsInfoList:LeasedAssetsInfo,
                      processingOfSoldProductsInfoList:ProcessingOfSoldProductsInfo,
                      
                      directUsePhaseEmissionsForSoldProductsInfoList:DirectUsePhaseEmissionsForSoldProductsInfo,
                      inDirectUsePhaseEmissionsForSoldProductsInfoList:InDirectUsePhaseEmissionsForSoldProductsInfo,
                      
                      endOfLifeTreatmentOfSoldProductsInfoList:EndOfLifeTreatmentOfSoldProductsInfo,
                      franchisesInfoList:FranchisesInfo,
                      
                      investmentsInfoList:InvestmentsInfo,
                      userList:SecUser
                     ]

    static constraints = {
        organizationName(blank:false, unique:true, maxsize:255)

        currentInventoryBeginDate(nullable:true)
        currentInventoryEndDate(nullable:true)
        programType(nullable:true, default:"US EPA", maxsize:255)
        organizationStreetAddress1(nullable:true, maxsize:255)
        organizationStreetAddress2(nullable:true, maxsize:255)
        organizationCity(nullable:true, maxsize:255)
        organizationState(nullable:true, maxsize:255)
        organizationZipCode(nullable:true, maxsize:255)
        organizationCountry(nullable:true, maxsize:255)
        organizationWebsite(nullable:true, maxsize:255)
        organizationHQ(nullable:true, maxsize:255)
        pointOfContact(nullable:true, maxsize:255)
        consolidationApproach(nullable:true, maxsize:255)
        
        stationaryCombustionInfoList(nullable:true)
        emissionsSummaryList(nullable:true)
        mobileCombustionInfoList (nullable:true)
        refridgerationAirConditioningInfoList(nullable:true)
        purchasedElectricityInfoList (nullable:true)
        purchasedSteamInfoList (nullable:true)
        optionalSourceInfoList (nullable:true)
        wasteStreamCombustionInfoList (nullable:true)
        
        purchasedProductInfoList(nullable:true)
        materialUsedBy_T1S_InfoList(nullable:true)
        materialTransportTo_T1S_InfoList(nullable:true)
        wasteOutputFrom_T1S_InfoList(nullable:true)

        purchasedEnergyInfoList(nullable:true)
        
        transportationInfoList(nullable:true)
        distributionInfoList(nullable:true)
        wasteGeneratedInOperationsInfoList(nullable:true)        
        businessTravelInfoList(nullable:true)
        leasedAssetsInfoList(nullable:true)
        processingOfSoldProductsInfoList(nullable:true)
                
        directUsePhaseEmissionsForSoldProductsInfoList(nullable:true)
        inDirectUsePhaseEmissionsForSoldProductsInfoList(nullable:true)
                
        endOfLifeTreatmentOfSoldProductsInfoList(nullable:true)
        
        franchisesInfoList(nullable:true)
        
        investmentsInfoList(nullable:true)
        userList (nullable:true)
    }

    static mapping = {
        stationaryCombustionInfoList cascade: "all-delete-orphan"
        emissionsSummaryList cascade: "all-delete-orphan"
        mobileCombustionInfoList cascade: "all-delete-orphan"
        refridgerationAirConditioningInfoList cascade: "all-delete-orphan"
        purchasedElectricityInfoList cascade: "all-delete-orphan"
        purchasedSteamInfoList cascade: "all-delete-orphan"
        optionalSourceInfoList cascade: "all-delete-orphan"
        wasteStreamCombustionInfoList cascade: "all-delete-orphan"

        purchasedProductInfoList cascade: "all-delete-orphan"
        materialUsedBy_T1S_InfoList cascade: "all-delete-orphan"        
        materialTransportTo_T1S_InfoList cascade: "all-delete-orphan"
        wasteOutputFrom_T1S_InfoList cascade: "all-delete-orphan"        
        
        purchasedEnergyInfoList cascade: "all-delete-orphan"
        //upstreamEmissionsFromPurchasedFuelInfoList cascade: "all-delete-orphan"
        //upstreamEmissionsPurchasedElectricityInfoList cascade: "all-delete-orphan"        
        //emissionsFromPowerPurchasedAndSoldInfoList cascade: "all-delete-orphan"
        //emissionsFromTransAndDistLossesInfoList cascade: "all-delete-orphan"        
        
        transportationInfoList cascade: "all-delete-orphan"
        distributionInfoList  cascade: "all-delete-orphan"
        wasteGeneratedInOperationsInfoList  cascade: "all-delete-orphan"
        businessTravelInfoList cascade: "all-delete-orphan"
        leasedAssetsInfoList cascade: "all-delete-orphan"
        processingOfSoldProductsInfoList cascade: "all-delete-orphan"

        directUsePhaseEmissionsForSoldProductsInfoList cascade: "all-delete-orphan"
        inDirectUsePhaseEmissionsForSoldProductsInfoList cascade: "all-delete-orphan"
        
        endOfLifeTreatmentOfSoldProductsInfoList cascade: "all-delete-orphan"
        franchisesInfoList cascade: "all-delete-orphan"
        investmentsInfoList cascade: "all-delete-orphan"
        userList cascade: "all-delete-orphan"
        
        //tablePerHierarchy false
        
    }
}
