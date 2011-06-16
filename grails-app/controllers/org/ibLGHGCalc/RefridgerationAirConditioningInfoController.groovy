package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class RefridgerationAirConditioningInfoController {

  def refridgerationAirConditioningInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "RefridgerationAirConditioningInfoController.list( ${params} )"
    def refridgerationAirConditioningInfos = refridgerationAirConditioningInfoService.findRefridgerationAirConditioningInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        refridgerationAirConditioningInfos.each { theRefridgerationAirConditioningInfo ->
          flushRefridgerationAirConditioningInfo xml, theRefridgerationAirConditioningInfo
        }
      }
    }
  }

  def save = {
    log.info "RefridgerationAirConditioningInfoController.add( ${params} )"
    def theRefridgerationAirConditioningInfo
    try {
        theRefridgerationAirConditioningInfo = refridgerationAirConditioningInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theRefridgerationAirConditioningInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theRefridgerationAirConditioningInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushRefridgerationAirConditioningInfo xml, theRefridgerationAirConditioningInfo
      }
    }
  }

  def remove = {
    log.info "RefridgerationAirConditioningInfoController.remove( ${params} )"
    refridgerationAirConditioningInfoService.remove(params)
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

  private def flushRefridgerationAirConditioningInfo = { xml, refridgerationAirConditioningInfo ->
    xml.record(
        
        id: refridgerationAirConditioningInfo.id,
        organizationId: refridgerationAirConditioningInfo.organizationId,
	gasType:refridgerationAirConditioningInfo.gasType,

	inventoryChange:refridgerationAirConditioningInfo.inventoryChange,
	transferredAmount:refridgerationAirConditioningInfo.transferredAmount,
	capacityChange:refridgerationAirConditioningInfo.capacityChange,

	newUnitsCharge:refridgerationAirConditioningInfo.newUnitsCharge,
	newUnitsCapacity:refridgerationAirConditioningInfo.newUnitsCapacity,
	existingUnitsRecharge:refridgerationAirConditioningInfo.existingUnitsRecharge,
	disposedUnitsCapacity:refridgerationAirConditioningInfo.disposedUnitsCapacity,
	disposedUnitsRecovered:refridgerationAirConditioningInfo.disposedUnitsRecovered,

	sourceDescription:refridgerationAirConditioningInfo.sourceDescription,
	typeOfEquipment:refridgerationAirConditioningInfo.typeOfEquipment,
	sourceNewUnitsCharge:refridgerationAirConditioningInfo.sourceNewUnitsCharge,
	operatingUnitsCapacity:refridgerationAirConditioningInfo.operatingUnitsCapacity,
	sourceDisposedUnitsCapacity:refridgerationAirConditioningInfo.sourceDisposedUnitsCapacity,
        timeInYearsUsed:refridgerationAirConditioningInfo.timeInYearsUsed,

	methodType:refridgerationAirConditioningInfo.methodType,
	fuelUsedBeginDate:refridgerationAirConditioningInfo.fuelUsedBeginDate?.format("yyyy-MM-dd"),
	fuelUsedEndDate:refridgerationAirConditioningInfo.fuelUsedEndDate?.format("yyyy-MM-dd")
    )
  }
}
