package org.ibLGHGCalc

class PurchasedProductInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription
    
    String supplierName
    String supplierContact
    
    String purchasedProductType //goods or service or capital good
    String purchasedProductName
    //-------------------------------
    //--Product Level Method fields
    //-------------------------------
    
    Double quantityOfPurchasedProduct
    String quantityOfPurchasedProduct_Unit //'Kg'

    Double supplierSpecific_EF_ForPurchasedProduct
    String supplierSpecific_EF_ForPurchasedProduct_Unit //'Kg CO2e/Kg'	
    
    //-------------------------------
    //-Supplier Specific Level Method fields - Purchased goods or services
    //-------------------------------
    //String tier1SupplierName
    Double scope1EmissionsOf_T1S_ForPurchasedProduct
    String scope1EmissionsOf_T1S_ForPurchasedProduct_Unit //Kg CO2e
    Double scope2EmissionsOf_T1S_ForPurchasedProduct 
    String scope2EmissionsOf_T1S_ForPurchasedProduct_Unit //Kg CO2e
    
    //-------------------------------
    //-Supplier Specific Level Method fields - Material inputs
    //-------------------------------
    /*
    String materialUsedBy_T1S_ForPurchasedProduct		
    Double amountOfMaterialUsedBy_T1S_ForPurchasedProduct  //mass or value
    String amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit //Kg or $
    
    String EF_Material		
    String EF_Material_Unit		
    */   
    //-------------------------------
    //-Supplier Specific Level Method fields - Transport of material inputs to tier 1 supplier
    //-------------------------------
/*    
    Double distanceOfTransportOfMaterialInputsTo_T1S
    String distanceOfTransportOfMaterialInputsTo_T1S_Unit //Km	

    Double massOfMateriaInput 
    String massOfMateriaInput_Unit  // Tonnes

    String vehicleType
    String EF_VehicleType
    String EF_VehicleType_Unit
*/
    //-------------------------------
    //-Supplier Specific Level Method fields - waste outputs by tier 1 supplier relating to purchased goods
    //-------------------------------
/*    
    Double massOfWasteFrom_T1S_ForPurchasedProduct
    String massOfWasteFrom_T1S_ForPurchasedProduct_Unit	

    Double EF_WasteActivity
    String EF_WasteActivity_Unit
*/    
    //-------------------------------    
    //-Material or Spend-Based Calculation Method fields -- Mass of purchased good or service
    //-------------------------------
