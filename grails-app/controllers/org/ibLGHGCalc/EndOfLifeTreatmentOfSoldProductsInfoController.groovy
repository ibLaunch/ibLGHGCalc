package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class EndOfLifeTreatmentOfSoldProductsInfoController {

  def endOfLifeTreatmentOfSoldProductsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "EndOfLifeTreatmentOfSoldProductsInfoController.list( ${params} )"

    def endOfLifeTreatmentOfSoldProductsInfos = endOfLifeTreatmentOfSoldProductsInfoService.findEndOfLifeTreatmentOfSoldProductsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        endOfLifeTreatmentOfSoldProductsInfos.each { theEndOfLifeTreatmentOfSoldProductsInfo ->
          flushEndOfLifeTreatmentOfSoldProductsInfo xml, theEndOfLifeTreatmentOfSoldProductsInfo
        }
      }
    }
  }

  def save = {
    log.info "EndOfLifeTreatmentOfSoldProductsInfoController.add( ${params} )"        
    def theEndOfLifeTreatmentOfSoldProductsInfo
        
    try {
        theEndOfLifeTreatmentOfSoldProductsInfo = endOfLifeTreatmentOfSoldProductsInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(theEndOfLifeTreatmentOfSoldProductsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theEndOfLifeTreatmentOfSoldProductsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEndOfLifeTreatmentOfSoldProductsInfo xml, theEndOfLifeTreatmentOfSoldProductsInfo
      }
    }
  }

  def remove = {
    log.info "EndOfLifeTreatmentOfSoldProductsInfoController.remove( ${params} )"
    endOfLifeTreatmentOfSoldProductsInfoService.remove(params)
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

  private def flushEndOfLifeTreatmentOfSoldProductsInfo = { xml, endOfLifeTreatmentOfSoldProductsInfo ->
    xml.record(
        id: endOfLifeTreatmentOfSoldProductsInfo.id,
        organizationId: endOfLifeTreatmentOfSoldProductsInfo.organizationId,
        
        sourceDescription: endOfLifeTreatmentOfSoldProductsInfo.sourceDescription,        
        soldProductName: endOfLifeTreatmentOfSoldProductsInfo.soldProductName,        
        
        massOfSoldProductsAfterConsumerUse: endOfLifeTreatmentOfSoldProductsInfo.massOfSoldProductsAfterConsumerUse,        
        massOfSoldProductsAfterConsumerUse_Unit: endOfLifeTreatmentOfSoldProductsInfo.massOfSoldProductsAfterConsumerUse_Unit,        
        
        percentOfWasteTreatedByWasteTreatmentMethod: endOfLifeTreatmentOfSoldProductsInfo.percentOfWasteTreatedByWasteTreatmentMethod,
        
        wasteType: endOfLifeTreatmentOfSoldProductsInfo.wasteType,
        wasteTreatmentType: endOfLifeTreatmentOfSoldProductsInfo.wasteTreatmentType,

        EF_WasteTreatmentMethod: endOfLifeTreatmentOfSoldProductsInfo.EF_WasteTreatmentMethod,
        EF_WasteTreatmentMethod_Unit: endOfLifeTreatmentOfSoldProductsInfo.EF_WasteTreatmentMethod_Unit,
                    
        //---General fields
        userNotesOnData:endOfLifeTreatmentOfSoldProductsInfo.userNotesOnData,            
        methodType:endOfLifeTreatmentOfSoldProductsInfo.methodType,
        dataBeginDate:endOfLifeTreatmentOfSoldProductsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:endOfLifeTreatmentOfSoldProductsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
