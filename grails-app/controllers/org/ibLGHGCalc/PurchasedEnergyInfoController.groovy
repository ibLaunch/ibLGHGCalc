package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class PurchasedEnergyInfoController {

  def purchasedEnergyInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "PurchasedEnergyInfoController.list( ${params} )"
    //println "I am in Product Controller------------"

    def purchasedEnergyInfos = purchasedEnergyInfoService.findPurchasedEnergyInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        purchasedEnergyInfos.each { thePurchasedEnergyInfo ->
          flushPurchasedEnergyInfo xml, thePurchasedEnergyInfo
        }
      }
    }
  }

  def save = {
    log.info "PurchasedEnergyInfoController.add( ${params} )"        
    def thePurchasedEnergyInfo
        
    try {
        thePurchasedEnergyInfo = purchasedEnergyInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(thePurchasedEnergyInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "thePurchasedEnergyInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushPurchasedEnergyInfo xml, thePurchasedEnergyInfo
      }
    }
  }

  def remove = {
    log.info "PurchasedEnergyInfoController.remove( ${params} )"
    purchasedEnergyInfoService.remove(params)
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

  private def flushPurchasedEnergyInfo = { xml, purchasedEnergyInfo ->
    xml.record(
        id: purchasedEnergyInfo.id,
        organizationId: purchasedEnergyInfo.organizationId,

        activityType: purchasedEnergyInfo.activityType,   
        fuelType: purchasedEnergyInfo.fuelType,   
        
        sourceDescription: purchasedEnergyInfo.sourceDescription,        
        energyType: purchasedEnergyInfo.energyType,
        supplierName: purchasedEnergyInfo.supplierName,
        supplierContact: purchasedEnergyInfo.supplierContact,        
        supplierRegionCountry: purchasedEnergyInfo.supplierRegionCountry,

        energyPurchased: purchasedEnergyInfo.energyPurchased,
        energyPurchased_Unit: purchasedEnergyInfo.energyPurchased_Unit,        
        flag_upstreamEF: purchasedEnergyInfo.flag_upstreamEF,
        EF_UpstreamEnergy: purchasedEnergyInfo.EF_UpstreamEnergy,
        EF_UpstreamEnergy_Unit: purchasedEnergyInfo.EF_UpstreamEnergy_Unit,
        cradleToGate_EF_Energy: purchasedEnergyInfo.cradleToGate_EF_Energy,
        cradleToGate_EF_Energy_Unit: purchasedEnergyInfo.cradleToGate_EF_Energy_Unit,
        combustion_EF_Energy: purchasedEnergyInfo.combustion_EF_Energy,
        combustion_EF_Energy_Unit: purchasedEnergyInfo.combustion_EF_Energy_Unit,
        
        transAndDistDataType: purchasedEnergyInfo.transAndDistDataType,
        transAndDistLossRate: purchasedEnergyInfo.transAndDistLossRate,            
        scope2EmissionsOfEnergyUse: purchasedEnergyInfo.scope2EmissionsOfEnergyUse,
        scope2EmissionsOfEnergyUse_Unit: purchasedEnergyInfo.scope2EmissionsOfEnergyUse_Unit,
            
            
/*            
        electricityConsumed: purchasedEnergyInfo.electricityConsumed,
        electricityConsumed_Unit: purchasedEnergyInfo.electricityConsumed_Unit,        
        EF_UpstreamElectricity: purchasedEnergyInfo.EF_UpstreamElectricity,
        EF_UpstreamElectricity_Unit: purchasedEnergyInfo.EF_UpstreamElectricity_Unit,
        cradleToGate_EF_Electricity: purchasedEnergyInfo.cradleToGate_EF_Electricity,
        cradleToGate_EF_Electricity_Unit: purchasedEnergyInfo.cradleToGate_EF_Electricity_Unit,
        ombustion_EF_Electricity: purchasedEnergyInfo.combustion_EF_Electricity,
        combustion_EF_Electricity_Unit: purchasedEnergyInfo.combustion_EF_Electricity_Unit,
        
        steamConsumed: purchasedEnergyInfo.steamConsumed,
        steamConsumed_Unit: purchasedEnergyInfo.steamConsumed_Unit,
        EF_UpstreamSteam: purchasedEnergyInfo.EF_UpstreamSteam,
        EF_UpstreamSteam_Unit: purchasedEnergyInfo.EF_UpstreamSteam_Unit,
        cradleToGate_EF_Steam: purchasedEnergyInfo.cradleToGate_EF_Steam,
        cradleToGate_EF_Steam_Unit: purchasedEnergyInfo.cradleToGate_EF_Steam_Unit,
        combustion_EF_Steam: purchasedEnergyInfo.combustion_EF_Steam,
        combustion_EF_Steam_Unit: purchasedEnergyInfo.combustion_EF_Steam_Unit,
            
        heatingConsumed: purchasedEnergyInfo.heatingConsumed,
        heatingConsumed_Unit: purchasedEnergyInfo.heatingConsumed_Unit,
        EF_UpstreamHeating: purchasedEnergyInfo.EF_UpstreamHeating,
        EF_UpstreamHeating_Unit: purchasedEnergyInfo.EF_UpstreamHeating_Unit,
        cradleToGate_EF_Heating: purchasedEnergyInfo.cradleToGate_EF_Heating,
        cradleToGate_EF_Heating_Unit: purchasedEnergyInfo.cradleToGate_EF_Heating_Unit,
        combustion_EF_Heating: purchasedEnergyInfo.combustion_EF_Heating,
        combustion_EF_Heating_Unit: purchasedEnergyInfo.combustion_EF_Heating_Unit,
        
        coolingConsumed: purchasedEnergyInfo.coolingConsumed,
        coolingConsumed_Unit: purchasedEnergyInfo.coolingConsumed_Unit,
        EF_UpstreamCooling: purchasedEnergyInfo.EF_UpstreamCooling,
        EF_UpstreamCooling_Unit: purchasedEnergyInfo.EF_UpstreamCooling_Unit,
        cradleToGate_EF_Cooling: purchasedEnergyInfo.cradleToGate_EF_Cooling,
        cradleToGate_EF_Cooling_Unit: purchasedEnergyInfo.cradleToGate_EF_Cooling_Unit,
        combustion_EF_Cooling: purchasedEnergyInfo.combustion_EF_Cooling,
        combustion_EF_Cooling_Unit: purchasedEnergyInfo.combustion_EF_Cooling_Unit,        
*/            
        //---General fields
        userNotesOnData:purchasedEnergyInfo.userNotesOnData,            
        methodType:purchasedEnergyInfo.methodType,
        dataBeginDate:purchasedEnergyInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:purchasedEnergyInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}