/*    
    Double massOfPurchasedProduct
    String massOfPurchasedProduct_Unit  //kg
    
    Double EF_PurchasedProductPerUnitOfMass
    String EF_PurchasedProductPerUnitOfMass_Unit //(Kg CO2e/Kg)
    
    //-------------------------------
    //-Material or Spend-Based Calculation Method fields -- Unit of purchased good or service    
    //-------------------------------
    Double unitOfPurchasedProduct
    String unitOfPurchasedProduct_Unit //e.g. piece
    
    Double EF_PurchasedProductPerReferenceUnit
    String EF_PurchasedProductPerReferenceUnit_Unit //(e.g. Kg CO2e/piece)  
    
    //
    //-------------------------------
    //-Material or Spend-Based Calculation Method fields -- Value of purchased good or service
    //-------------------------------
    Double valueOfPurchasedProduct
    String valueOfPurchasedProduct_Unit //$
    
    Double EF_PurchasedProductPerUnitOfReferenceValue
    String EF_PurchasedProductPerUnitOfReferenceValue_Unit //(e.g. Kg CO2e/$)
*/

    Double secondary_EF
    String secondary_EF_Unit //(e.g. Kg CO2e/piece)
    
    //Other fields    
    String methodType   
    String userNotesOnData  //User can keep the notes here, in terms of where the data was coming from    
    //Double calculatedEmissions  //calculated emissions based on the use data
    //String calculatedEmissions_Unit
    
    Date dataBeginDate
    Date dataEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'
    
    static hasMany = [emissionsDetailsList: EmissionsDetails,
                        materialUsedBy_T1S_InfoList: MaterialUsedBy_T1S_Info,
                        materialTransportTo_T1S_InfoList: MaterialTransportTo_T1S_Info,                        
                        wasteOutputFrom_T1S_InfoList: WasteOutputFrom_T1S_Info
                    ]
    
    static constraints = {	

        organization(nullable:false)
        sourceDescription(nullable:true, maxsize:255)
        supplierName(nullable:true, maxsize:255)
        supplierContact(nullable:true, maxsize:255)
            
        purchasedProductType (blank:false, maxsize:255) //'Kg'//goods or service
        purchasedProductName(blank:false, maxsize:255) //'Kg'
        quantityOfPurchasedProduct (nullable:true)
        quantityOfPurchasedProduct_Unit(nullable:true, maxsize:255) //'Kg'

        supplierSpecific_EF_ForPurchasedProduct(nullable:true)
        supplierSpecific_EF_ForPurchasedProduct_Unit(nullable:true, maxsize:255) //'Kg CO2e/Kg'	

        //-Supplier Specific Level Method fields
        //tier1SupplierName(nullable:true, maxsize:255)
        scope1EmissionsOf_T1S_ForPurchasedProduct(nullable:true)
        scope1EmissionsOf_T1S_ForPurchasedProduct_Unit(nullable:true, maxsize:255) //Kg CO2e
        scope2EmissionsOf_T1S_ForPurchasedProduct(nullable:true) 
        scope2EmissionsOf_T1S_ForPurchasedProduct_Unit(nullable:true, maxsize:255) //Kg CO2e
/*
        materialUsedBy_T1S_ForPurchasedProduct(nullable:true, maxsize:255)		
        amountOfMaterialUsedBy_T1S_ForPurchasedProduct(nullable:true)  
        amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit(nullable:true, maxsize:255) //Kg or $

        EF_Material(nullable:true)		
        EF_Material_Unit(nullable:true, maxsize:255)		

        distanceOfTransportOfMaterialInputsTo_T1S(nullable:true) 
        distanceOfTransportOfMaterialInputsTo_T1S_Unit(nullable:true, maxsize:255) //Km	

        massOfMateriaInput(nullable:true) 
        massOfMateriaInput_Unit(nullable:true, maxsize:255)  // Tonnes

        vehicleType(nullable:true, maxsize:255)
        EF_VehicleType(nullable:true, maxsize:255)
        EF_VehicleType_Unit(nullable:true, maxsize:255)

        massOfWasteFrom_T1S_ForPurchasedProduct(nullable:true)
        massOfWasteFrom_T1S_ForPurchasedProduct_Unit(nullable:true, maxsize:255)

        EF_WasteActivity(nullable:true)
        EF_WasteActivity_Unit(nullable:true, maxsize:255)	

        //-Material or Spend-Based Calculation Method fields - Mass of purchased good or service
        massOfPurchasedProduct(nullable:true)
        massOfPurchasedProduct_Unit(nullable:true, maxsize:255) 

        EF_PurchasedProductPerUnitOfMass(nullable:true)
        EF_PurchasedProductPerUnitOfMass_Unit (nullable:true, maxsize:255)

        //-Material or Spend-Based Calculation Method fields -- Unit of purchased good or service    
        unitOfPurchasedProduct(nullable:true)
        unitOfPurchasedProduct_Unit(nullable:true, maxsize:255)

        EF_PurchasedProductPerReferenceUnit(nullable:true)
        EF_PurchasedProductPerReferenceUnit_Unit(nullable:true, maxsize:255)

        //-Material or Spend-Based Calculation Method fields -- Value of purchased good or service
        valueOfPurchasedProduct(nullable:true)
        valueOfPurchasedProduct_Unit(nullable:true, maxsize:255)

        EF_PurchasedProductPerUnitOfReferenceValue(nullable:true)
        EF_PurchasedProductPerUnitOfReferenceValue_Unit(nullable:true, maxsize:255)
*/		

        secondary_EF(nullable:true)
        secondary_EF_Unit(nullable:true, maxsize:255)
        
        methodType(blank:false, maxsize:255)
        userNotesOnData(nullable:true, maxsize:255)
        //calculatedEmissions(nullable:true)
        //calculatedEmissions_Unit(nullable:true, maxsize:255)
        dataBeginDate(nullable:false)
        dataEndDate(nullable:false)		
        emissionsDetailsList(nullable:true)
        materialUsedBy_T1S_InfoList(nullable:true)
        materialTransportTo_T1S_InfoList(nullable:true)
        wasteOutputFrom_T1S_InfoList(nullable:true)
        
        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true, maxsize:255)

    }

    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
        materialUsedBy_T1S_InfoList cascade: "all-delete-orphan"        
        materialTransportTo_T1S_InfoList cascade: "all-delete-orphan"
        wasteOutputFrom_T1S_InfoList cascade: "all-delete-orphan"
    }        
        
}