package org.ibLGHGCalc

class StationaryCombustionInfo {
    List emissionsByProgramTypes
   
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


}
