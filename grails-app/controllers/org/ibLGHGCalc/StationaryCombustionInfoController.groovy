package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

//import org.springframework.security.access.prepost.PostFilter
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class StationaryCombustionInfoController {

  def stationaryCombustionInfoService
  def aclUtilService
  def springSecurityService
  
  def list = {
    log.info "StationaryCombustionInfoController.list( ${params} )"
    def stationaryCombustionInfos = stationaryCombustionInfoService.findStationaryCombustionInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        stationaryCombustionInfos.each { theStationaryCombustionInfo ->
          flushStationaryCombustionInfo xml, theStationaryCombustionInfo
        }
      }
    }
  }

  //@Transactional
  //@PreAuthorize("hasRole('ROLE_USER')")
  def save = {
    log.info "StationaryCombustionInfoController.add( ${params} )"
    def theStationaryCombustionInfo
    try {
        theStationaryCombustionInfo = stationaryCombustionInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        aclUtilService.addPermission(theStationaryCombustionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theStationaryCombustionInfo not Saved"
    }

    println "theStationaryCombustionInfo from Controller is: " + theStationaryCombustionInfo

    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushStationaryCombustionInfo xml, theStationaryCombustionInfo
      }
    }
  }

  def remove = {
    log.info "StationaryCombustionInfoController.remove( ${params} )"
    stationaryCombustionInfoService.remove(params)
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

  private def flushStationaryCombustionInfo = { xml, stationaryCombustionInfo ->
    xml.record(
        id: stationaryCombustionInfo.id,
        organizationId: stationaryCombustionInfo.organizationId,
        fuelSourceDescription: stationaryCombustionInfo.fuelSourceDescription,
        fuelType: stationaryCombustionInfo.fuelType,
        fuelQuantity: stationaryCombustionInfo.fuelQuantity,
        fuelUnit: stationaryCombustionInfo.fuelUnit,
        fuelUsedBeginDate:stationaryCombustionInfo.fuelUsedBeginDate,
        fuelUsedEndDate:stationaryCombustionInfo.fuelUsedEndDate
        //isPublic: stationaryCombustionInfo.isPublic
    )
  }
}
