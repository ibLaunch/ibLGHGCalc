package org.ibLGHGCalc

class PurchasedElectricityInfo {

    static belongsTo = [organization:Organization]

    String sourceDescription
    String eGRIDSubregion
    Float purchasedElectricity
    String purchasedElectricityUnit

    Date fuelUsedBeginDate
    Date fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    static hasMany = [emissionsDetailsList: EmissionsDetails]

    static constraints = {
        organization(nullable:false)
        sourceDescription(blank:false, maxsize:255)
        eGRIDSubregion(blank:false)
        purchasedElectricity(nullable:false)
        purchasedElectricityUnit(blank:false)

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
