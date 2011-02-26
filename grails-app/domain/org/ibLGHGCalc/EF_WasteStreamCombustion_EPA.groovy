package org.ibLGHGCalc

class EF_WasteStreamCombustion_EPA {

    String wasteStreamComponent
    String wasteStreamComponentChemicalFormula
    Double molecularWeight
    Double percentCarbon

    //String molecularWeightUnit="?"

    static constraints = {
            wasteStreamComponent(blank:false)
            wasteStreamComponentChemicalFormula(blank:false)
            molecularWeight(nullable:false)
            percentCarbon(nullable:false)
    }
}
