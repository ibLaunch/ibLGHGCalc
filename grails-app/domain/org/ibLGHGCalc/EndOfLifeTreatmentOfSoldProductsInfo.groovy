package org.ibLGHGCalc

class EndOfLifeTreatmentOfSoldProductsInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription    
    
    String soldProductName
    Double massOfSoldProductsAfterConsumerUse
    String massOfSoldProductsAfterConsumerUse_Unit  //(Kg)

    Double percentOfWasteTreatedByWasteTreatmentMethod //(%)

    String wasteType
    String wasteTreatmentType    
    
    Double EF_WasteTreatmentMethod
    String EF_WasteTreatmentMethod_Unit //(Kg CO2e/Kg)

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

    static hasMany = [emissionsDetailsList: EmissionsDetails]
    
    static constraints = {

        organization(nullable:false)
        sourceDescription(nullable:true, maxsize:255)        
        
        soldProductName(nullable:true, maxsize:255)
        massOfSoldProductsAfterConsumerUse(nullable:true)
        massOfSoldProductsAfterConsumerUse_Unit(nullable:true, maxsize:255)

        percentOfWasteTreatedByWasteTreatmentMethod(nullable:true)

        wasteType (nullable:true, maxsize:255)
        wasteTreatmentType(nullable:true, maxsize:255)
        
        EF_WasteTreatmentMethod(nullable:true)
        EF_WasteTreatmentMethod_Unit(nullable:true, maxsize:255)
        
        methodType(blank:false, maxsize:255)
        userNotesOnData(nullable:true, maxsize:255)
        //calculatedEmissions(nullable:true)
        //calculatedEmissions_Unit(nullable:true, maxsize:255)
        dataBeginDate(nullable:false)
        dataEndDate(nullable:false)		
        emissionsDetailsList(nullable:true)
        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true, maxsize:255)                                                                        
    }
}
