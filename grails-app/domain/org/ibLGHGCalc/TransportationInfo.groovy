package org.ibLGHGCalc

class TransportationInfo {

    static belongsTo = [organization:Organization]
    
    String sourceDescription
    String streamType   //1.upstream 2.downstream
    
    String serviceProviderName
    String serviceProviderContact
    
    //Fuel Based Method -  transport emission
    String fuelDataType // "Fuel Consumed", "Distance/Vehicle Used", "Calculate Allocated Fuel Used"
    
    String fuelType
    Double fuelConsumed
    String fuelConsumed_Unit  //(litres)

    Double EF_Fuel
    String EF_Fuel_Unit //(Kg CO2e/litre)

    String refrigerantType
    Double refrigerantLeakage
    String refrigerantLeakage_Unit  //(Kg)

    Double EF_Refrigerant
    String EF_Refrigerant_Unit //(Kg CO2e/Kg)

    //-----------------------------------------------
//    Boolean flag_calculateFuelConsumed  //Calculate fuel consumed based on the distance
    Double totalDistanceTravelled
    String totalDistanceTravelled_Unit //(Km)

    String vehicleType
    String vehicleName
    Double fuelEfficiencyOfVehicle
    String fuelEfficiencyOfVehicle_Unit //(1/Km)

    //------------------------------------------------------------------------------------------------------

//    Boolean flag_calculateAllocatedFuelUse
    Double allocatedFuelUse //Calculated field
    String allocatedFuelUse_Unit //litres

    Double totalFuelConsumed  //either calculated or user supplied
    String totalFuelConsumed_Unit //(litres)

    //String massOrVolumeOfCompanyGoods   
    
    Double companyGoodsTransported
    String companyGoodsTransported_Unit
    String companyGoodsTransported_MassType //Actual, Chargeable, Dimensional
    
    Double totalGoodsTransportedByCarrier
    String totalGoodsTransportedByCarrier_Unit
    String totalGoodsTransportedByCarrier_MassType //Actual, Chargeable, Dimensional
    
    //String massType  //Actual, Chargeable, Dimensional
    
    //Companies may optionally substitute mass of goods by volume, dimensional mass or chargeable mass where data is available to prove that the alternative method is more suitable.
    //Dimensional mass is a calculated mass that takes into account packaging volume as well as the actual mass of the goods.
    //Chargeable mass is higher value of either the actual or the dimensional mass of the goods.
    //-------------------------------------------------------------------------------------------------------    
    
    Boolean flag_unladenBackhaul
    
/*
    Double emissionsFromUnladenBackhaul  //calculated
    String emissionsFromUnladenBackhaul_Unit

    Double quantityOfBackhaulFuelConsumed  //calculated
    String quantityOfBackhaulFuelConsumed_Unit //(litres)

    Double EF_BackhaulFuel
    String EF_BackhaulFuel_Unit //(Kg CO2e/litre)

    Double averageEfficiencyOfVehicleUnladden
    String averageEfficiencyOfVehicleUnladden_Unit //(1/Km) 

    Double totalDistanceTraveledUnladen
    String totalDistanceTraveledUnladen_Unit //(Km)
*/    

    //Distance Based Method -  transport emission								
    String transportModeOrVehicleType

    Double massOfGoodsPurchased
    String massOfGoodsPurchased_Unit //(tonnes or volume)

    Double distanceTraveledInTransportLeg
    String distanceTraveledInTransportLeg_Unit //(Km)

    Double EF_TransportModeOrVehicleType
    String EF_TransportModeOrVehicleType_Unit //(Kg CO2e/tonne or Volume/Km)

    //Each transport mode or vehicle type should be calculated separately and total emissions aggregated."    

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

        serviceProviderName(nullable:true, maxsize:255)
        serviceProviderContact(nullable:true, maxsize:255)
        
        //Fuel Based Method -  transport emission	        
        fuelDataType(nullable:true, maxsize:255)
        fuelType (nullable:true, maxsize:255)
        fuelConsumed(nullable:true)
        fuelConsumed_Unit (nullable:true, maxsize:255)

        EF_Fuel(nullable:true)
        EF_Fuel_Unit (nullable:true, maxsize:255)

        refrigerantType(nullable:true, maxsize:255)
        refrigerantLeakage(nullable:true)
        refrigerantLeakage_Unit (nullable:true, maxsize:255)

        EF_Refrigerant(nullable:true)
        EF_Refrigerant_Unit(nullable:true, maxsize:255)

        //------------------------------------------------------------------------------------------------------
//        flag_calculateFuelConsumed(nullable:true)
        totalDistanceTravelled(nullable:true)
        totalDistanceTravelled_Unit(nullable:true, maxsize:255)

        vehicleType(nullable:true, maxsize:255)
        vehicleName(nullable:true, maxsize:255)
        fuelEfficiencyOfVehicle(nullable:true)
        fuelEfficiencyOfVehicle_Unit (nullable:true, maxsize:255)

        //------------------------------------------------------------------------------------------------------
//        flag_calculateAllocatedFuelUse(nullable:true)
        allocatedFuelUse(nullable:true)
        allocatedFuelUse_Unit(nullable:true, maxsize:255) 

        totalFuelConsumed (nullable:true)
        totalFuelConsumed_Unit (nullable:true, maxsize:255)

        companyGoodsTransported(nullable:true)
        companyGoodsTransported_Unit(nullable:true, maxsize:255)
        companyGoodsTransported_MassType(nullable:true, maxsize:255)
        
        totalGoodsTransportedByCarrier(nullable:true)
        totalGoodsTransportedByCarrier_Unit(nullable:true, maxsize:255)
        totalGoodsTransportedByCarrier_MassType(nullable:true, maxsize:255)
        //massType(nullable:true,maxsize:255)
        //-------------------------------------------------------------------------------------------------------

        //emissionsFromUnladenBackhaul(nullable:true)
        //emissionsFromUnladenBackhaul_Unit(nullable:true, maxsize:255)
        
        flag_unladenBackhaul(nullable:true)
/*        
        quantityOfBackhaulFuelConsumed (nullable:true)
        quantityOfBackhaulFuelConsumed_Unit(nullable:true, maxsize:255)

        EF_BackhaulFuel(nullable:true)
        EF_BackhaulFuel_Unit(nullable:true, maxsize:255)

        averageEfficiencyOfVehicleUnladden(nullable:true)
        averageEfficiencyOfVehicleUnladden_Unit(nullable:true, maxsize:255)

        totalDistanceTraveledUnladen(nullable:true)
        totalDistanceTraveledUnladen_Unit(nullable:true, maxsize:255)

*/
        //Distance Based Method -  transport emission								
        transportModeOrVehicleType(nullable:true, maxsize:255)

        massOfGoodsPurchased(nullable:true)
        massOfGoodsPurchased_Unit(nullable:true, maxsize:255)

        distanceTraveledInTransportLeg(nullable:true)
        distanceTraveledInTransportLeg_Unit(nullable:true, maxsize:255)

        EF_TransportModeOrVehicleType(nullable:true)
        EF_TransportModeOrVehicleType_Unit (nullable:true, maxsize:255)

        //Each transport mode or vehicle type should be calculated separately and total emissions aggregated."    
               
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