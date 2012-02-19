package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class ProcessingOfSoldProductsInfoController {

  def processingOfSoldProductsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "ProcessingOfSoldProductsInfoController.list( ${params} )"

    def processingOfSoldProductsInfos = processingOfSoldProductsInfoService.findProcessingOfSoldProductsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        processingOfSoldProductsInfos.each { theProcessingOfSoldProductsInfo ->
          flushProcessingOfSoldProductsInfo xml, theProcessingOfSoldProductsInfo
        }
      }
    }
  }

  def save = {
    log.info "ProcessingOfSoldProductsInfoController.add( ${params} )"        
    def theProcessingOfSoldProductsInfo
        
    try {
        theProcessingOfSoldProductsInfo = processingOfSoldProductsInfoService.save(params)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theProcessingOfSoldProductsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushProcessingOfSoldProductsInfo xml, theProcessingOfSoldProductsInfo
      }
    }
  }

  def remove = {
    log.info "ProcessingOfSoldProductsInfoController.remove( ${params} )"
    processingOfSoldProductsInfoService.remove(params)
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

  private def flushProcessingOfSoldProductsInfo = { xml, processingOfSoldProductsInfo ->
    xml.record(
        id: processingOfSoldProductsInfo.id,
        organizationId: processingOfSoldProductsInfo.organizationId,
        
        sourceDescription: processingOfSoldProductsInfo.sourceDescription,        
        soldProductName: processingOfSoldProductsInfo.soldProductName,
        productSoldTo: processingOfSoldProductsInfo.productSoldTo,
        productSoldToContact: processingOfSoldProductsInfo.productSoldToContact,        

        fuelType: processingOfSoldProductsInfo.fuelType,
        fuelConsumed: processingOfSoldProductsInfo.fuelConsumed,        
        fuelConsumed_Unit: processingOfSoldProductsInfo.fuelConsumed_Unit,
        EF_Fuel: processingOfSoldProductsInfo.EF_Fuel,
        EF_Fuel_Unit: processingOfSoldProductsInfo.EF_Fuel_Unit,
        
        electricityConsumed: processingOfSoldProductsInfo.electricityConsumed,
        electricityConsumed_Unit: processingOfSoldProductsInfo.electricityConsumed_Unit,
        EF_Electricity: processingOfSoldProductsInfo.EF_Electricity,
        EF_Electricity_Unit: processingOfSoldProductsInfo.EF_Electricity_Unit,

        refrigerantType: processingOfSoldProductsInfo.refrigerantType,            
        refrigerantLeakage: processingOfSoldProductsInfo.refrigerantLeakage,
        refrigerantLeakage_Unit: processingOfSoldProductsInfo.refrigerantLeakage_Unit,            
        EF_Refrigerant: processingOfSoldProductsInfo.EF_Refrigerant,
        EF_Refrigerant_Unit: processingOfSoldProductsInfo.EF_Refrigerant_Unit,

        massOfWasteOutput: processingOfSoldProductsInfo.massOfWasteOutput,            
        massOfWasteOutput_Unit: processingOfSoldProductsInfo.massOfWasteOutput_Unit,
        EF_WasteActivity: processingOfSoldProductsInfo.EF_WasteActivity,            
        EF_WasteActivity_Unit: processingOfSoldProductsInfo.EF_WasteActivity_Unit,
        
        //Average Data Method -  processingOfSoldProducts emission - 1 & 2
        massOfSoldIntermediateProduct: processingOfSoldProductsInfo.massOfSoldIntermediateProduct,
        massOfSoldIntermediateProduct_Unit: processingOfSoldProductsInfo.massOfSoldIntermediateProduct_Unit,
        EF_ProcessingOfSoldProducts: processingOfSoldProductsInfo.EF_ProcessingOfSoldProducts,
        EF_ProcessingOfSoldProducts_Unit: processingOfSoldProductsInfo.EF_ProcessingOfSoldProducts_Unit,
            
        //---General fields
        userNotesOnData:processingOfSoldProductsInfo.userNotesOnData,            
        methodType:processingOfSoldProductsInfo.methodType,
        dataBeginDate:processingOfSoldProductsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:processingOfSoldProductsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}


