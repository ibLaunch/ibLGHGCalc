package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class WasteOutputFrom_T1S_InfoController {
      
  def wasteOutputFrom_T1S_InfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "WasteOutputFrom_T1S_InfoController.list( ${params} )"

    //--findWasteOutputFrom_T1S_Infos() below is not working, not sure why. Hence changed the name to getP..(), but after trying that findPu..() started working.
    def wasteOutputFrom_T1S_Infos = wasteOutputFrom_T1S_InfoService.findWasteOutputFrom_T1S_Infos(params);
        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        wasteOutputFrom_T1S_Infos.each { theWasteOutputFrom_T1S_Info ->
          flushWasteOutputFrom_T1S_Info xml, theWasteOutputFrom_T1S_Info
        }
      }
    }
    
        //println xml.getProperties().toString()
  }

  def save = {
    log.info "WasteOutputFrom_T1S_InfoController.add( ${params} )"
    def theWasteOutputFrom_T1S_Info
    try {
        theWasteOutputFrom_T1S_Info = wasteOutputFrom_T1S_InfoService.save(params)
	//-- temporary approach below for now, need more better approach??
	//aclUtilService.addPermission(theWasteOutputFrom_T1S_Info, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
	println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theWasteOutputFrom_T1S_Info not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushWasteOutputFrom_T1S_Info xml, theWasteOutputFrom_T1S_Info
      }
    }
  }

  def remove = {
    log.info "WasteOutputFrom_T1S_InfoController.remove( ${params} )"
    wasteOutputFrom_T1S_InfoService.remove(params)
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

  private def flushWasteOutputFrom_T1S_Info = { xml, wasteOutputFrom_T1S_Info ->
    xml.record(
        id: wasteOutputFrom_T1S_Info.id,
        purchasedProductInfoId: wasteOutputFrom_T1S_Info.purchasedProductInfoId,

        sourceDescription: wasteOutputFrom_T1S_Info.sourceDescription,
        massOfWasteFrom_T1S_ForPurchasedProduct: wasteOutputFrom_T1S_Info.massOfWasteFrom_T1S_ForPurchasedProduct,
        massOfWasteFrom_T1S_ForPurchasedProduct_Unit: wasteOutputFrom_T1S_Info.massOfWasteFrom_T1S_ForPurchasedProduct_Unit,
        EF_WasteActivity: wasteOutputFrom_T1S_Info.EF_WasteActivity,
        EF_WasteActivity_Unit: wasteOutputFrom_T1S_Info.EF_WasteActivity_Unit,
		
        //---General fields
        userNotesOnData:wasteOutputFrom_T1S_Info.userNotesOnData,            
        dataBeginDate:wasteOutputFrom_T1S_Info.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:wasteOutputFrom_T1S_Info.dataEndDate?.format("yyyy-MM-dd")
		
    )
  }
}