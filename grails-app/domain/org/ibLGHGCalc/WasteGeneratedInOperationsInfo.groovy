package org.ibLGHGCalc

class WasteGeneratedInOperationsInfo {

    static belongsTo = [organization:Organization]
    
    //Waste Type Specific Method	
    String sourceDescription    
    String serviceProviderName
    String serviceProviderContact
    
    String wasteType
    String wasteTreatmentType    
    
    Double wasteProduced
    String wasteProduced_Unit //(tonnes)

    Double EF_WasteTypeAndWasteTreatment
    String EF_WasteTypeAndWasteTreatment_Unit // (Kg CO2e/tonne)

    //Average Data Method								
    //Double totalMassOfWaste
    //String totalMassOfWaste_Unit //(tonnes)

    Double percentOfWasteTreatedByWasteTreatmentMethod //(%)

    Double EF_WasteTreatmentMethod
    String EF_WasteTreatmentMethod_Unit //(Kg CO2e/tonne)
    
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

        serviceProviderName(nullable:true, maxsize:255)
        serviceProviderContact(nullable:true, maxsize:255)
        
        //Waste Type Specific Method								
        wasteType (nullable:true, maxsize:255)
        wasteTreatmentType(nullable:true, maxsize:255)
        wasteProduced(nullable:true)
        wasteProduced_Unit(nullable:true, maxsize:255)

        EF_WasteTypeAndWasteTreatment(nullable:true)
        EF_WasteTypeAndWasteTreatment_Unit(nullable:true, maxsize:255)

        //Average Data Method								

        //totalMassOfWaste(nullable:true)
        //totalMassOfWaste_Unit(nullable:true, maxsize:255)

        percentOfWasteTreatedByWasteTreatmentMethod(nullable:true)

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
