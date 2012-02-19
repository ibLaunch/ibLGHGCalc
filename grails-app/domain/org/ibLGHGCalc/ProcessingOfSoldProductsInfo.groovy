package org.ibLGHGCalc

class ProcessingOfSoldProductsInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription    

    //----------------------------------------------------
    //Site-Specific Method
    //----------------------------------------------------
    
    String soldProductName
    String productSoldTo
    String productSoldToContact
    
    String fuelType
    Double fuelConsumed
    String fuelConsumed_Unit //(e.g. litre)

    Double EF_Fuel 
    String EF_Fuel_Unit //(Kg CO2e/litre)

    Double electricityConsumed 
    String electricityConsumed_Unit // (e.g. kWh)

    Double EF_Electricity 
    String EF_Electricity_Unit //(Kg CO2e/kWh)

    String refrigerantType
    Double refrigerantLeakage 
    String refrigerantLeakage_Unit //(e.g. Kg)

    Double EF_Refrigerant
    String EF_Refrigerant_Unit //(Kg CO2e/Kg)

    Double massOfWasteOutput  
    String massOfWasteOutput_Unit //  (e.g. Kg)

    Double EF_WasteActivity 
    String EF_WasteActivity_Unit // (Kg CO2e/Kg)

    //----------------------------------------------------
    //Average-Data Method
    //----------------------------------------------------
    
    Double massOfSoldIntermediateProduct
    String massOfSoldIntermediateProduct_Unit //(Kg)

    Double EF_ProcessingOfSoldProducts  
    String EF_ProcessingOfSoldProducts_Unit  //(Kg CO2e/Kg of final product)

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

        soldProductName(nullable:true, maxsize:255)
        productSoldTo(nullable:true, maxsize:255)
        productSoldToContact(nullable:true, maxsize:255)
        
        fuelType(nullable:true, maxsize:255)
        fuelConsumed(nullable:true)
        fuelConsumed_Unit (nullable:true, maxsize:255)

        EF_Fuel (nullable:true)
        EF_Fuel_Unit(nullable:true, maxsize:255)

        electricityConsumed (nullable:true)
        electricityConsumed_Unit (nullable:true, maxsize:255)

        EF_Electricity (nullable:true)
        EF_Electricity_Unit(nullable:true, maxsize:255)

        refrigerantType(nullable:true, maxsize:255)
        refrigerantLeakage (nullable:true)
        refrigerantLeakage_Unit (nullable:true, maxsize:255)

        EF_Refrigerant(nullable:true)
        EF_Refrigerant_Unit (nullable:true, maxsize:255)

        massOfWasteOutput  (nullable:true)
        massOfWasteOutput_Unit (nullable:true, maxsize:255)

        EF_WasteActivity (nullable:true)
        EF_WasteActivity_Unit (nullable:true, maxsize:255)

        massOfSoldIntermediateProduct(nullable:true)
        massOfSoldIntermediateProduct_Unit(nullable:true, maxsize:255)

        EF_ProcessingOfSoldProducts  (nullable:true)
        EF_ProcessingOfSoldProducts_Unit (nullable:true, maxsize:255)
                       
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
