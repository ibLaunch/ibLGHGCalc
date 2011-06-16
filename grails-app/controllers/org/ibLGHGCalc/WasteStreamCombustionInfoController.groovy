package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class WasteStreamCombustionInfoController {
  def wasteStreamCombustionInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "WasteStreamCombustionInfoController.list( ${params} )"
    def wasteStreamCombustionInfos = wasteStreamCombustionInfoService.findWasteStreamCombustionInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        wasteStreamCombustionInfos.each { theWasteStreamCombustionInfo ->
          flushWasteStreamCombustionInfo xml, theWasteStreamCombustionInfo
        }
      }
    }
  }

  def save = {
    log.info "WasteStreamCombustionInfoController.add( ${params} )"
    def theWasteStreamCombustionInfo
    try {
        theWasteStreamCombustionInfo = wasteStreamCombustionInfoService.save(params)
	//-- temporary approach below for now, need more better approach??
	aclUtilService.addPermission(theWasteStreamCombustionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
	println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theWasteStreamCombustionInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushWasteStreamCombustionInfo xml, theWasteStreamCombustionInfo
      }
    }
  }

  def remove = {
    log.info "WasteStreamCombustionInfoController.remove( ${params} )"
    wasteStreamCombustionInfoService.remove(params)
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

  private def flushWasteStreamCombustionInfo = { xml, wasteStreamCombustionInfo ->
    xml.record(
        id: wasteStreamCombustionInfo.id,
        organizationId: wasteStreamCombustionInfo.organizationId,

        fuelSourceDescription: wasteStreamCombustionInfo.fuelSourceDescription,

        amountOfWasterStreamGasCombusted: wasteStreamCombustionInfo.amountOfWasterStreamGasCombusted,
        amountOfWasterStreamGasCombustedUnit: wasteStreamCombustionInfo.amountOfWasterStreamGasCombustedUnit,

        totalNumberOfMolesPerUnitVolument: wasteStreamCombustionInfo.totalNumberOfMolesPerUnitVolument,
        totalNumberOfMolesPerUnitVolumentUnit: wasteStreamCombustionInfo.totalNumberOfMolesPerUnitVolumentUnit,
        
        carbonMonoxideMolarFractionPercent: wasteStreamCombustionInfo.carbonMonoxideMolarFractionPercent,
        carbonDioxideMolarFractionPercent: wasteStreamCombustionInfo.carbonDioxideMolarFractionPercent,
        methaneMolarFractionPercent: wasteStreamCombustionInfo.methaneMolarFractionPercent,
        cetyleneMolarFractionPercent: wasteStreamCombustionInfo.cetyleneMolarFractionPercent,
        ethyleneMolarFractionPercent: wasteStreamCombustionInfo.ethyleneMolarFractionPercent,
        ethaneMolarFractionPercent: wasteStreamCombustionInfo.ethaneMolarFractionPercent,
        propyleneMolarFractionPercent: wasteStreamCombustionInfo.propyleneMolarFractionPercent,
        propaneMolarFractionPercent: wasteStreamCombustionInfo.propaneMolarFractionPercent,
        n_ButaneMolarFractionPercent: wasteStreamCombustionInfo.n_ButaneMolarFractionPercent,
        benzeneMolarFractionPercent: wasteStreamCombustionInfo.benzeneMolarFractionPercent,
        bexaneMolarFractionPercent: wasteStreamCombustionInfo.bexaneMolarFractionPercent,
        tolueneMolarFractionPercent: wasteStreamCombustionInfo.tolueneMolarFractionPercent,
        octaneMolarFractionPercent: wasteStreamCombustionInfo.octaneMolarFractionPercent,
        ethanolMolarFractionPercent: wasteStreamCombustionInfo.ethanolMolarFractionPercent,
        acetoneMolarFractionPercent: wasteStreamCombustionInfo.acetoneMolarFractionPercent,
        tetrahydrofuranMolarFractionPercent: wasteStreamCombustionInfo.tetrahydrofuranMolarFractionPercent,
        otherNon_CMolarFractionPercent: wasteStreamCombustionInfo.otherNon_CMolarFractionPercent,

        oxidationFactorPercent: wasteStreamCombustionInfo.oxidationFactorPercent,

        fuelUsedBeginDate:wasteStreamCombustionInfo.fuelUsedBeginDate?.format("yyyy-MM-dd"),
        fuelUsedEndDate:wasteStreamCombustionInfo.fuelUsedEndDate?.format("yyyy-MM-dd")
    )
  }
}
