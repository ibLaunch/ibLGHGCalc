package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class BusinessTravelInfoController {

  def businessTravelInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "BusinessTravelInfoController.list( ${params} )"
    //println "I am in Product Controller------------"

    def businessTravelInfos = businessTravelInfoService.findBusinessTravelInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        businessTravelInfos.each { theBusinessTravelInfo ->
          flushBusinessTravelInfo xml, theBusinessTravelInfo
        }
      }
    }
  }

  def save = {
    log.info "BusinessTravelInfoController.add( ${params} )"        
    def theBusinessTravelInfo
        
    try {
        theBusinessTravelInfo = businessTravelInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theBusinessTravelInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theBusinessTravelInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushBusinessTravelInfo xml, theBusinessTravelInfo
      }
    }
  }

  def remove = {
    log.info "BusinessTravelInfoController.remove( ${params} )"
    businessTravelInfoService.remove(params)
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

  private def flushBusinessTravelInfo = { xml, businessTravelInfo ->
    xml.record(
        id: businessTravelInfo.id,
        organizationId: businessTravelInfo.organizationId,
        
        sourceDescription: businessTravelInfo.sourceDescription,        
        activityType: businessTravelInfo.activityType,        
        //serviceProviderContact: businessTravelInfo.serviceProviderContact,        
        
        //Travel
        vehicleType: businessTravelInfo.vehicleType,
        distanceTravelledByVehicleType: businessTravelInfo.distanceTravelledByVehicleType,
        distanceTravelledByVehicleType_Unit: businessTravelInfo.distanceTravelledByVehicleType_Unit,        
        EF_VehicleType: businessTravelInfo.EF_VehicleType,
        EF_VehicleType_Unit: businessTravelInfo.EF_VehicleType_Unit,
        
        //Hotel
        annualNumberOfHotelNights: businessTravelInfo.annualNumberOfHotelNights,
        EF_Hotel: businessTravelInfo.EF_Hotel,        
        EF_Hotel_Unit: businessTravelInfo.EF_Hotel_Unit,        
        
        //---General fields
        userNotesOnData:businessTravelInfo.userNotesOnData,            
        methodType:businessTravelInfo.methodType,
        dataBeginDate:businessTravelInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:businessTravelInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
