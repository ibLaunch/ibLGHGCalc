package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission


class OptionalSourceInfoController {
  def optionalSourceInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "OptionalSourceInfoController.list( ${params} )"
    def optionalSourceInfos = optionalSourceInfoService.findOptionalSourceInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        optionalSourceInfos.each { theOptionalSourceInfo ->
          flushOptionalSourceInfo xml, theOptionalSourceInfo
        }
      }
    }
  }

  def save = {
    log.info "OptionalSourceInfoController.add( ${params} )"
    def theOptionalSourceInfo
    try {
        theOptionalSourceInfo = optionalSourceInfoService.save(params)
	//-- temporary approach below for now, need more better approach??
	///aclUtilService.addPermission(theOptionalSourceInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
	println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theOptionalSourceInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushOptionalSourceInfo xml, theOptionalSourceInfo
      }
    }
  }

  def remove = {
    log.info "OptionalSourceInfoController.remove( ${params} )"
    optionalSourceInfoService.remove(params)
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

  private def flushOptionalSourceInfo = { xml, optionalSourceInfo ->
    xml.record(
        id: optionalSourceInfo.id,
        organizationId: optionalSourceInfo.organizationId,

        optionalSourceType: optionalSourceInfo.optionalSourceType,
        sourceDescription: optionalSourceInfo.sourceDescription,

        vehicleType: optionalSourceInfo.vehicleType,
        railType: optionalSourceInfo.railType,
        busType: optionalSourceInfo.busType,
        airTravelType: optionalSourceInfo.airTravelType,
        transportType:optionalSourceInfo.transportType,

        annualNumberOfHotelNights: optionalSourceInfo.annualNumberOfHotelNights,
        EF_Hotel: optionalSourceInfo.EF_Hotel,
        EF_Hotel_Unit: optionalSourceInfo.EF_Hotel_Unit,
            
        passengerMiles: optionalSourceInfo.passengerMiles,
        tonMiles: optionalSourceInfo.tonMiles,

        fuelUsedBeginDate:optionalSourceInfo.fuelUsedBeginDate?.format("yyyy-MM-dd"),
        fuelUsedEndDate:optionalSourceInfo.fuelUsedEndDate?.format("yyyy-MM-dd")
    )
  }
}