package org.ibLGHGCalc

class OptionalSourceInfo {

    static belongsTo = [organization:Organization]

//--Common to all optional source types
    String optionalSourceType
    String sourceDescription

//--Employee Business Travel - By Vehicle
//--Employee Commuting - By Vehicle
//--Product Transport - By Vehicle
    String vehicleType
    //Double vehicleMiles

//--Employee Business Travel - By Rail
//--Employee Commuting - By Rail
    String railType
    //Double railPassengerMiles

//--Employee Business Travel - By Bus
//--Employee Commuting - By Bus
    String busType
    //Double busPassengerMiles

//--Employee Business Travel - By Air
    String airTravelType
    //Double airTravelPassengerMiles

//--Employee Business Travel - By Air    
    Double annualNumberOfHotelNights  //(nights)
    Double EF_Hotel
    String EF_Hotel_Unit //(Kg CO2e/night)
    
    
//--Common to all optional source types except for sources that used tonMiles se below
    Double passengerMiles

//--Product Transport - By Heavy Duty Trucks
//--Product Transport - By Rail
//--Product Transport - By Water or Air
    String transportType
    Double tonMiles

    Date  fuelUsedBeginDate
    Date  fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'

    static hasMany = [emissionsDetailsList: EmissionsDetails]

    static constraints = {
            organization(nullable:false)
            optionalSourceType(blank:false, maxsize:255)
            sourceDescription(blank:false, maxsize:255)

            vehicleType(nullable:true)
            railType(nullable:true)
            busType(nullable:true)
            airTravelType(nullable:true)
            transportType(nullable:true)

            annualNumberOfHotelNights (nullable:true)
            EF_Hotel(nullable:true)
            EF_Hotel_Unit(nullable:true, maxsize:255)
        
            passengerMiles(nullable:true)
            tonMiles(nullable:true)

            fuelUsedBeginDate(nullable:false)
            fuelUsedEndDate(nullable:false)
            emissionsDetailsList(nullable:true)
            
            lastUpdatedByUserReference(nullable:true)
            dataOrigin(nullable:true)

    }
    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
    }
}