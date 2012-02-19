package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class MaterialUsedBy_T1S_InfoController {
    
  def materialUsedBy_T1S_InfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "MaterialUsedBy_T1S_InfoController.list( ${params} )"

    //--findMaterialUsedBy_T1S_Infos() below is not working, not sure why. Hence changed the name to getP..(), but after trying that findPu..() started working.
    def materialUsedBy_T1S_Infos = materialUsedBy_T1S_InfoService.findMaterialUsedBy_T1S_Infos(params);
        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        materialUsedBy_T1S_Infos.each { theMaterialUsedBy_T1S_Info ->
          flushMaterialUsedBy_T1S_Info xml, theMaterialUsedBy_T1S_Info
        }
      }
    }
    
        //println xml.getProperties().toString()
  }

  def save = {
    log.info "MaterialUsedBy_T1S_InfoController.add( ${params} )"
    def theMaterialUsedBy_T1S_Info
    try {
        theMaterialUsedBy_T1S_Info = materialUsedBy_T1S_InfoService.save(params)
	//-- temporary approach below for now, need more better approach??
	//aclUtilService.addPermission(theMaterialUsedBy_T1S_Info, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
	println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theMaterialUsedBy_T1S_Info not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushMaterialUsedBy_T1S_Info xml, theMaterialUsedBy_T1S_Info
      }
    }
  }

  def remove = {
    log.info "MaterialUsedBy_T1S_InfoController.remove( ${params} )"
    materialUsedBy_T1S_InfoService.remove(params)
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

  private def flushMaterialUsedBy_T1S_Info = { xml, materialUsedBy_T1S_Info ->
    xml.record(
        id: materialUsedBy_T1S_Info.id,
        purchasedProductInfoId: materialUsedBy_T1S_Info.purchasedProductInfoId,

        materialUsedBy_T1S_ForPurchasedProduct: materialUsedBy_T1S_Info.materialUsedBy_T1S_ForPurchasedProduct,        
        amountOfMaterialUsedBy_T1S_ForPurchasedProduct: materialUsedBy_T1S_Info.amountOfMaterialUsedBy_T1S_ForPurchasedProduct,        
        amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit: materialUsedBy_T1S_Info.amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit,        
        EF_Material: materialUsedBy_T1S_Info.EF_Material,        
        EF_Material_Unit: materialUsedBy_T1S_Info.EF_Material_Unit,        

        //---General fields
        userNotesOnData:materialUsedBy_T1S_Info.userNotesOnData,            
        dataBeginDate:materialUsedBy_T1S_Info.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:materialUsedBy_T1S_Info.dataEndDate?.format("yyyy-MM-dd")
		
    )
  }
}