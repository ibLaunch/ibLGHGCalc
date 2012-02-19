package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class TransportationInfoController {

  def transportationInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "TransportationInfoController.list( ${params} )"
    //println "I am in Product Controller------------"

    def transportationInfos = transportationInfoService.findTransportationInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        transportationInfos.each { theTransportationInfo ->
          flushTransportationInfo xml, theTransportationInfo
        }
      }
    }
  }

  def save = {
    log.info "TransportationInfoController.add( ${params} )"        
    def theTransportationInfo
        
    try {
        theTransportationInfo = transportationInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theTransportationInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theTransportationInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushTransportationInfo xml, theTransportationInfo
      }
    }
  }

  def remove = {
    log.info "TransportationInfoController.remove( ${params} )"
    transportationInfoService.remove(params)
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

  private def flushTransportationInfo = { xml, transportationInfo ->
    xml.record(
        id: transportationInfo.id,
        organizationId: transportationInfo.organizationId,

        sourceDescription: transportationInfo.sourceDescription,        
        streamType: transportationInfo.streamType,   

        serviceProviderName: transportationInfo.serviceProviderName,
        serviceProviderContact: transportationInfo.serviceProviderContact,        

        //Fuel Based Method -  transport emission
        fuelDataType:transportationInfo.fuelDataType,
        fuelType: transportationInfo.fuelType,           
        fuelConsumed: transportationInfo.fuelConsumed,
        fuelConsumed_Unit: transportationInfo.fuelConsumed_Unit,                
        EF_Fuel: transportationInfo.EF_Fuel,
        EF_Fuel_Unit: transportationInfo.EF_Fuel_Unit,    
        
        refrigerantType:transportationInfo.refrigerantType,
        refrigerantLeakage: transportationInfo.refrigerantLeakage,
        refrigerantLeakage_Unit: transportationInfo.refrigerantLeakage_Unit,
        EF_Refrigerant: transportationInfo.EF_Refrigerant,
        EF_Refrigerant_Unit: transportationInfo.EF_Refrigerant_Unit,

        //flag_calculateFuelConsumed: transportationInfo.flag_calculateFuelConsumed,            
        totalDistanceTravelled: transportationInfo.totalDistanceTravelled,
        totalDistanceTravelled_Unit: transportationInfo.totalDistanceTravelled_Unit,            
        vehicleType: transportationInfo.vehicleType,
        vehicleName: transportationInfo.vehicleName,
        fuelEfficiencyOfVehicle: transportationInfo.fuelEfficiencyOfVehicle,
        fuelEfficiencyOfVehicle_Unit: transportationInfo.fuelEfficiencyOfVehicle_Unit,

        //flag_calculateAllocatedFuelUse: transportationInfo.flag_calculateAllocatedFuelUse,
        allocatedFuelUse: transportationInfo.allocatedFuelUse,
        allocatedFuelUse_Unit: transportationInfo.allocatedFuelUse_Unit,            
        totalFuelConsumed: transportationInfo.totalFuelConsumed,
        totalFuelConsumed_Unit: transportationInfo.totalFuelConsumed_Unit,
        //massOrVolumeOfCompanyGoods: transportationInfo.massOrVolumeOfCompanyGoods,
        companyGoodsTransported: transportationInfo.companyGoodsTransported,        
        companyGoodsTransported_Unit: transportationInfo.companyGoodsTransported_Unit,
        companyGoodsTransported_MassType: transportationInfo.companyGoodsTransported_MassType,        
        totalGoodsTransportedByCarrier: transportationInfo.totalGoodsTransportedByCarrier,
        totalGoodsTransportedByCarrier_Unit: transportationInfo.totalGoodsTransportedByCarrier_Unit,
        totalGoodsTransportedByCarrier_MassType: transportationInfo.totalGoodsTransportedByCarrier_MassType,
        //massType: transportationInfo.massType,
                
        flag_unladenBackhaul: transportationInfo.flag_unladenBackhaul,

        //Distance Based Method -  transport emission								            
        transportModeOrVehicleType: transportationInfo.transportModeOrVehicleType,            
        massOfGoodsPurchased: transportationInfo.massOfGoodsPurchased,
        massOfGoodsPurchased_Unit: transportationInfo.massOfGoodsPurchased_Unit,
        distanceTraveledInTransportLeg: transportationInfo.distanceTraveledInTransportLeg,
        distanceTraveledInTransportLeg_Unit: transportationInfo.distanceTraveledInTransportLeg_Unit,
        EF_TransportModeOrVehicleType: transportationInfo.EF_TransportModeOrVehicleType,
        EF_TransportModeOrVehicleType_Unit: transportationInfo.EF_TransportModeOrVehicleType_Unit,
        
        //---General fields
        userNotesOnData:transportationInfo.userNotesOnData,            
        methodType:transportationInfo.methodType,
        dataBeginDate:transportationInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:transportationInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}


