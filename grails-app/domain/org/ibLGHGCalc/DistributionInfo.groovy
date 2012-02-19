package org.ibLGHGCalc

class DistributionInfo {
    
    static belongsTo = [organization:Organization]

    String sourceDescription
    String streamType   //1.upstream 2.downstream
    
    String serviceProviderName
    String serviceProviderContact
    
    //Site Specific Method - distribution emission								
    //"Formula 1:
    String storageFacility

    //Double emissionsFromDistribution
    //String emissionsFromDistribution_Unit

    String fuelType
    Double fuelConsumed
    String fuelConsumed_Unit //(kWh)

    Double EF_Fuel
    String EF_Fuel_Unit // (Kg CO2e/kWh)

    Double electricityConsumed
    String electricityConsumed_Unit //(kWh)

    Double EF_Electricity
    String EF_Electricity_Unit //(Kg CO2e/kWh)

    String refrigerantType
    Double refrigerantLeakage
    String refrigerantLeakage_Unit //(Kg)

    Double EF_Refrigerant
    String EF_Refrigerant_Unit //(Kg CO2e/Kg)


    //Then, allocate emissions based on volume that company’s products take within storage facility:
    //Formula 2:
    Double allocatedEmissionsOfStorageFacility
    String allocatedEmissionsOfStorageFacility_Unit

    Double volumeOfReportingCompanysPurchasedGoods
    String volumeOfReportingCompanysPurchasedGoods_Unit //(m3)

    Double totalVolumeOfGoodsInStorageFacility
    String totalVolumeOfGoodsInStorageFacility_Unit  //(m3)

    Double emissionsOfStorageFacility
    String emissionsOfStorageFacility_Unit //(Kg CO2e)

    //Average Data Method -  distribution emission - 1 & 2
    Double storedGoodsInReportingYear
    String storedGoodsInReportingYear_Unit //(m3) or (pallets) 

    Double EF_StorageFacility
    String EF_StorageFacility_Unit //(Kg CO2e/m3) or (Kg CO2e/pallet)
/*
    //OR
    //Average Data Method -  distribution emission - 2s

    Double totalNumberOfPalletsStored
    String totalNumberOfPalletsStored_Unit  //(pallets) 

    Double EF_StoredPallet
    String EF_StoredPallet_Unit //(Kg CO2e/pallet)
*/
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
        
        //Site Specific Method -  distribution emission								
        storageFacility(nullable:true, maxsize:255)

        fuelType(nullable:true, maxsize:255)
        fuelConsumed(nullable:true)
        fuelConsumed_Unit(nullable:true, maxsize:255)

        EF_Fuel(nullable:true)
        EF_Fuel_Unit(nullable:true, maxsize:255)

        electricityConsumed(nullable:true)
        electricityConsumed_Unit(nullable:true, maxsize:255)

        EF_Electricity(nullable:true)
        EF_Electricity_Unit(nullable:true, maxsize:255)

        refrigerantType(nullable:true, maxsize:255)
        refrigerantLeakage(nullable:true)
        refrigerantLeakage_Unit(nullable:true, maxsize:255)

        EF_Refrigerant(nullable:true)
        EF_Refrigerant_Unit(nullable:true, maxsize:255)

        allocatedEmissionsOfStorageFacility(nullable:true)
        allocatedEmissionsOfStorageFacility_Unit(nullable:true, maxsize:255)

        volumeOfReportingCompanysPurchasedGoods(nullable:true)
        volumeOfReportingCompanysPurchasedGoods_Unit(nullable:true, maxsize:255)

        totalVolumeOfGoodsInStorageFacility(nullable:true)
        totalVolumeOfGoodsInStorageFacility_Unit(nullable:true, maxsize:255)

        emissionsOfStorageFacility(nullable:true)
        emissionsOfStorageFacility_Unit(nullable:true, maxsize:255)

        //Average Data Method -  distribution emission - 1
        storedGoodsInReportingYear(nullable:true)
        storedGoodsInReportingYear_Unit(nullable:true, maxsize:255) // m3 or Pallets

        EF_StorageFacility(nullable:true)
        EF_StorageFacility_Unit(nullable:true, maxsize:255)  //Kg CO2e/m3 or Kg CO2e/Pallet

        //Average Data Method -  distribution emission - 2s
/*
        totalNumberOfPalletsStored(nullable:true)
        totalNumberOfPalletsStored_Unit(nullable:true, maxsize:255) 

        EF_StoredPallet(nullable:true)
        EF_StoredPallet_Unit(nullable:true, maxsize:255)        
*/        
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