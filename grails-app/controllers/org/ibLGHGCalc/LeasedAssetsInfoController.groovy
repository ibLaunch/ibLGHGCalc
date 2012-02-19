package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class LeasedAssetsInfoController {

  def leasedAssetsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "LeasedAssetsInfoController.list( ${params} )"

    def leasedAssetsInfos = leasedAssetsInfoService.findLeasedAssetsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        leasedAssetsInfos.each { theLeasedAssetsInfo ->
          flushLeasedAssetsInfo xml, theLeasedAssetsInfo
        }
      }
    }
  }

  def save = {
    log.info "LeasedAssetsInfoController.add( ${params} )"        
    def theLeasedAssetsInfo
        
    try {
        theLeasedAssetsInfo = leasedAssetsInfoService.save(params)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theLeasedAssetsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushLeasedAssetsInfo xml, theLeasedAssetsInfo
      }
    }
  }

  def remove = {
    log.info "LeasedAssetsInfoController.remove( ${params} )"
    leasedAssetsInfoService.remove(params)
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

  private def flushLeasedAssetsInfo = { xml, leasedAssetsInfo ->
    xml.record(
        id: leasedAssetsInfo.id,
        organizationId: leasedAssetsInfo.organizationId,
        
        sourceDescription: leasedAssetsInfo.sourceDescription,        
        streamType: leasedAssetsInfo.streamType,
        typeOfLeasingArrangement: leasedAssetsInfo.typeOfLeasingArrangement,
        lessorOrLessee: leasedAssetsInfo.lessorOrLessee,        
        leasedAssetName: leasedAssetsInfo.leasedAssetName,

        scope1EmissionsOfLeasedAsset: leasedAssetsInfo.scope1EmissionsOfLeasedAsset,
        scope1EmissionsOfLeasedAsset_Unit: leasedAssetsInfo.scope1EmissionsOfLeasedAsset_Unit,        
        scope2EmissionsOfLeasedAsset: leasedAssetsInfo.scope2EmissionsOfLeasedAsset,
        scope2EmissionsOfLeasedAsset_Unit: leasedAssetsInfo.scope2EmissionsOfLeasedAsset_Unit,
        
        //Average Data Method -  leasedAssets emission - 1 & 2            
        flag_floorSpaceData: leasedAssetsInfo.flag_floorSpaceData,
        //emissionsFromCommercialAsset: leasedAssetsInfo.emissionsFromCommercialAsset,
        floorSpace: leasedAssetsInfo.floorSpace,
        floorSpace_Unit: leasedAssetsInfo.floorSpace_Unit,

        average_EF: leasedAssetsInfo.average_EF,            
        average_EF_Unit: leasedAssetsInfo.average_EF_Unit,
        //emissionsFromOtherAsset: leasedAssetsInfo.emissionsFromOtherAsset,                    
        //emissionsFromOtherAsset_Unit: leasedAssetsInfo.emissionsFromOtherAsset_Unit,
        numberOfbuildingsOrAssetTypes: leasedAssetsInfo.numberOfbuildingsOrAssetTypes,

        averageEmissionsPerBuildingOrAssetType: leasedAssetsInfo.averageEmissionsPerBuildingOrAssetType,            
        averageEmissionsPerBuildingOrAssetType_Unit: leasedAssetsInfo.averageEmissionsPerBuildingOrAssetType_Unit,
                    
        //---General fields
        userNotesOnData:leasedAssetsInfo.userNotesOnData,            
        methodType:leasedAssetsInfo.methodType,
        dataBeginDate:leasedAssetsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:leasedAssetsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}


