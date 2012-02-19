package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class PurchasedProductInfoController {

  def purchasedProductInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "PurchasedProductInfoController.list( ${params} )"
    //println "I am in Product Controller------------"

    def purchasedProductInfos = purchasedProductInfoService.findPurchasedProductInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        purchasedProductInfos.each { thePurchasedProductInfo ->
          flushPurchasedProductInfo xml, thePurchasedProductInfo
        }
      }
    }
  }

  def save = {
    log.info "PurchasedProductInfoController.add( ${params} )"        
    def thePurchasedProductInfo
        
    try {
/*
//----------------------------------------------------          
//----------------------------------------------------        
        println "params is: " +params           
        PurchasedProductInfo thePurchasedProductInfo2 = PurchasedProductInfo.get(params.id)       
        
        def stringOrArray = params.materialUsedBy_T1S_Info.getClass().toString()            
        //Check for null condition as well
        if (stringOrArray.contains("[L")) {
           println "its multiple records"
           def materialMapList = []
           materialMapList =  params.materialUsedBy_T1S_Info
           materialMapList.each(){
               def materialMap = [:]
                //def tempStr =  params.materialUsedBy_T1S_Info
               def tempStr =  it
               int tempStrSize = tempStr.size()-2            
               def tempStr2 = tempStr[1..tempStrSize]                        
               tempStr2.split(",").each(){ matMapItem ->
                   def nameAndValue = matMapItem.split(":")                
                   def name = nameAndValue[0]
                   int nameSize = nameAndValue[0].size()-2               
                   def name2= name[1..nameSize]
                   materialMap[name2] = nameAndValue[1]  
                }
            materialMap["dataBeginDate"]= new Date() //"1995-01-01T18:00:00"
            materialMap["dataEndDate"]= new Date() //"1995-01-01T18:00:00"
            println "---materialMap---in LIST:---"+materialMap            
            MaterialUsedBy_T1S_Info theMaterialUsedBy_T1S_Info = new MaterialUsedBy_T1S_Info()
            theMaterialUsedBy_T1S_Info = materialMap
            thePurchasedProductInfo2.addToMaterialUsedBy_T1S_InfoList(theMaterialUsedBy_T1S_Info);            
           }
        } 
        else             
        {   
            def materialMap = [:]
            def tempStr =  params.materialUsedBy_T1S_Info
            int tempStrSize = tempStr.size()-2            
            def tempStr2 = tempStr[1..tempStrSize]                        
            tempStr2.split(",").each(){ matMapItem ->
                def nameAndValue = matMapItem.split(":")                
                def name = nameAndValue[0]
                int nameSize = nameAndValue[0].size()-2               
                def name2= name[1..nameSize]
                materialMap[name2] = nameAndValue[1]  
            }
            materialMap["dataBeginDate"]= new Date() //"1995-01-01T18:00:00"
            materialMap["dataEndDate"]= new Date() //"1995-01-01T18:00:00"
            println "---materialMap---"+materialMap            
            MaterialUsedBy_T1S_Info theMaterialUsedBy_T1S_Info = new MaterialUsedBy_T1S_Info()            
            theMaterialUsedBy_T1S_Info = materialMap
            thePurchasedProductInfo2.addToMaterialUsedBy_T1S_InfoList(theMaterialUsedBy_T1S_Info);
            //aclUtilService.addPermission(theMaterialUsedBy_T1S_Info, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)        
        }                 
        thePurchasedProductInfo2.save(flush:true)      
        aclUtilService.addPermission(thePurchasedProductInfo2, springSecurityService.authentication.name, BasePermission.ADMINISTRATION) 
        
        thePurchasedProductInfo2.materialUsedBy_T1S_InfoList.each(){
            aclUtilService.addPermission(it, springSecurityService.authentication.name, BasePermission.ADMINISTRATION) 
        }
        //aclUtilService.addPermission(theMaterialUsedBy_T1S_Info, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)        
//----------------------------------------------------          
//----------------------------------------------------
*/

        thePurchasedProductInfo = purchasedProductInfoService.save(params)
        //-- temporary approach below for now, need more better approach??
        //aclUtilService.addPermission(thePurchasedProductInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "thePurchasedProductInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushPurchasedProductInfo xml, thePurchasedProductInfo
      }
    }
  }

  def remove = {
    log.info "PurchasedProductInfoController.remove( ${params} )"
    purchasedProductInfoService.remove(params)
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

  private def flushPurchasedProductInfo = { xml, purchasedProductInfo ->
    xml.record(
        id: purchasedProductInfo.id,
        organizationId: purchasedProductInfo.organizationId,

        sourceDescription: purchasedProductInfo.sourceDescription,
        supplierName: purchasedProductInfo.supplierName,
        supplierContact: purchasedProductInfo.supplierContact,
        purchasedProductType: purchasedProductInfo.purchasedProductType,
        purchasedProductName: purchasedProductInfo.purchasedProductName,

        quantityOfPurchasedProduct: purchasedProductInfo.quantityOfPurchasedProduct,
        quantityOfPurchasedProduct_Unit: purchasedProductInfo.quantityOfPurchasedProduct_Unit,
        
        supplierSpecific_EF_ForPurchasedProduct: purchasedProductInfo.supplierSpecific_EF_ForPurchasedProduct,
        supplierSpecific_EF_ForPurchasedProduct_Unit: purchasedProductInfo.supplierSpecific_EF_ForPurchasedProduct_Unit,

        //-Supplier Specific Level Method fields - Purchased goods or services
        //tier1SupplierName: purchasedProductInfo.tier1SupplierName,        
        scope1EmissionsOf_T1S_ForPurchasedProduct: purchasedProductInfo.scope1EmissionsOf_T1S_ForPurchasedProduct,        
        scope1EmissionsOf_T1S_ForPurchasedProduct_Unit: purchasedProductInfo.scope1EmissionsOf_T1S_ForPurchasedProduct_Unit,        
        scope2EmissionsOf_T1S_ForPurchasedProduct: purchasedProductInfo.scope2EmissionsOf_T1S_ForPurchasedProduct,        
        scope2EmissionsOf_T1S_ForPurchasedProduct_Unit: purchasedProductInfo.scope2EmissionsOf_T1S_ForPurchasedProduct_Unit,        
   /*     
        materialUsedBy_T1S_Info(
            materialUsedBy_T1S_ForPurchasedProduct: purchasedProductInfo.materialUsedBy_T1S_ForPurchasedProduct,        
            amountOfMaterialUsedBy_T1S_ForPurchasedProduct: purchasedProductInfo.amountOfMaterialUsedBy_T1S_ForPurchasedProduct,        
            amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit: purchasedProductInfo.amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit,        
            EF_Material: purchasedProductInfo.EF_Material,        
            EF_Material_Unit: purchasedProductInfo.EF_Material_Unit        
        ),
        
        distanceOfTransportOfMaterialInputsTo_T1S: purchasedProductInfo.distanceOfTransportOfMaterialInputsTo_T1S,        
        distanceOfTransportOfMaterialInputsTo_T1S_Unit: purchasedProductInfo.distanceOfTransportOfMaterialInputsTo_T1S_Unit,        
        massOfMateriaInput: purchasedProductInfo.massOfMateriaInput,        
        massOfMateriaInput_Unit: purchasedProductInfo.massOfMateriaInput_Unit,                
        vehicleType: purchasedProductInfo.vehicleType,        
        EF_VehicleType: purchasedProductInfo.EF_VehicleType,        
        EF_VehicleType_Unit: purchasedProductInfo.EF_VehicleType_Unit,
        
        massOfWasteFrom_T1S_ForPurchasedProduct: purchasedProductInfo.massOfWasteFrom_T1S_ForPurchasedProduct,        
        massOfWasteFrom_T1S_ForPurchasedProduct_Unit: purchasedProductInfo.massOfWasteFrom_T1S_ForPurchasedProduct_Unit,        
        EF_WasteActivity: purchasedProductInfo.EF_WasteActivity,        
        EF_WasteActivity_Unit: purchasedProductInfo.EF_WasteActivity_Unit,        
        
        //Material or Spend-Based Calculation Method fields -- Mass of purchased good or service
        massOfPurchasedProduct: purchasedProductInfo.massOfPurchasedProduct,        
        massOfPurchasedProduct_Unit: purchasedProductInfo.massOfPurchasedProduct_Unit,        
        EF_PurchasedProductPerUnitOfMass: purchasedProductInfo.EF_PurchasedProductPerUnitOfMass,        
        EF_PurchasedProductPerUnitOfMass_Unit: purchasedProductInfo.EF_PurchasedProductPerUnitOfMass_Unit,                


        unitOfPurchasedProduct: purchasedProductInfo.unitOfPurchasedProduct,        
        unitOfPurchasedProduct_Unit: purchasedProductInfo.unitOfPurchasedProduct_Unit,                
        EF_PurchasedProductPerReferenceUnit: purchasedProductInfo.EF_PurchasedProductPerReferenceUnit,        
        EF_PurchasedProductPerReferenceUnit_Unit: purchasedProductInfo.EF_PurchasedProductPerReferenceUnit_Unit,                

        valueOfPurchasedProduct: purchasedProductInfo.valueOfPurchasedProduct,        
        valueOfPurchasedProduct_Unit: purchasedProductInfo.valueOfPurchasedProduct_Unit,                
        EF_PurchasedProductPerUnitOfReferenceValue: purchasedProductInfo.EF_PurchasedProductPerUnitOfReferenceValue,        
        EF_PurchasedProductPerUnitOfReferenceValue_Unit: purchasedProductInfo.EF_PurchasedProductPerUnitOfReferenceValue_Unit,                
 */
        secondary_EF:purchasedProductInfo.secondary_EF,            
        secondary_EF_Unit:purchasedProductInfo.secondary_EF_Unit,
            
        //---General fields
        userNotesOnData:purchasedProductInfo.userNotesOnData,            
        methodType:purchasedProductInfo.methodType,
        dataBeginDate:purchasedProductInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:purchasedProductInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
