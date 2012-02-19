package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class DistributionInfoController {

  def distributionInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "DistributionInfoController.list( ${params} )"
    //println "I am in Product Controller------------"

    def distributionInfos = distributionInfoService.findDistributionInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        distributionInfos.each { theDistributionInfo ->
          flushDistributionInfo xml, theDistributionInfo
        }
      }
    }
  }

  def save = {
    log.info "DistributionInfoController.add( ${params} )"        
    def theDistributionInfo
        
    try {
        theDistributionInfo = distributionInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theDistributionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theDistributionInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushDistributionInfo xml, theDistributionInfo
      }
    }
  }

  def remove = {
    log.info "DistributionInfoController.remove( ${params} )"
    distributionInfoService.remove(params)
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

  private def flushDistributionInfo = { xml, distributionInfo ->
    xml.record(
        id: distributionInfo.id,
        organizationId: distributionInfo.organizationId,
        
        sourceDescription: distributionInfo.sourceDescription,        
        streamType: distributionInfo.streamType,
        serviceProviderName: distributionInfo.serviceProviderName,
        serviceProviderContact: distributionInfo.serviceProviderContact,        
        storageFacility: distributionInfo.storageFacility,

        fuelType: distributionInfo.fuelType,
        fuelConsumed: distributionInfo.fuelConsumed,        
        fuelConsumed_Unit: distributionInfo.fuelConsumed_Unit,
        EF_Fuel: distributionInfo.EF_Fuel,
        EF_Fuel_Unit: distributionInfo.EF_Fuel_Unit,
        
        electricityConsumed: distributionInfo.electricityConsumed,
        electricityConsumed_Unit: distributionInfo.electricityConsumed_Unit,
        EF_Electricity: distributionInfo.EF_Electricity,
        EF_Electricity_Unit: distributionInfo.EF_Electricity_Unit,

        refrigerantType: distributionInfo.refrigerantType,            
        refrigerantLeakage: distributionInfo.refrigerantLeakage,
        refrigerantLeakage_Unit: distributionInfo.refrigerantLeakage_Unit,            
        EF_Refrigerant: distributionInfo.EF_Refrigerant,
        EF_Refrigerant_Unit: distributionInfo.EF_Refrigerant_Unit,

        allocatedEmissionsOfStorageFacility: distributionInfo.allocatedEmissionsOfStorageFacility,            
        allocatedEmissionsOfStorageFacility_Unit: distributionInfo.allocatedEmissionsOfStorageFacility_Unit,
        volumeOfReportingCompanysPurchasedGoods: distributionInfo.volumeOfReportingCompanysPurchasedGoods,            
        volumeOfReportingCompanysPurchasedGoods_Unit: distributionInfo.volumeOfReportingCompanysPurchasedGoods_Unit,
        totalVolumeOfGoodsInStorageFacility: distributionInfo.totalVolumeOfGoodsInStorageFacility,
        totalVolumeOfGoodsInStorageFacility_Unit: distributionInfo.totalVolumeOfGoodsInStorageFacility_Unit,
        emissionsOfStorageFacility: distributionInfo.emissionsOfStorageFacility,
        emissionsOfStorageFacility_Unit: distributionInfo.emissionsOfStorageFacility_Unit,
        
        //Average Data Method -  distribution emission - 1 & 2
        storedGoodsInReportingYear: distributionInfo.storedGoodsInReportingYear,
        storedGoodsInReportingYear_Unit: distributionInfo.storedGoodsInReportingYear_Unit,
        EF_StorageFacility: distributionInfo.EF_StorageFacility,
        EF_StorageFacility_Unit: distributionInfo.EF_StorageFacility_Unit,
            
        //---General fields
        userNotesOnData:distributionInfo.userNotesOnData,            
        methodType:distributionInfo.methodType,
        dataBeginDate:distributionInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:distributionInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}


