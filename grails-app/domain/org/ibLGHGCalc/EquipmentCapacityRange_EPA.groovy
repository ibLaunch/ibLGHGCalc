package org.ibLGHGCalc

class EquipmentCapacityRange_EPA {

    String typeOfEquipment
    String capacityRange
    String capacityRangeUnit='kg'
    Double kFactor
    Double xFactor
    Double yFactor
    Double zFactor

    static constraints = {
            typeOfEquipment(blank:false)
            capacityRange(blank:false)
            capacityRangeUnit(blank:true)
            kFactor(nullable:false)
            kFactor(nullable:false)
            kFactor(nullable:false)
            kFactor(nullable:false)
    }
}
