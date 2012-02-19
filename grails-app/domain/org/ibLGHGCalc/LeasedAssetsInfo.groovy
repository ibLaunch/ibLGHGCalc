package org.ibLGHGCalc

class LeasedAssetsInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription    
    String streamType   //1.upstream 2.downstream
    //---------------------------------------------------------------------------
    //Site Specific Method
    //---------------------------------------------------------------------------

    /*
    "Formula 1:
    Aggregate the scope 1 and scope 2 emissions across all of the reporting company’s leased assets. Companies then should apply the formula below to calculate emissions.
    */

    String typeOfLeasingArrangement // "finance/capital lease" or "operating lease"
    //String consolidationApproach // "Equity share or financial control approach used" or "Operational control approach used"
    String lessorOrLessee

    String leasedAssetName
    Double scope1EmissionsOfLeasedAsset
    String scope1EmissionsOfLeasedAsset_Unit

    Double scope2EmissionsOfLeasedAsset
    String scope2EmissionsOfLeasedAsset_Unit

    /*
    Formula 2:
    Companies that lease a portion of a building (e.g., an office building) where energy use is not separately sub-metered by tenant may estimate energy consumed using the reporting company’s share of the building’s total floor space and total building energy use, following this formula:
    */

    Double energyUseFromLeasedSpace 

    Double reportingCompanysArea
    String reportingCompanysArea_Unit //(m2)

    Double buildingsTotalArea
    String buildingsTotalArea_Unit //(m2)

    Double buildingsTotalEnergyUse
    String buildingsTotalEnergyUse_Unit//(kWh)

    Double buildingsOccupancyRate
    String buildingsOccupancyRate_Unit  //(e.g.0.75)

    //---------------------------------------------------------------------------
    //Average Data Method
    //---------------------------------------------------------------------------
    /*
    "Formula 1: 
    For commercial assets (office, warehouse, retail) where office space data is available
    */

    Boolean flag_floorSpaceData  //Whether floor space data is available?
    //Double emissionsFromCommercialAsset
    //String emissionsFromCommercialAsset_Unit

    Double floorSpace
    String floorSpace_Unit //(m2)

    Double average_EF
    String average_EF_Unit //(Kg CO2e/m2/year)

    //---------------------------------------------------------------------------------------
    //Formula 2: If floor space data is not available:
    //Double emissionsFromOtherAsset
    //String emissionsFromOtherAsset_Unit

    String numberOfbuildingsOrAssetTypes

    Double averageEmissionsPerBuildingOrAssetType
    String averageEmissionsPerBuildingOrAssetType_Unit //(Kg CO2e/Asset Type/year)
        
    //Other fields    
    String methodType
    String userNotesOnData  //User can keep the notes here, in terms of where the data was coming from    
    //Double calculatedEmissions  //calculated emissions based on the use data
    //String calculatedEmissions_Unit
    
    Date dataBeginDate
    Date dataEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'    
    
    static hasMany = [emissionsDetailsList: EmissionsDetails]
    
    static constraints = {
        organization(nullable:false)
        sourceDescription(nullable:true, maxsize:255)
	streamType(nullable:true, maxsize:255)
		
        typeOfLeasingArrangement(nullable:true, maxsize:255)
        //consolidationApproach (nullable:true, maxsize:255)
        lessorOrLessee(nullable:true, maxsize:255)

        leasedAssetName(nullable:true, maxsize:255)
        scope1EmissionsOfLeasedAsset(nullable:true)
        scope1EmissionsOfLeasedAsset_Unit(nullable:true, maxsize:255)

        scope2EmissionsOfLeasedAsset(nullable:true)
        scope2EmissionsOfLeasedAsset_Unit(nullable:true, maxsize:255)

        energyUseFromLeasedSpace (nullable:true)

        reportingCompanysArea(nullable:true)
        reportingCompanysArea_Unit(nullable:true, maxsize:255)

        buildingsTotalArea(nullable:true)
        buildingsTotalArea_Unit(nullable:true, maxsize:255)

        buildingsTotalEnergyUse(nullable:true)
        buildingsTotalEnergyUse_Unit(nullable:true, maxsize:255)

        buildingsOccupancyRate(nullable:true)
        buildingsOccupancyRate_Unit(nullable:true, maxsize:255)

        flag_floorSpaceData(nullable:true)
        
        //emissionsFromCommercialAsset(nullable:true)
        //emissionsFromCommercialAsset_Unit(nullable:true, maxsize:255)
        
        floorSpace(nullable:true)
        floorSpace_Unit(nullable:true, maxsize:255)

        average_EF(nullable:true)
        average_EF_Unit (nullable:true, maxsize:255)

        //emissionsFromOtherAsset(nullable:true)
        //emissionsFromOtherAsset_Unit(nullable:true, maxsize:255)

        numberOfbuildingsOrAssetTypes(nullable:true)

        averageEmissionsPerBuildingOrAssetType(nullable:true)
        averageEmissionsPerBuildingOrAssetType_Unit(nullable:true, maxsize:255)
        
        methodType(blank:false, maxsize:255)
        userNotesOnData(nullable:true, maxsize:255)
        //calculatedEmissions(nullable:true)
        //calculatedEmissions_Unit(nullable:true, maxsize:255)
        dataBeginDate(nullable:false)
        dataEndDate(nullable:false)		
        emissionsDetailsList(nullable:true)
        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true, maxsize:255)                                                
                
    }
}