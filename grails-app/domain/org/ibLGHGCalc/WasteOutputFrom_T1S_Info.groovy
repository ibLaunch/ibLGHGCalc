package org.ibLGHGCalc

class WasteOutputFrom_T1S_Info {

    static belongsTo = [purchasedProductInfo:PurchasedProductInfo]
    
    //-------------------------------
    //-Supplier Specific Level Method fields - waste outputs by tier 1 supplier relating to purchased goods
    //-------------------------------
    String sourceDescription	
    Double massOfWasteFrom_T1S_ForPurchasedProduct
    String massOfWasteFrom_T1S_ForPurchasedProduct_Unit //Kg

    Double EF_WasteActivity 
    String EF_WasteActivity_Unit  // Tonnes

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
        sourceDescription(nullable:true, maxsize:255)

        massOfWasteFrom_T1S_ForPurchasedProduct(nullable:true)  
        massOfWasteFrom_T1S_ForPurchasedProduct_Unit(nullable:true, maxsize:255) //Kg or $

        EF_WasteActivity(nullable:true)  
        EF_WasteActivity_Unit(nullable:true, maxsize:255) //Kg or $
		
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