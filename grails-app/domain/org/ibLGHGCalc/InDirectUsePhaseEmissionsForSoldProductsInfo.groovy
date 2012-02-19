package org.ibLGHGCalc

class InDirectUsePhaseEmissionsForSoldProductsInfo {
    
    static belongsTo = [organization:Organization]
    String sourceDescription    

    //----------------------------------------------------
    //InDirect Use Phase Emissions- Typical Use Phase Profile Method								
    //----------------------------------------------------
    String productType //1. Products that indirectly consume energy (fuels or electricity) during use
    String productName
    Double totalLifetimeExpectedUsesOfProduct

    String scenarioDescription
    Double percentOfTotalLifetimeUsesInThisScenario

    Double numberSoldInReportingPeriod

    String fuelType
    Double fuelConsumedPerUseInThisScenario
    String fuelConsumedPerUseInThisScenario_Unit //(kWh)

    Double EF_Fuel
    String EF_Fuel_Unit //(Kg CO2e/kWh)

    Double electricityConsumedPerUseInThisScenario
    String electricityConsumedPerUseInThisScenario_Unit //(kWh)

    Double EF_Electricity
    String EF_Electricity_Unit //(Kg CO2e/kWh)

    String refrigerantType
    Double refrigerantLeakagePerUseInThisScenario
    String refrigerantLeakagePerUseInThisScenario_Unit //(Kg)

    Double EF_Refrigerant
    String EF_Refrigerant_Unit //(Kg CO2e/Kg)

    Double GHG_EmittedIndirectly
    String GHG_EmittedIndirectly_Unit //(Kg)

    String GHG_Name
    Double GWP_GHG

    //----------------------------------------------------
    //InDirect Use Phase Emissions- Functional Unit Method								
    //----------------------------------------------------

    /*
    "The reporting company must first decide on the functional unit to apply to the product.
    */

    //Double numberOfFunctionalUnitsPerformedOverLifetimeOfSoldProducts
    Double functionalUnitsPerformedPerProduct 
    //Double totalNumberOfProductsSold

    Double emissionsPerFunctionalUnitOfProduct
    String emissionsPerFunctionalUnitOfProduct_Unit
    
    Double totalLifetimeEmissions
    String totalLifetimeEmissions_Unit
    //numberOfFunctionalUnitsPerformedOverLifetimeOfSoldProducts

    
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

        productType(nullable:true, maxsize:255)
        productName(nullable:true, maxsize:255)
        totalLifetimeExpectedUsesOfProduct(nullable:true)

        scenarioDescription(nullable:true, maxsize:255)
        percentOfTotalLifetimeUsesInThisScenario(nullable:true)

        numberSoldInReportingPeriod(nullable:true)

        fuelType(nullable:true, maxsize:255)
        fuelConsumedPerUseInThisScenario(nullable:true)
        fuelConsumedPerUseInThisScenario_Unit(nullable:true, maxsize:255)

        EF_Fuel(nullable:true)
        EF_Fuel_Unit(nullable:true, maxsize:255)

        electricityConsumedPerUseInThisScenario(nullable:true)
        electricityConsumedPerUseInThisScenario_Unit (nullable:true, maxsize:255)

        EF_Electricity(nullable:true)
        EF_Electricity_Unit (nullable:true, maxsize:255)

        refrigerantType(nullable:true, maxsize:255)
        refrigerantLeakagePerUseInThisScenario(nullable:true)
        refrigerantLeakagePerUseInThisScenario_Unit(nullable:true, maxsize:255)

        EF_Refrigerant(nullable:true)
        EF_Refrigerant_Unit(nullable:true, maxsize:255)

        GHG_EmittedIndirectly(nullable:true)
        GHG_EmittedIndirectly_Unit(nullable:true, maxsize:255)
        
        GHG_Name(nullable:true, maxsize:255)
        GWP_GHG(nullable:true)
        functionalUnitsPerformedPerProduct (nullable:true)

        emissionsPerFunctionalUnitOfProduct(nullable:true)
        emissionsPerFunctionalUnitOfProduct_Unit(nullable:true, maxsize:255)
        
        totalLifetimeEmissions(nullable:true)
        totalLifetimeEmissions_Unit(nullable:true, maxsize:255)
        
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
