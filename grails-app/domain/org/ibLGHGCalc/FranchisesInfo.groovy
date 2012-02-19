package org.ibLGHGCalc

    class FranchisesInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription    
    
    //====================================
    //Franchise Specific Method
    //====================================

    /*
    "Aggregate the scope 1 and scope 2 emissions across all of the reporting company’s franchises. Companies then should apply the formula below to calculate emissions.
    */

    String franchiseName
    Double scope1EmissionsOfFranchise
    String scope1EmissionsOfFranchise_Unit
    Double scope2EmissionsOfFranchise
    String scope2EmissionsOfFranchise_Unit

    //==========================================
    // Average Data Method
    //==========================================

    //"Formula 1: If floor space data is available:

    Boolean flag_floorSpaceData  //Whether floor space data is available?
    
    Double floorSpace
    String floorSpace_Unit //(m2)

    Double average_EF
    String average_EF_Unit // (Kg CO2e/m2/year)

    //Formula 2: If floor space data is not available:
    String buildingOrAssetName
    Double numberOfbuildingsOrAssetTypes
    //String numberOfbuildingsOrAssetTypes_Unit

    Double averageEmissionsPerBuildingOrAssetType
    String averageEmissionsPerBuildingOrAssetType_Unit //(Kg CO2e/building or Asset Type)

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

        franchiseName(nullable:true, maxsize:255)
        scope1EmissionsOfFranchise(nullable:true)
        scope1EmissionsOfFranchise_Unit(nullable:true, maxsize:255)
        scope2EmissionsOfFranchise(nullable:true)
        scope2EmissionsOfFranchise_Unit(nullable:true, maxsize:255)

        flag_floorSpaceData(nullable:true)
        
        floorSpace(nullable:true)
        floorSpace_Unit(nullable:true, maxsize:255)

        average_EF(nullable:true)
        average_EF_Unit(nullable:true, maxsize:255)

        buildingOrAssetName(nullable:true, maxsize:255)
        numberOfbuildingsOrAssetTypes(nullable:true)
        //numberOfbuildingsOrAssetTypes_Unit(nullable:true, maxsize:255)

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
