package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission


class PurchasedElectricityInfoController {
  def purchasedElectricityInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "PurchasedElectricityInfoController.list( ${params} )"
    def purchasedElectricityInfos = purchasedElectricityInfoService.findPurchasedElectricityInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        purchasedElectricityInfos.each { thePurchasedElectricityInfo ->
          flushPurchasedElectricityInfo xml, thePurchasedElectricityInfo
        }
      }
    }
  }

  def save = {
    log.info "PurchasedElectricityInfoController.add( ${params} )"
    def thePurchasedElectricityInfo
    try {
        thePurchasedElectricityInfo = purchasedElectricityInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        aclUtilService.addPermission(thePurchasedElectricityInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "thePurchasedElectricityInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushPurchasedElectricityInfo xml, thePurchasedElectricityInfo
      }
    }
  }

  def remove = {
    log.info "PurchasedElectricityInfoController.remove( ${params} )"
    purchasedElectricityInfoService.remove(params)
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

  private def flushPurchasedElectricityInfo = { xml, purchasedElectricityInfo ->
    xml.record(
        id: purchasedElectricityInfo.id,
        organizationId: purchasedElectricityInfo.organizationId,
        sourceDescription: purchasedElectricityInfo.sourceDescription,
        eGRIDSubregion: purchasedElectricityInfo.eGRIDSubregion,
        purchasedElectricity: purchasedElectricityInfo.purchasedElectricity,
        purchasedElectricityUnit: purchasedElectricityInfo.purchasedElectricityUnit,
        fuelUsedBeginDate:purchasedElectricityInfo.fuelUsedBeginDate,
        fuelUsedEndDate:purchasedElectricityInfo.fuelUsedEndDate
    )
  }
}
