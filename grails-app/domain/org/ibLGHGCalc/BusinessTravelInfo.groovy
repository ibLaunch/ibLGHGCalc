package org.ibLGHGCalc

class BusinessTravelInfo {

    static belongsTo = [organization:Organization]

    //"Once the company has determined total annual distance travelled by each mode of transport (aggregated across all employees), apply the formula below to calculate emissions.

    String activityType //Travel, Hotel
    
    String sourceDescription
    String vehicleType
    Double distanceTravelledByVehicleType
    String distanceTravelledByVehicleType_Unit //(Vehicle-Km or passenger-Km )

    Double EF_VehicleType
    String EF_VehicleType_Unit //(Kg CO2e/vehicle-km or Kg CO2e/passenger-Km )

    Double annualNumberOfHotelNights  //(nights)
    Double EF_Hotel
    String EF_Hotel_Unit //(Kg CO2e/night)

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
        activityType(nullable:true, maxsize:255)
        vehicleType(nullable:true, maxsize:255)
        distanceTravelledByVehicleType(nullable:true)
        distanceTravelledByVehicleType_Unit (nullable:true, maxsize:255)

        EF_VehicleType(nullable:true)
        EF_VehicleType_Unit (nullable:true, maxsize:255)

        annualNumberOfHotelNights (nullable:true)
        EF_Hotel(nullable:true)
        EF_Hotel_Unit(nullable:true, maxsize:255)
        
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
