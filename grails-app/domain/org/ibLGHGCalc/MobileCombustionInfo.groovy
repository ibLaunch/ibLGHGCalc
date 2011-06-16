package org.ibLGHGCalc

class MobileCombustionInfo {

    static belongsTo = [organization:Organization]

    String fuelSourceDescription
    String vehicleType
    String vehicleYear ='' //default blank value
    String fuelType
    Float fuelQuantity
    String fuelUnit
    Float milesTravelled
    Float bioFuelPercent
    Float ethanolPercent

    Date  fuelUsedBeginDate
    Date  fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'

    static hasMany = [emissionsDetailsList: EmissionsDetails]

    static constraints = {
            organization(nullable:false)
            fuelSourceDescription(blank:false, maxsize:255)
            vehicleType(blank:false)
            vehicleYear(blank:true)
            fuelQuantity(nullable:false)
            fuelUnit(blank:false)
            bioFuelPercent(nullable:false)
            ethanolPercent(nullable:false)
            fuelUsedBeginDate(nullable:false)
            fuelUsedEndDate(nullable:false)
            dateCreated(nullable:true)
            lastUpdated(nullable:true)
            emissionsDetailsList(nullable:true)
            lastUpdatedByUserReference(nullable:true)
            dataOrigin(nullable:true)
    }
    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
    }
}
