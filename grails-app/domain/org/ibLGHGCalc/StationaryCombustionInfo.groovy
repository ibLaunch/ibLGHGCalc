package org.ibLGHGCalc

class StationaryCombustionInfo {

    static belongsTo = [organization:Organization]
   
    String fuelSourceDescription
    String fuelType
    Float fuelQuantity
    String fuelUnit

    Boolean isPublic = Boolean.TRUE

    Date  fuelUsedBeginDate
    Date  fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    static hasMany = [emissionsDetailsList:EmissionsDetails]

    static constraints = {
            organization(nullable:false)
            fuelSourceDescription(blank:false, maxsize:255)
            fuelType(blank:false)
            fuelQuantity(nullable:false)
            fuelUnit(blank:false)
            fuelUsedBeginDate(nullable:false)
            fuelUsedEndDate(nullable:false)
            dateCreated(nullable:true)     
            lastUpdated(nullable:true)
            emissionsDetailsList(nullable:true)
    }
    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
    }
}
