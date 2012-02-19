package org.ibLGHGCalc

class MaterialUsedBy_T1S_Info {

    static belongsTo = [purchasedProductInfo:PurchasedProductInfo]
    
    //-------------------------------
    //-Supplier Specific Level Method fields - Material inputs
    //-------------------------------
    
    String materialUsedBy_T1S_ForPurchasedProduct		
    Double amountOfMaterialUsedBy_T1S_ForPurchasedProduct  //mass or value
    String amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit //Kg or $
    
    String EF_Material		
    String EF_Material_Unit		

    String userNotesOnData  //User can keep the notes here, in terms of where the data was coming from    
	
    Date dataBeginDate
    Date dataEndDate
    
    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'
    
    static hasMany = [emissionsDetailsList: EmissionsDetails]

    static constraints = {	

        purchasedProductInfo(nullable:false)

        materialUsedBy_T1S_ForPurchasedProduct(nullable:true, maxsize:255)		
        amountOfMaterialUsedBy_T1S_ForPurchasedProduct(nullable:true)  
        amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit(nullable:true, maxsize:255) //Kg or $

        EF_Material(nullable:true)		
        EF_Material_Unit(nullable:true, maxsize:255)		

        userNotesOnData(nullable:true, maxsize:255)
        dataBeginDate(nullable:false)
        dataEndDate(nullable:false)		
        emissionsDetailsList(nullable:true)
        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true, maxsize:255)
    }

    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
    }        
        
}