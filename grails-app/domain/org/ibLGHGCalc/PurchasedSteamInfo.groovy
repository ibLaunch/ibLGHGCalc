package org.ibLGHGCalc

class PurchasedSteamInfo {

    static belongsTo = [organization:Organization]

    String sourceDescription
    String fuelType
    Double boilerEfficiencyPercent
    Double purchasedSteam
    String purchasedSteamUnit

    Double supplierCO2Multiplier
    Double supplierCH4Multiplier
    Double supplierN2OMultiplier

    String supplierCO2MultiplierUnit
    String supplierCH4MultiplierUnit
    String supplierN2OMultiplierUnit

    Date fuelUsedBeginDate
    Date fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'

    static hasMany = [emissionsDetailsList: EmissionsDetails]

    static constraints = {
        organization(nullable:false)

        sourceDescription(blank:false, maxsize:255)
        fuelType(blank:false)
        boilerEfficiencyPercent(nullable:false)
        purchasedSteam(nullable:false)
        purchasedSteamUnit(blank:false, default:"mmBtu")

        supplierCO2Multiplier(nullable:true)
        supplierCH4Multiplier(nullable:true)
        supplierN2OMultiplier(nullable:true)

        supplierCO2MultiplierUnit(blank:true, default:"lb/mmBtu")
        supplierCH4MultiplierUnit(blank:true, default:"lb/mmBtu")
        supplierN2OMultiplierUnit(blank:true, default:"lb/mmBtu")

        fuelUsedBeginDate(nullable:false)
        fuelUsedEndDate(nullable:false)
        dateCreated(nullable:true)
        lastUpdated(nullable:true)

        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true)

        emissionsDetailsList(nullable:true)
    }

    static mapping = {
        emissionsDetailsList cascade: "all-delete-orphan"
    }
}
