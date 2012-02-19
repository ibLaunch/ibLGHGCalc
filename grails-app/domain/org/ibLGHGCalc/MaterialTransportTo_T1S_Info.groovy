package org.ibLGHGCalc

class MaterialTransportTo_T1S_Info {
      
    static belongsTo = [purchasedProductInfo:PurchasedProductInfo]
    
    //-------------------------------
    //-Supplier Specific Level Method fields - Transport of material inputs to tier 1 supplier
    //-------------------------------
    String materialUsedBy_T1S_ForPurchasedProduct
    Double distanceOfTransportOfMaterialInputsTo_T1S
    String distanceOfTransportOfMaterialInputsTo_T1S_Unit //Km	

    Double massOfMateriaInput 
    String massOfMateriaInput_Unit  // Tonnes

    String vehicleType
    Double EF_VehicleType
    String EF_VehicleType_Unit

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
        distanceOfTransportOfMaterialInputsTo_T1S(nullable:true)  
        distanceOfTransportOfMaterialInputsTo_T1S_Unit(nullable:true, maxsize:255) //Kg or $

        massOfMateriaInput(nullable:true)  
        massOfMateriaInput_Unit(nullable:true, maxsize:255) //Kg or $
		
        vehicleType(nullable:true, maxsize:255)				
        EF_VehicleType(nullable:true)		
        EF_VehicleType_Unit(nullable:true, maxsize:255)		

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