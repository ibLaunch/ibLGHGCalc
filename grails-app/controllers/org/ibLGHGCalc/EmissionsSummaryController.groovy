package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission


class EmissionsSummaryController {
  def emissionsSummaryService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "EmissionsSummaryController.list( ${params} )"
    def emissionsSummarys = emissionsSummaryService.findEmissionsSummarys(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        emissionsSummarys.each { theEmissionsSummary ->
          flushEmissionsSummary xml, theEmissionsSummary
        }
      }
    }
  }

  def save = {
    log.info "EmissionsSummaryController.add( ${params} )"
    //def theEmissionsSummary = emissionsSummaryService.save(params)
    def theEmissionsSummary

    try {
        theEmissionsSummary = emissionsSummaryService.save(params)
        //-- temporary approach below for now, need more better approach??
        aclUtilService.addPermission(theEmissionsSummary, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theEmissionsSummary not Saved"
    }

    println "theEmissionsSummary from Controller is: " + theEmissionsSummary

    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEmissionsSummary xml, theEmissionsSummary
      }
    }
  }

  def remove = {
    log.info "EmissionsSummaryController.remove( ${params} )"
    emissionsSummaryService.remove(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      data {
        status(0)
        record {
          id(params.id)
        }
      }
    }
  }

  private def flushEmissionsSummary = { xml, emissionsSummary ->
    xml.record(
        id: emissionsSummary.id,
        organizationId: emissionsSummary.organizationId,

    	directEmissions: emissionsSummary.directEmissions,
	stationaryCombustionEmissions: emissionsSummary.stationaryCombustionEmissions,
    	mobileCombustionEmissions: emissionsSummary.mobileCombustionEmissions,
    	refridgerationAirConditioningEmissions: emissionsSummary.refridgerationAirConditioningEmissions,
    	fireSuppressantEmissions: emissionsSummary.fireSuppressantEmissions,
    	wasteStreamCombustionEmissions: emissionsSummary.wasteStreamCombustionEmissions,

        purchasedElectricityEmissions: emissionsSummary.purchasedElectricityEmissions,
        purchasedSteamEmissions: emissionsSummary.purchasedSteamEmissions,

        employeeBusinessTravelByVehicleEmissions:  emissionsSummary.employeeBusinessTravelByVehicleEmissions,
        employeeBusinessTravelByRailEmissions:emissionsSummary.employeeBusinessTravelByRailEmissions,
        employeeBusinessTravelByBusEmissions:emissionsSummary.employeeBusinessTravelByBusEmissions,
        employeeBusinessTravelByAirEmissions:emissionsSummary.employeeBusinessTravelByAirEmissions,

        employeeCommutingByVehicleEmissions:emissionsSummary.employeeCommutingByVehicleEmissions,
        employeeCommutingByRailEmissions:emissionsSummary.employeeCommutingByRailEmissions,
        employeeCommutingByBusEmissions:emissionsSummary.employeeCommutingByBusEmissions,

        productTransportByVehicleEmissions:emissionsSummary.productTransportByVehicleEmissions,
        productTransportByHeavyDutyTrucksEmissions:emissionsSummary.productTransportByHeavyDutyTrucksEmissions,
        productTransportByRailEmissions:emissionsSummary.productTransportByRailEmissions,
        productTransportByWaterAirEmissions:emissionsSummary.productTransportByWaterAirEmissions,

        biomassStationaryCombustionEmissions: emissionsSummary.biomassStationaryCombustionEmissions,
        biomassMobileCombustionEmissions: emissionsSummary.biomassMobileCombustionEmissions,

    	totalEmissions: emissionsSummary.totalEmissions,
        programType: emissionsSummary.programType,        
    	emissionsBeginDate: emissionsSummary.emissionsBeginDate,
    	emissionsEndDate: emissionsSummary.emissionsEndDate
    )
  }

  def calculateEmissionsSummary = {
    log.info "EmissionsSummaryController.calculateEmissionsSummary( ${params} )"
    emissionsSummaryService.calculateEmissionsSummary(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      data {
        status(0)
        record {
          id(params.id)
        }
      }
    }
  }
}