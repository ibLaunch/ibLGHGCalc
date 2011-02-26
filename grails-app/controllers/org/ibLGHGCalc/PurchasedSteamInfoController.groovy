package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class PurchasedSteamInfoController {
    
  def purchasedSteamInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "PurchasedSteamInfoController.list( ${params} )"
    println "I am in Steam Controller------------"
    //--findPurchasedSteamInfos() below is not working, not sure why. Hence changed the name to getP..(), but after trying that findPu..() started working.
    def purchasedSteamInfos = purchasedSteamInfoService.findPurchasedSteamInfos(params);

    //def purchasedSteamInfos = purchasedSteamInfoService.getPurchasedSteamInfos(params);
    // Map tempMap = [:]
    //tempMap.put("organizationId", 1)
    
    //def purchasedSteamInfos = purchasedSteamInfoService.findPurchasedSteamInfos(tempMap)
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        purchasedSteamInfos.each { thePurchasedSteamInfo ->
          flushPurchasedSteamInfo xml, thePurchasedSteamInfo
        }
      }
    }
  }

  def save = {
    log.info "PurchasedSteamInfoController.add( ${params} )"
    def thePurchasedSteamInfo
    try {
        thePurchasedSteamInfo = purchasedSteamInfoService.save(params)
	//-- temporary approach below for now, need more better approach??
	aclUtilService.addPermission(thePurchasedSteamInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
	println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "thePurchasedSteamInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushPurchasedSteamInfo xml, thePurchasedSteamInfo
      }
    }
  }

  def remove = {
    log.info "PurchasedSteamInfoController.remove( ${params} )"
    purchasedSteamInfoService.remove(params)
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

  private def flushPurchasedSteamInfo = { xml, purchasedSteamInfo ->
    xml.record(
        id: purchasedSteamInfo.id,
        organizationId: purchasedSteamInfo.organizationId,

        sourceDescription: purchasedSteamInfo.sourceDescription,
        fuelType: purchasedSteamInfo.fuelType,
        boilerEfficiencyPercent: purchasedSteamInfo.boilerEfficiencyPercent,
        purchasedSteam: purchasedSteamInfo.purchasedSteam,
        purchasedSteamUnit: purchasedSteamInfo.purchasedSteamUnit,

        supplierCO2Multiplier: purchasedSteamInfo.supplierCO2Multiplier,
        supplierCH4Multiplier: purchasedSteamInfo.supplierCH4Multiplier,
        supplierN2OMultiplier: purchasedSteamInfo.supplierN2OMultiplier,

        supplierCO2MultiplierUnit: purchasedSteamInfo.supplierCO2MultiplierUnit,
        supplierCH4MultiplierUnit: purchasedSteamInfo.supplierCH4MultiplierUnit,
        supplierN2OMultiplierUnit: purchasedSteamInfo.supplierN2OMultiplierUnit,

        fuelUsedBeginDate:purchasedSteamInfo.fuelUsedBeginDate,
        fuelUsedEndDate:purchasedSteamInfo.fuelUsedEndDate
    )
  }
}