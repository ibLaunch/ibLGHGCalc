package org.ibLGHGCalc

class EmissionsSummary {

    static belongsTo = [organization:Organization]
    
    Double directEmissions
    Double stationaryCombustionEmissions
    Double mobileCombustionEmissions
    Double refridgerationAirConditioningEmissions
    Double fireSuppressantEmissions
    Double wasteStreamCombustionEmissions

    Double purchasedElectricityEmissions
    Double purchasedSteamEmissions

//--optional Emissions
    Double employeeBusinessTravelByVehicleEmissions
    Double employeeBusinessTravelByRailEmissions
    Double employeeBusinessTravelByBusEmissions
    Double employeeBusinessTravelByAirEmissions

    Double employeeCommutingByVehicleEmissions
    Double employeeCommutingByRailEmissions
    Double employeeCommutingByBusEmissions

    Double productTransportByVehicleEmissions
    Double productTransportByHeavyDutyTrucksEmissions
    Double productTransportByRailEmissions
    Double productTransportByWaterAirEmissions

    Double biomassStationaryCombustionEmissions
    Double biomassMobileCombustionEmissions
    
    Double totalEmissions
    
    String programType
    Date emissionsBeginDate
    Date emissionsEndDate

    Date dateCreated
    Date lastUpdated

    static constraints = {
            directEmissions(nullable:true)
            stationaryCombustionEmissions(nullable:true)
    	    mobileCombustionEmissions(nullable:true)
	    refridgerationAirConditioningEmissions(nullable:true)
	    fireSuppressantEmissions(nullable:true)
	    wasteStreamCombustionEmissions(nullable:true)

            biomassStationaryCombustionEmissions(nullable:true)
            biomassMobileCombustionEmissions(nullable:true)

            employeeBusinessTravelByVehicleEmissions(nullable:true)
            employeeBusinessTravelByRailEmissions(nullable:true)
            employeeBusinessTravelByBusEmissions(nullable:true)
            employeeBusinessTravelByAirEmissions(nullable:true)

            employeeCommutingByVehicleEmissions(nullable:true)
            employeeCommutingByRailEmissions(nullable:true)
            employeeCommutingByBusEmissions(nullable:true)

            productTransportByVehicleEmissions(nullable:true)
            productTransportByHeavyDutyTrucksEmissions(nullable:true)
            productTransportByRailEmissions(nullable:true)
            productTransportByWaterAirEmissions(nullable:true)

            biomassStationaryCombustionEmissions(nullable:true)
            biomassMobileCombustionEmissions(nullable:true)

	    totalEmissions(nullable:true)
	    emissionsBeginDate(blank:false)
	    emissionsEndDate(blank:false)
            programType(nullable:true)
     }
}
