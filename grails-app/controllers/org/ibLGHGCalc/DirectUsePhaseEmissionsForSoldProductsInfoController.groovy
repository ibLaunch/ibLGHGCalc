package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class DirectUsePhaseEmissionsForSoldProductsInfoController {

  def directUsePhaseEmissionsForSoldProductsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "DirectUsePhaseEmissionsForSoldProductsInfoController.list( ${params} )"

    def directUsePhaseEmissionsForSoldProductsInfos = directUsePhaseEmissionsForSoldProductsInfoService.findDirectUsePhaseEmissionsForSoldProductsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        directUsePhaseEmissionsForSoldProductsInfos.each { theDirectUsePhaseEmissionsForSoldProductsInfo ->
          flushDirectUsePhaseEmissionsForSoldProductsInfo xml, theDirectUsePhaseEmissionsForSoldProductsInfo
        }
      }
    }
  }

  def save = {
    log.info "DirectUsePhaseEmissionsForSoldProductsInfoController.add( ${params} )"        
    def theDirectUsePhaseEmissionsForSoldProductsInfo
        
    try {
        theDirectUsePhaseEmissionsForSoldProductsInfo = directUsePhaseEmissionsForSoldProductsInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theDirectUsePhaseEmissionsForSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theDirectUsePhaseEmissionsForSoldProductsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushDirectUsePhaseEmissionsForSoldProductsInfo xml, theDirectUsePhaseEmissionsForSoldProductsInfo
      }
    }
  }

  def remove = {
    log.info "DirectUsePhaseEmissionsForSoldProductsInfoController.remove( ${params} )"
    directUsePhaseEmissionsForSoldProductsInfoService.remove(params)
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

  private def flushDirectUsePhaseEmissionsForSoldProductsInfo = { xml, directUsePhaseEmissionsForSoldProductsInfo ->
    xml.record(
        id: directUsePhaseEmissionsForSoldProductsInfo.id,
        organizationId: directUsePhaseEmissionsForSoldProductsInfo.organizationId,
        
        sourceDescription: directUsePhaseEmissionsForSoldProductsInfo.sourceDescription,        
        productType: directUsePhaseEmissionsForSoldProductsInfo.productType,
        productName: directUsePhaseEmissionsForSoldProductsInfo.productName,
		
	//Direct Use Phase Emissions – Energy Using Products - Lifetime-Uses Method								
        totalLifetimeExpectedUsesOfProduct: directUsePhaseEmissionsForSoldProductsInfo.totalLifetimeExpectedUsesOfProduct,        
        numberSoldInReportingPeriod: directUsePhaseEmissionsForSoldProductsInfo.numberSoldInReportingPeriod,

        fuelType: directUsePhaseEmissionsForSoldProductsInfo.fuelType,
        fuelConsumedPerUse: directUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUse,        
        fuelConsumedPerUse_Unit: directUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUse_Unit,
        EF_Fuel: directUsePhaseEmissionsForSoldProductsInfo.EF_Fuel,
        EF_Fuel_Unit: directUsePhaseEmissionsForSoldProductsInfo.EF_Fuel_Unit,
        
        electricityConsumedPerUse: directUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUse,
        electricityConsumedPerUse_Unit: directUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUse_Unit,
        EF_Electricity: directUsePhaseEmissionsForSoldProductsInfo.EF_Electricity,
        EF_Electricity_Unit: directUsePhaseEmissionsForSoldProductsInfo.EF_Electricity_Unit,

        refrigerantType: directUsePhaseEmissionsForSoldProductsInfo.refrigerantType,            
        refrigerantLeakagePerUse: directUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUse,
        refrigerantLeakagePerUse_Unit: directUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUse_Unit,            
        EF_Refrigerant: directUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant,
        EF_Refrigerant_Unit: directUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant_Unit,
        
        //Direct Use Phase Emissions – Fuels and Feedstocks - Combustion Method								
        totalQuantityOfFuelOrFeedstockSold: directUsePhaseEmissionsForSoldProductsInfo.totalQuantityOfFuelOrFeedstockSold,
        totalQuantityOfFuelOrfeedstockSold_Unit: directUsePhaseEmissionsForSoldProductsInfo.totalQuantityOfFuelOrfeedstockSold_Unit,
        combustion_EF_ForFuelOrFeedstock: directUsePhaseEmissionsForSoldProductsInfo.combustion_EF_ForFuelOrFeedstock,
        combustion_EF_ForFuelOrFeedstock_Unit: directUsePhaseEmissionsForSoldProductsInfo.combustion_EF_ForFuelOrFeedstock_Unit,

	//Direct Use Phase Emissions – Product Containing GHGs that are Emitted During Use - % GHG released Method								
        GHG_Name: directUsePhaseEmissionsForSoldProductsInfo.GHG_Name,
        GHG_PerProduct: directUsePhaseEmissionsForSoldProductsInfo.GHG_PerProduct,
        percentOfGHGReleasedDuringLifetimeUseOfProduct: directUsePhaseEmissionsForSoldProductsInfo.percentOfGHGReleasedDuringLifetimeUseOfProduct,
        GWP_GHG: directUsePhaseEmissionsForSoldProductsInfo.GWP_GHG,
		
        //---General fields
        userNotesOnData:directUsePhaseEmissionsForSoldProductsInfo.userNotesOnData,            
        methodType:directUsePhaseEmissionsForSoldProductsInfo.methodType,
        dataBeginDate:directUsePhaseEmissionsForSoldProductsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:directUsePhaseEmissionsForSoldProductsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
