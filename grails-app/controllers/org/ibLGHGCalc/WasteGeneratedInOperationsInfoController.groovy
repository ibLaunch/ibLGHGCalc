package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class WasteGeneratedInOperationsInfoController {

  def wasteGeneratedInOperationsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "WasteGeneratedInOperationsInfoController.list( ${params} )"
    //println "I am in Product Controller------------"

    def wasteGeneratedInOperationsInfos = wasteGeneratedInOperationsInfoService.findWasteGeneratedInOperationsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        wasteGeneratedInOperationsInfos.each { theWasteGeneratedInOperationsInfo ->
          flushWasteGeneratedInOperationsInfo xml, theWasteGeneratedInOperationsInfo
        }
      }
    }
  }

  def save = {
    log.info "WasteGeneratedInOperationsInfoController.add( ${params} )"        
    def theWasteGeneratedInOperationsInfo
        
    try {
        theWasteGeneratedInOperationsInfo = wasteGeneratedInOperationsInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theWasteGeneratedInOperationsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theWasteGeneratedInOperationsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushWasteGeneratedInOperationsInfo xml, theWasteGeneratedInOperationsInfo
      }
    }
  }

  def remove = {
    log.info "WasteGeneratedInOperationsInfoController.remove( ${params} )"
    wasteGeneratedInOperationsInfoService.remove(params)
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

  private def flushWasteGeneratedInOperationsInfo = { xml, wasteGeneratedInOperationsInfo ->
    xml.record(
        id: wasteGeneratedInOperationsInfo.id,
        organizationId: wasteGeneratedInOperationsInfo.organizationId,
        
        sourceDescription: wasteGeneratedInOperationsInfo.sourceDescription,        
        serviceProviderName: wasteGeneratedInOperationsInfo.serviceProviderName,        
        serviceProviderContact: wasteGeneratedInOperationsInfo.serviceProviderContact,        
        
        //Waste Type Specific Method
        wasteType: wasteGeneratedInOperationsInfo.wasteType,
        wasteTreatmentType: wasteGeneratedInOperationsInfo.wasteTreatmentType,
        wasteProduced: wasteGeneratedInOperationsInfo.wasteProduced,        
        wasteProduced_Unit: wasteGeneratedInOperationsInfo.wasteProduced_Unit,

        EF_WasteTypeAndWasteTreatment: wasteGeneratedInOperationsInfo.EF_WasteTypeAndWasteTreatment,
        EF_WasteTypeAndWasteTreatment_Unit: wasteGeneratedInOperationsInfo.EF_WasteTypeAndWasteTreatment_Unit,        
        
        //Average Data Method
        percentOfWasteTreatedByWasteTreatmentMethod: wasteGeneratedInOperationsInfo.percentOfWasteTreatedByWasteTreatmentMethod,
        EF_WasteTreatmentMethod: wasteGeneratedInOperationsInfo.EF_WasteTreatmentMethod,
        EF_WasteTreatmentMethod_Unit: wasteGeneratedInOperationsInfo.EF_WasteTreatmentMethod_Unit,
                    
        //---General fields
        userNotesOnData:wasteGeneratedInOperationsInfo.userNotesOnData,            
        methodType:wasteGeneratedInOperationsInfo.methodType,
        dataBeginDate:wasteGeneratedInOperationsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:wasteGeneratedInOperationsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
