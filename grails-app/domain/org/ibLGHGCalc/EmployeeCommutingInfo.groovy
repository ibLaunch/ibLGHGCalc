package org.ibLGHGCalc

class EmployeeCommutingInfo {

    static belongsTo = [organization:Organization]

    /*
    "Companies should convert daily commuting distance into annual commuting distance by multiplying the daily distance by the number of times the trip occurs during the reporting period
    Distance travelled data by transport mode should be summed across all employees to obtain total annual kilometers or passenger-kilometers travelled by each mode of transport.
    Companies then should apply the formula below to calculate emissions.
    */

    //Company Specific Method

    String description
    
    //First, sum across all employees to determine total distance travelled using each vehicle type:
    String vehicleType

    Double totalDistanceTravelledByVehicleType
    String totalDistanceTravelledByVehicleType_Unit // (Vehicle-Km or passenger-Km )

    Double dailyOneWayDistanceBetweenHomeAndWork
    String dailyOneWayDistanceBetweenHomeAndWork_Unit //(Km)

    Integer numberOfCommutingDaysPerYear

    //Then, sum across vehicle types to determine total emissions
    Double emissionsFromEmployeeCommuting
    String emissionsFromEmployeeCommuting_Unit

    String EF_VehicleType //(Kg CO2e/vehicle-km or Kg CO2e/passenger-Km)


    //Optionally, for each energy source used in teleworking:

    Double emissionsFromEnergySourceInTeleworking
    String emissionsFromEnergySourceInTeleworking_Unit

    Double quantityOfEEnergyConsumed 
    String quantityOfEEnergyConsumed_Unit //(kWh)

    Double EF_EnergySource 
    String EF_EnergySource_Unit //(Kg CO2e/kWh)

    //Average Data Method

    /*
    "Companies should convert daily commuting distance into annual commuting distance by multiplying the daily distance by the number of times the trip occurs during the reporting period
    Companies then should apply the formula below to calculate emissions.
    */

    String transportMode
    Integer totalNumberOfEmployees 

    Double percentOfEmployeesUsingModeOfTransport

    //Double dailyOneWayDistanceBetweenHomeAndWork
    //String dailyOneWayDistanceBetweenHomeAndWork_Unit //(vehicle-km or passenger-Km)

    Integer numberOfWorkingDaysPerYear

    Double EF_TransportMode
    String EF_TransportMode_Unit //(Kg CO2e/vehicle-km or Kg CO2e/passenger-Km)

    //Other fields    
    String methodType
    String userNotesOnData  //User can keep the notes here, in terms of where the data was coming from    
    Double calculatedEmissions  //calculated emissions based on the use data
    String calculatedEmissions_Unit
    
    Date dataProvidedBeginDate
    Date dataProvidedEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'    
    
    static constraints = {
        organization(nullable:false)
        description(nullable:true, maxsize:255)

        //Company Specific Method
        vehicleType(nullable:true, maxsize:255)

        totalDistanceTravelledByVehicleType (nullable:true)
        totalDistanceTravelledByVehicleType_Unit (nullable:true, maxsize:255)

        dailyOneWayDistanceBetweenHomeAndWork(nullable:true)
        dailyOneWayDistanceBetweenHomeAndWork_Unit (nullable:true, maxsize:255)

        numberOfCommutingDaysPerYear(nullable:true)

        emissionsFromEmployeeCommuting(nullable:true)
        emissionsFromEmployeeCommuting_Unit(nullable:true, maxsize:255)

        EF_VehicleType (nullable:true, maxsize:255)

        emissionsFromEnergySourceInTeleworking(nullable:true)
        emissionsFromEnergySourceInTeleworking_Unit(nullable:true, maxsize:255)

        quantityOfEEnergyConsumed (nullable:true)
        quantityOfEEnergyConsumed_Unit (nullable:true, maxsize:255)

        EF_EnergySource(nullable:true) 
        EF_EnergySource_Unit(nullable:true, maxsize:255)

        //Average Data Method

        transportMode(nullable:true,maxsize:255)
        totalNumberOfEmployees(nullable:true) 

        percentOfEmployeesUsingModeOfTransport(nullable:true)

        dailyOneWayDistanceBetweenHomeAndWork(nullable:true)
        dailyOneWayDistanceBetweenHomeAndWork_Unit (nullable:true, maxsize:255)

        numberOfWorkingDaysPerYear(nullable:true)

        EF_TransportMode(nullable:true)
        EF_TransportMode_Unit (nullable:true, maxsize:255)
                
        methodType(blank:false, maxsize:255)
        userNotesOnData(nullable:true, maxsize:255)
        calculatedEmissions(nullable:true)
        calculatedEmissions_Unit(nullable:true, maxsize:255)
        dataProvidedBeginDate(nullable:false)
        dataProvidedEndDate(nullable:false)		
        //emissionsDetailsList(nullable:true)
        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true, maxsize:255)                                                
    }
}
