package org.ibLGHGCalc

class PurchasedEnergyInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription
    
    String activityType
    String fuelType
    /*
        Upstream emissions of purchased fuels
        Upstream emissions of purchased fuels
        Transmission & Distribution Losses
        Generation of purchased electricity that is sold to end users
    */
   
    String energyType  //Electricity, Steam, Heating, Cooling
    
    String supplierName
    String supplierContact

    String supplierRegionCountry

    Double energyPurchased
    String energyPurchased_Unit // (kWh)
    
    Boolean flag_upstreamEF //upstream EF provided?
    Double EF_UpstreamEnergy
    String EF_UpstreamEnergy_Unit // (Kg CO2e/kWh)
    
    Double cradleToGate_EF_Energy
    String cradleToGate_EF_Energy_Unit 
    Double combustion_EF_Energy
    String combustion_EF_Energy_Unit
    
    String transAndDistDataType //Energy Data or Scope 2 data
    Double transAndDistLossRate //(%)    
    Double scope2EmissionsOfEnergyUse //(Kg CO2e)
    String scope2EmissionsOfEnergyUse_Unit //(Kg CO2e)
        
/*    
    Double electricityConsumed
    String electricityConsumed_Unit // (kWh)

    Double EF_UpstreamElectricity
    String EF_UpstreamElectricity_Unit //(Kg CO2e/kWh)

    Double steamConsumed
    String steamConsumed_Unit // (kWh)

    Double EF_UpstreamSteam
    String EF_UpstreamSteam_Unit //(Kg CO2e/kWh)

    Double heatingConsumed
    String heatingConsumed_Unit // (kWh)

    Double EF_UpstreamHeating
    String EF_UpstreamHeating_Unit // (Kg CO2e/kWh)

    Double coolingConsumed
    String coolingConsumed_Unit // (kWh)

    Double EF_UpstreamCooling
    String EF_UpstreamCooling_Unit // (Kg CO2e/kWh)

    Double cradleToGate_EF_Electricity
    String cradleToGate_EF_Electricity_Unit 
    Double combustion_EF_Electricity
    String combustion_EF_Electricity_Unit

    Double cradleToGate_EF_Steam
    String cradleToGate_EF_Steam_Unit 
    Double combustion_EF_Steam
    String combustion_EF_Steam_Unit

    Double cradleToGate_EF_Heating
    String cradleToGate_EF_Heating_Unit 
    Double combustion_EF_Heating
    String combustion_EF_Heating_Unit

    Double cradleToGate_EF_Cooling
    String cradleToGate_EF_Cooling_Unit 
    Double combustion_EF_Cooling
    String combustion_EF_Cooling_Unit
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

        activityType(blank:false, maxsize:255)
        fuelType(nullable:true, maxsize:255)
        energyType(nullable:true, maxsize:255)
        supplierName(nullable:true, maxsize:255)
        supplierContact(nullable:true, maxsize:255)
        
        supplierRegionCountry(nullable:true, maxsize:255) 

        energyPurchased (nullable:true)
        energyPurchased_Unit(nullable:true, maxsize:255) 
        
        flag_upstreamEF(nullable:true)
        EF_UpstreamEnergy(nullable:true)
        EF_UpstreamEnergy_Unit(nullable:true, maxsize:255) 
        
        cradleToGate_EF_Energy(nullable:true)
        cradleToGate_EF_Energy_Unit (nullable:true, maxsize:255) 
        combustion_EF_Energy(nullable:true)
        combustion_EF_Energy_Unit (nullable:true, maxsize:255) 
        
        transAndDistDataType(nullable:true)
        transAndDistLossRate(nullable:true)        
        scope2EmissionsOfEnergyUse(nullable:true)
        scope2EmissionsOfEnergyUse_Unit (nullable:true, maxsize:255) 
        
/*
        electricityConsumed (nullable:true)
        electricityConsumed_Unit(nullable:true, maxsize:255) 

        EF_UpstreamElectricity(nullable:true)
        EF_UpstreamElectricity_Unit(nullable:true, maxsize:255) 

        steamConsumed(nullable:true)
        steamConsumed_Unit(nullable:true, maxsize:255) 

        EF_UpstreamSteam(nullable:true)
        EF_UpstreamSteam_Unit(nullable:true, maxsize:255) 

        heatingConsumed(nullable:true)
        heatingConsumed_Unit(nullable:true, maxsize:255) 

        EF_UpstreamHeating(nullable:true)
        EF_UpstreamHeating_Unit(nullable:true, maxsize:255) 

        coolingConsumed(nullable:true)
        coolingConsumed_Unit(nullable:true, maxsize:255) 

        EF_UpstreamCooling(nullable:true)
        EF_UpstreamCooling_Unit(nullable:true, maxsize:255) 

        CradleToGate_EF_Electricity(nullable:true)
        CradleToGate_EF_Electricity_Unit (nullable:true, maxsize:255)  
        Combustion_EF_Electricity(nullable:true)
        Combustion_EF_Electricity_Unit (nullable:true, maxsize:255) 

        CradleToGate_EF_Steam(nullable:true)
        CradleToGate_EF_Steam_Unit (nullable:true, maxsize:255) 
        Combustion_EF_Steam(nullable:true)
        Combustion_EF_Steam_Unit (nullable:true, maxsize:255) 

        CradleToGate_EF_Heating(nullable:true)
        CradleToGate_EF_Heating_Unit (nullable:true, maxsize:255) 
        Combustion_EF_Heating(nullable:true)
        Combustion_EF_Heating_Unit(nullable:true, maxsize:255) 

        CradleToGate_EF_Cooling(nullable:true)
        CradleToGate_EF_Cooling_Unit (nullable:true, maxsize:255) 
        Combustion_EF_Cooling(nullable:true)
        Combustion_EF_Cooling_Unit (nullable:true, maxsize:255) 
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
    
    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
    }        

    
}
