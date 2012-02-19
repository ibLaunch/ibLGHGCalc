package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class InDirectUsePhaseEmissionsForSoldProductsInfoController {

  def inDirectUsePhaseEmissionsForSoldProductsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "InDirectUsePhaseEmissionsForSoldProductsInfoController.list( ${params} )"

    def inDirectUsePhaseEmissionsForSoldProductsInfos = inDirectUsePhaseEmissionsForSoldProductsInfoService.findInDirectUsePhaseEmissionsForSoldProductsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        inDirectUsePhaseEmissionsForSoldProductsInfos.each { theInDirectUsePhaseEmissionsForSoldProductsInfo ->
          flushInDirectUsePhaseEmissionsForSoldProductsInfo xml, theInDirectUsePhaseEmissionsForSoldProductsInfo
        }
      }
    }
  }

  def save = {
    log.info "InDirectUsePhaseEmissionsForSoldProductsInfoController.add( ${params} )"        
    def theInDirectUsePhaseEmissionsForSoldProductsInfo
        
    try {
        theInDirectUsePhaseEmissionsForSoldProductsInfo = inDirectUsePhaseEmissionsForSoldProductsInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theInDirectUsePhaseEmissionsForSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theInDirectUsePhaseEmissionsForSoldProductsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushInDirectUsePhaseEmissionsForSoldProductsInfo xml, theInDirectUsePhaseEmissionsForSoldProductsInfo
      }
    }
  }

  def remove = {
    log.info "InDirectUsePhaseEmissionsForSoldProductsInfoController.remove( ${params} )"
    inDirectUsePhaseEmissionsForSoldProductsInfoService.remove(params)
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

  private def flushInDirectUsePhaseEmissionsForSoldProductsInfo = { xml, inDirectUsePhaseEmissionsForSoldProductsInfo ->
    xml.record(
        id: inDirectUsePhaseEmissionsForSoldProductsInfo.id,
        organizationId: inDirectUsePhaseEmissionsForSoldProductsInfo.organizationId,
        
        sourceDescription: inDirectUsePhaseEmissionsForSoldProductsInfo.sourceDescription,        
        productType: inDirectUsePhaseEmissionsForSoldProductsInfo.productType,
        productName: inDirectUsePhaseEmissionsForSoldProductsInfo.productName,
		
	//InDirect Use Phase Emissions- Typical Use Phase Profile Method								
        totalLifetimeExpectedUsesOfProduct: inDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeExpectedUsesOfProduct,        
        scenarioDescription: inDirectUsePhaseEmissionsForSoldProductsInfo.scenarioDescription,        
        percentOfTotalLifetimeUsesInThisScenario: inDirectUsePhaseEmissionsForSoldProductsInfo.percentOfTotalLifetimeUsesInThisScenario,                
        numberSoldInReportingPeriod: inDirectUsePhaseEmissionsForSoldProductsInfo.numberSoldInReportingPeriod,

        fuelType: inDirectUsePhaseEmissionsForSoldProductsInfo.fuelType,
        fuelConsumedPerUseInThisScenario: inDirectUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUseInThisScenario,        
        fuelConsumedPerUseInThisScenario_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.fuelConsumedPerUseInThisScenario_Unit,
        EF_Fuel: inDirectUsePhaseEmissionsForSoldProductsInfo.EF_Fuel,
        EF_Fuel_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.EF_Fuel_Unit,
        
        electricityConsumedPerUseInThisScenario: inDirectUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUseInThisScenario,
        electricityConsumedPerUseInThisScenario_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.electricityConsumedPerUseInThisScenario_Unit,
        EF_Electricity: inDirectUsePhaseEmissionsForSoldProductsInfo.EF_Electricity,
        EF_Electricity_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.EF_Electricity_Unit,

        refrigerantType: inDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantType,            
        refrigerantLeakagePerUseInThisScenario: inDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUseInThisScenario,
        refrigerantLeakagePerUseInThisScenario_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.refrigerantLeakagePerUseInThisScenario_Unit,            
        EF_Refrigerant: inDirectUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant,
        EF_Refrigerant_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.EF_Refrigerant_Unit,
                
        GHG_EmittedIndirectly: inDirectUsePhaseEmissionsForSoldProductsInfo.GHG_EmittedIndirectly,
        GHG_EmittedIndirectly_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.GHG_EmittedIndirectly_Unit,
        GHG_Name: inDirectUsePhaseEmissionsForSoldProductsInfo.GHG_Name,
        GWP_GHG: inDirectUsePhaseEmissionsForSoldProductsInfo.GWP_GHG,

    	//Functional Unit Method - Emissions Intensity Metrics - In-Direct Use Phase Emissions
        functionalUnitsPerformedPerProduct: inDirectUsePhaseEmissionsForSoldProductsInfo.functionalUnitsPerformedPerProduct,
        emissionsPerFunctionalUnitOfProduct: inDirectUsePhaseEmissionsForSoldProductsInfo.emissionsPerFunctionalUnitOfProduct,
        emissionsPerFunctionalUnitOfProduct_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.emissionsPerFunctionalUnitOfProduct_Unit,
        totalLifetimeEmissions: inDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeEmissions,
        totalLifetimeEmissions_Unit: inDirectUsePhaseEmissionsForSoldProductsInfo.totalLifetimeEmissions_Unit,
        
        //---General fields
        userNotesOnData:inDirectUsePhaseEmissionsForSoldProductsInfo.userNotesOnData,            
        methodType:inDirectUsePhaseEmissionsForSoldProductsInfo.methodType,
        dataBeginDate:inDirectUsePhaseEmissionsForSoldProductsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:inDirectUsePhaseEmissionsForSoldProductsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
