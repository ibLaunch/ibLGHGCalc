package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class MobileCombustionInfoController {
  def mobileCombustionInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "MobileCombustionInfoController.list( ${params} )"
    def mobileCombustionInfos = mobileCombustionInfoService.findMobileCombustionInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        mobileCombustionInfos.each { theMobileCombustionInfo ->
          flushMobileCombustionInfo xml, theMobileCombustionInfo
        }
      }
    }
  }

  def save = {
    log.info "MobileCombustionInfoController.add( ${params} )"
    println "MobileCombustionInfoController.add( ${params} )"
    def theMobileCombustionInfo
    try {
        theMobileCombustionInfo = mobileCombustionInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theMobileCombustionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theMobileCombustionInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushMobileCombustionInfo xml, theMobileCombustionInfo
      }
    }
  }

  def remove = {
    log.info "MobileCombustionInfoController.remove( ${params} )"
    mobileCombustionInfoService.remove(params)
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

  private def flushMobileCombustionInfo = { xml, mobileCombustionInfo ->
    xml.record(
        id: mobileCombustionInfo.id,
        organizationId: mobileCombustionInfo.organizationId,
        fuelSourceDescription: mobileCombustionInfo.fuelSourceDescription,
        vehicleType: mobileCombustionInfo.vehicleType,
        vehicleYear: mobileCombustionInfo.vehicleYear,
        fuelType: mobileCombustionInfo.fuelType,
        fuelQuantity: mobileCombustionInfo.fuelQuantity,
        fuelUnit: mobileCombustionInfo.fuelUnit,
        milesTravelled: mobileCombustionInfo.milesTravelled,
        bioFuelPercent: mobileCombustionInfo.bioFuelPercent,
        ethanolPercent: mobileCombustionInfo.ethanolPercent,
        fuelUsedBeginDate:mobileCombustionInfo.fuelUsedBeginDate?.format("yyyy-MM-dd"),
        fuelUsedEndDate:mobileCombustionInfo.fuelUsedEndDate?.format("yyyy-MM-dd")
        //isPublic: mobileCombustionInfo.isPublic
    )
  }
}
