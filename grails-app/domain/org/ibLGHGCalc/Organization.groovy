package org.ibLGHGCalc

class Organization {

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
    Date dateCreated
    Date lastUpdated
    
    static hasMany = [stationaryCombustionInfoList:StationaryCombustionInfo, 
                      emissionsSummaryList:EmissionsSummary,
                      mobileCombustionInfoList:MobileCombustionInfo,
                      refridgerationAirConditioningInfoList:RefridgerationAirConditioningInfo,
                      purchasedElectricityInfoList:PurchasedElectricityInfo,
                      purchasedSteamInfoList:PurchasedSteamInfo,
                      optionalSourceInfoList:OptionalSourceInfo,
                      wasteStreamCombustionInfoList:WasteStreamCombustionInfo
                     ]

    static constraints = {
        organizationName(blank:false, unique:true)

        currentInventoryBeginDate(nullable:true)
        currentInventoryEndDate(nullable:true)
        organizationStreetAddress1(nullable:true)
        organizationStreetAddress2(nullable:true)
        organizationCity(nullable:true)
        organizationState(nullable:true)
        organizationZipCode(nullable:true)
        organizationCountry(nullable:true)
        organizationWebsite(nullable:true)
        organizationHQ(nullable:true)
        pointOfContact(nullable:true)

        stationaryCombustionInfoList(nullable:true)
        emissionsSummaryList(nullable:true)
        mobileCombustionInfoList (nullable:true)
        purchasedElectricityInfoList (nullable:true)
        purchasedSteamInfoList (nullable:true)
        optionalSourceInfoList (nullable:true)
        wasteStreamCombustionInfoList (nullable:true)
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
        //tablePerHierarchy false
    }
}
