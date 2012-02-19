package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class FranchisesInfoController {

  def franchisesInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "FranchisesInfoController.list( ${params} )"

    def franchisesInfos = franchisesInfoService.findFranchisesInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        franchisesInfos.each { theFranchisesInfo ->
          flushFranchisesInfo xml, theFranchisesInfo
        }
      }
    }
  }

  def save = {
    log.info "FranchisesInfoController.add( ${params} )"        
    def theFranchisesInfo
        
    try {
        theFranchisesInfo = franchisesInfoService.save(params)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theFranchisesInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushFranchisesInfo xml, theFranchisesInfo
      }
    }
  }

  def remove = {
    log.info "FranchisesInfoController.remove( ${params} )"
    franchisesInfoService.remove(params)
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

  private def flushFranchisesInfo = { xml, franchisesInfo ->
    xml.record(
        id: franchisesInfo.id,
        organizationId: franchisesInfo.organizationId,
        
        sourceDescription: franchisesInfo.sourceDescription,        
        franchiseName: franchisesInfo.franchiseName,

        //Franchise Specific Method 
        scope1EmissionsOfFranchise: franchisesInfo.scope1EmissionsOfFranchise,
        scope1EmissionsOfFranchise_Unit: franchisesInfo.scope1EmissionsOfFranchise_Unit,        
        scope2EmissionsOfFranchise: franchisesInfo.scope2EmissionsOfFranchise,
        scope2EmissionsOfFranchise_Unit: franchisesInfo.scope2EmissionsOfFranchise_Unit,
        
        //Average Data Method -  franchises emission - 1 & 2            
        flag_floorSpaceData: franchisesInfo.flag_floorSpaceData,
        
        floorSpace: franchisesInfo.floorSpace,
        floorSpace_Unit: franchisesInfo.floorSpace_Unit,

        average_EF: franchisesInfo.average_EF,            
        average_EF_Unit: franchisesInfo.average_EF_Unit,
        buildingOrAssetName: franchisesInfo.buildingOrAssetName,
        numberOfbuildingsOrAssetTypes: franchisesInfo.numberOfbuildingsOrAssetTypes,

        averageEmissionsPerBuildingOrAssetType: franchisesInfo.averageEmissionsPerBuildingOrAssetType,            
        averageEmissionsPerBuildingOrAssetType_Unit: franchisesInfo.averageEmissionsPerBuildingOrAssetType_Unit,
                    
        //---General fields
        userNotesOnData:franchisesInfo.userNotesOnData,            
        methodType:franchisesInfo.methodType,
        dataBeginDate:franchisesInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:franchisesInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}


