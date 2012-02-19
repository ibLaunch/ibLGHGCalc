package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class MaterialTransportTo_T1S_InfoController {
      
  def materialTransportTo_T1S_InfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "MaterialTransportTo_T1S_InfoController.list( ${params} )"

    //--findMaterialTransportTo_T1S_Infos() below is not working, not sure why. Hence changed the name to getP..(), but after trying that findPu..() started working.
    def materialTransportTo_T1S_Infos = materialTransportTo_T1S_InfoService.findMaterialTransportTo_T1S_Infos(params);
        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        materialTransportTo_T1S_Infos.each { theMaterialTransportTo_T1S_Info ->
          flushMaterialTransportTo_T1S_Info xml, theMaterialTransportTo_T1S_Info
        }
      }
    }
    
        //println xml.getProperties().toString()
  }

  def save = {
    log.info "MaterialTransportTo_T1S_InfoController.add( ${params} )"
    def theMaterialTransportTo_T1S_Info
    try {
        theMaterialTransportTo_T1S_Info = materialTransportTo_T1S_InfoService.save(params)
	//-- temporary approach below for now, need more better approach??
	//aclUtilService.addPermission(theMaterialTransportTo_T1S_Info, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
	println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theMaterialTransportTo_T1S_Info not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushMaterialTransportTo_T1S_Info xml, theMaterialTransportTo_T1S_Info
      }
    }
  }

  def remove = {
    log.info "MaterialTransportTo_T1S_InfoController.remove( ${params} )"
    materialTransportTo_T1S_InfoService.remove(params)
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

  private def flushMaterialTransportTo_T1S_Info = { xml, materialTransportTo_T1S_Info ->
    xml.record(
        id: materialTransportTo_T1S_Info.id,
        purchasedProductInfoId: materialTransportTo_T1S_Info.purchasedProductInfoId,

        materialUsedBy_T1S_ForPurchasedProduct: materialTransportTo_T1S_Info.materialUsedBy_T1S_ForPurchasedProduct,        
        distanceOfTransportOfMaterialInputsTo_T1S: materialTransportTo_T1S_Info.distanceOfTransportOfMaterialInputsTo_T1S,        
        distanceOfTransportOfMaterialInputsTo_T1S_Unit: materialTransportTo_T1S_Info.distanceOfTransportOfMaterialInputsTo_T1S_Unit,        
        massOfMateriaInput: materialTransportTo_T1S_Info.massOfMateriaInput,        
        massOfMateriaInput_Unit: materialTransportTo_T1S_Info.massOfMateriaInput_Unit,        
        vehicleType: materialTransportTo_T1S_Info.vehicleType,        
        EF_VehicleType: materialTransportTo_T1S_Info.EF_VehicleType,        
        EF_VehicleType_Unit: materialTransportTo_T1S_Info.EF_VehicleType_Unit,        
		
        //---General fields
        userNotesOnData:materialTransportTo_T1S_Info.userNotesOnData,            
        dataBeginDate:materialTransportTo_T1S_Info.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:materialTransportTo_T1S_Info.dataEndDate?.format("yyyy-MM-dd")
		
    )
  }
}