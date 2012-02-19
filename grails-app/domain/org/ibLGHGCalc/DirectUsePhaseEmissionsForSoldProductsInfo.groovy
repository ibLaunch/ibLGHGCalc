package org.ibLGHGCalc

class DirectUsePhaseEmissionsForSoldProductsInfo {

    static belongsTo = [organization:Organization]

    String sourceDescription    
    String productType //1. Energy Using Products 2.Fuels and Feedstocks 3. Product Containing GHGs that are Emitted During Use
    String productName
    
    //----------------------------------------------------
    //Direct Use Phase Emissions – Energy Using Products - Lifetime-Uses Method								
    //----------------------------------------------------

    Double totalLifetimeExpectedUsesOfProduct
    Double numberSoldInReportingPeriod

    String fuelType
    Double fuelConsumedPerUse
    String fuelConsumedPerUse_Unit //(kWh)

    Double EF_Fuel
    String EF_Fuel_Unit //(Kg CO2e/kWh)

    Double electricityConsumedPerUse
    String electricityConsumedPerUse_Unit //(kWh)

    Double EF_Electricity
    String EF_Electricity_Unit //(Kg CO2e/kWh)
    
    String refrigerantType
    Double refrigerantLeakagePerUse
    String refrigerantLeakagePerUse_Unit //(Kg)

    Double EF_Refrigerant
    String EF_Refrigerant_Unit //(Kg CO2e/Kg)

    //----------------------------------------------------
    //Direct Use Phase Emissions – Fuels and Feedstocks - Combustion Method								
    //----------------------------------------------------

    Double totalQuantityOfFuelOrFeedstockSold
    String totalQuantityOfFuelOrfeedstockSold_Unit //(e.g kWh)

    Double combustion_EF_ForFuelOrFeedstock
    String combustion_EF_ForFuelOrFeedstock_Unit // (Kg CO2e/kWh)

    //----------------------------------------------------
    //Direct Use Phase Emissions – Product Containing GHGs that are Emitted During Use - % GHG released Method								
    //----------------------------------------------------

    /*
    "The company should first account for all the different types of GHGs contained in a product, then aggregate for all products. Where the use phase of a product is likely to be similar between multiple products companies may choose to group similar products.
    */
    String GHG_Name
    Double GHG_PerProduct 
    //Double totalNumberOfProductsSold
    Double percentOfGHGReleasedDuringLifetimeUseOfProduct //Note:  if the % released is unknown 100% should be assumed"
    Double GWP_GHG
    
    

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

        productType (nullable:true, maxsize:255)
        productName (nullable:true, maxsize:255)
        totalLifetimeExpectedUsesOfProduct(nullable:true)

        numberSoldInReportingPeriod(nullable:true)

        fuelType(nullable:true, maxsize:255)
        fuelConsumedPerUse(nullable:true)
        fuelConsumedPerUse_Unit(nullable:true, maxsize:255)

        EF_Fuel(nullable:true)
        EF_Fuel_Unit(nullable:true, maxsize:255)

        electricityConsumedPerUse(nullable:true)
        electricityConsumedPerUse_Unit(nullable:true, maxsize:255)

        EF_Electricity(nullable:true)
        EF_Electricity_Unit(nullable:true, maxsize:255)

        refrigerantType(nullable:true, maxsize:255)
        refrigerantLeakagePerUse(nullable:true)
        refrigerantLeakagePerUse_Unit(nullable:true, maxsize:255)

        EF_Refrigerant(nullable:true)
        EF_Refrigerant_Unit(nullable:true, maxsize:255)

        totalQuantityOfFuelOrFeedstockSold(nullable:true)
        totalQuantityOfFuelOrfeedstockSold_Unit(nullable:true, maxsize:255)

        combustion_EF_ForFuelOrFeedstock(nullable:true)
        combustion_EF_ForFuelOrFeedstock_Unit(nullable:true, maxsize:255)

        GHG_Name(nullable:true, maxsize:255)
        GHG_PerProduct(nullable:true) 
        percentOfGHGReleasedDuringLifetimeUseOfProduct(nullable:true) 
        GWP_GHG(nullable:true)
        
        
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
