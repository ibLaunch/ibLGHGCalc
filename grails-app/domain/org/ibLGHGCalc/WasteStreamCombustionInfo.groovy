package org.ibLGHGCalc

class WasteStreamCombustionInfo {

    static belongsTo = [organization:Organization]

    String fuelSourceDescription

    Double amountOfWasterStreamGasCombusted
    String amountOfWasterStreamGasCombustedUnit="scf"
    Double totalNumberOfMolesPerUnitVolument
    String totalNumberOfMolesPerUnitVolumentUnit="lbmole/ft3"

    Double carbonMonoxideMolarFractionPercent
    Double carbonDioxideMolarFractionPercent
    Double methaneMolarFractionPercent
    Double cetyleneMolarFractionPercent
    Double ethyleneMolarFractionPercent
    Double ethaneMolarFractionPercent
    Double propyleneMolarFractionPercent
    Double propaneMolarFractionPercent
    Double n_ButaneMolarFractionPercent
    Double benzeneMolarFractionPercent
    Double bexaneMolarFractionPercent
    Double tolueneMolarFractionPercent
    Double octaneMolarFractionPercent
    Double ethanolMolarFractionPercent
    Double acetoneMolarFractionPercent
    Double tetrahydrofuranMolarFractionPercent
    Double otherNon_CMolarFractionPercent

    Double oxidationFactorPercent

    Date  fuelUsedBeginDate
    Date  fuelUsedEndDate

    Date dateCreated
    Date lastUpdated

    static hasMany = [emissionsDetailsList:EmissionsDetails]

    static constraints = {
            organization(nullable:false)
            fuelSourceDescription(blank:false, maxsize:255)

            amountOfWasterStreamGasCombusted(nullable:false)
            amountOfWasterStreamGasCombustedUnit(blank:true)
            totalNumberOfMolesPerUnitVolument(nullable:false)
            totalNumberOfMolesPerUnitVolumentUnit(blank:true)
            
            carbonMonoxideMolarFractionPercent(nullable:false)
            carbonDioxideMolarFractionPercent(nullable:false)
            methaneMolarFractionPercent(nullable:false)
            cetyleneMolarFractionPercent(nullable:false)
            ethyleneMolarFractionPercent(nullable:false)
            ethaneMolarFractionPercent(nullable:false)
            propyleneMolarFractionPercent(nullable:false)
            propaneMolarFractionPercent(nullable:false)
            n_ButaneMolarFractionPercent(nullable:false)
            benzeneMolarFractionPercent(nullable:false)
            bexaneMolarFractionPercent(nullable:false)
            tolueneMolarFractionPercent(nullable:false)
            octaneMolarFractionPercent(nullable:false)
            ethanolMolarFractionPercent(nullable:false)
            acetoneMolarFractionPercent(nullable:false)
            tetrahydrofuranMolarFractionPercent(nullable:false)
            otherNon_CMolarFractionPercent(nullable:false)

            oxidationFactorPercent (nullable:false)

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
