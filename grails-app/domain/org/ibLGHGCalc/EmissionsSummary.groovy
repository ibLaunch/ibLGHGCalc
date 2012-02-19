package org.ibLGHGCalc

class EmissionsSummary {

    //static searchable = true
    
    static belongsTo = [organization:Organization]

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
    Double employeeBusinessTravelHotelStayEmissions

    Double employeeCommutingByVehicleEmissions
    Double employeeCommutingByRailEmissions
    Double employeeCommutingByBusEmissions

    Double productTransportByVehicleEmissions
    Double productTransportByHeavyDutyTrucksEmissions
    Double productTransportByRailEmissions
    Double productTransportByWaterAirEmissions

    Double purchasedGoodsAndServicesEmissions
    Double purchasedCapitalGoodsEmissions
    
    Double purchasedEnergyEmissions

    Double upstreamTransportationEmissions
    Double downstreamTransportationEmissions

    Double upstreamDistributionEmissions
    Double downstreamDistributionEmissions
    
    Double wasteGeneratedInOperationsEmissions    

    Double businessTravelEmissions    

    Double upstreamLeasedAssetsEmissions
    Double downstreamLeasedAssetsEmissions    
    
    Double processingOfSoldProductsEmissions 

    Double directUsePhaseEmissionsForSoldProductsEmissions 
    Double inDirectUsePhaseEmissionsForSoldProductsEmissions 
    
    Double endOfLifeTreatmentOfSoldProductsEmissions
    Double franchisesEmissions
    Double investmentsEmissions
    
    Double anticipatedLifetimeProjectInvestmentsEmissions
    Double optionalInvestmentsEmissions
    
    Double biomassStationaryCombustionEmissions
    Double biomassMobileCombustionEmissions
    Double biomassEmissions
    
    Double totalEmissions
    Double totalBusinessTravelEmissions
    Double totalEmployeeCommutingEmissions
    Double totalOptionalEmissions
    Double totalDirectEmissions
    Double totalInDirectEmissions

    Integer totalNumberOfSources

    String programType
    Date emissionsBeginDate
    Date emissionsEndDate

    String reportFileName

    Date dateCreated
    Date lastUpdated

    SecUser summaryGeneratedByUserReference

    static constraints = {
            
            stationaryCombustionEmissions(nullable:true)
    	    mobileCombustionEmissions(nullable:true)
	    refridgerationAirConditioningEmissions(nullable:true)
	    fireSuppressantEmissions(nullable:true)
	    wasteStreamCombustionEmissions(nullable:true)

            biomassStationaryCombustionEmissions(nullable:true)
            biomassMobileCombustionEmissions(nullable:true)
            
            //just have one place to store all the biomass emissions
            biomassEmissions(nullable:true)

            employeeBusinessTravelByVehicleEmissions(nullable:true)
            employeeBusinessTravelByRailEmissions(nullable:true)
            employeeBusinessTravelByBusEmissions(nullable:true)
            employeeBusinessTravelByAirEmissions(nullable:true)
            employeeBusinessTravelHotelStayEmissions(nullable:true)
            
            employeeCommutingByVehicleEmissions(nullable:true)
            employeeCommutingByRailEmissions(nullable:true)
            employeeCommutingByBusEmissions(nullable:true)

            productTransportByVehicleEmissions(nullable:true)
            productTransportByHeavyDutyTrucksEmissions(nullable:true)
            productTransportByRailEmissions(nullable:true)
            productTransportByWaterAirEmissions(nullable:true)
        
            purchasedGoodsAndServicesEmissions(nullable:true)
            purchasedCapitalGoodsEmissions(nullable:true)
            
            purchasedEnergyEmissions(nullable:true)
    
            upstreamTransportationEmissions(nullable:true)
            downstreamTransportationEmissions(nullable:true)

            upstreamDistributionEmissions(nullable:true)
            downstreamDistributionEmissions(nullable:true)
        
            wasteGeneratedInOperationsEmissions(nullable:true)
            
            businessTravelEmissions(nullable:true)
            upstreamLeasedAssetsEmissions(nullable:true)
            downstreamLeasedAssetsEmissions(nullable:true)
            
            processingOfSoldProductsEmissions(nullable:true)
            
            directUsePhaseEmissionsForSoldProductsEmissions(nullable:true)
            inDirectUsePhaseEmissionsForSoldProductsEmissions(nullable:true)
            
            endOfLifeTreatmentOfSoldProductsEmissions(nullable:true)
            franchisesEmissions(nullable:true)
            
            investmentsEmissions(nullable:true)            
            anticipatedLifetimeProjectInvestmentsEmissions(nullable:true)
            optionalInvestmentsEmissions(nullable:true)
        
	    totalEmissions(nullable:true)
            totalBusinessTravelEmissions(nullable:true)
            totalEmployeeCommutingEmissions(nullable:true)
            totalOptionalEmissions(nullable:true)
            totalDirectEmissions(nullable:true)
            totalInDirectEmissions(nullable:true)
            totalNumberOfSources(nullable:true)
	    emissionsBeginDate(blank:false)
	    emissionsEndDate(blank:false)
            programType(nullable:true)

            reportFileName(nullable:true)

            summaryGeneratedByUserReference(nullable:true)
        
     }
}
