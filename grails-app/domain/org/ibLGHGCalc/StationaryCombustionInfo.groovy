package org.ibLGHGCalc

class StationaryCombustionInfo {

    //static belongsTo = Organization
    static belongsTo = [organization:Organization]
    //List emissionsByProgramTypes
   
    String fuelSourceDescription
    String fuelType
    Float fuelQuantity
    String fuelUnit

    Boolean isPublic = Boolean.TRUE

    String  fuelUsedBeginDate
    String  fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    static hasMany = [emissionsByProgramTypes:StationaryCombustionEmissions]

    static constraints = {
            //organization(nullable:false)
            fuelSourceDescription(blank:false, maxsize:255)
            fuelType(blank:false)
            fuelQuantity(blank:false)
            fuelUnit(blank:false)
            fuelUsedBeginDate(blank:false)
            fuelUsedEndDate(blank:false)
            dateCreated(nullable:true)     
            lastUpdated(nullable:true)
            emissionsByProgramTypes(nullable:true)
    }
    static mapping = {
        emissionsByProgramTypes cascade: "all-delete-orphan"
    }
}
