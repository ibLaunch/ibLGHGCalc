package org.ibLGHGCalc

class EquipmentCapacityRange_EPAService {

    static transactional = true
    //static expose = ["gwt:org.ibLGHGCalc.client"]

    EquipmentCapacityRange_EPA findEquipmentCapacityRange_EPA(Long id) {
      EquipmentCapacityRange_EPA.get(id)
    }

    def EquipmentCapacityRange_EPA[] findEquipmentCapacityRange_EPAs() {
      EquipmentCapacityRange_EPA.list()
    }

    def EquipmentCapacityRange_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEquipmentCapacityRange_EPA = EquipmentCapacityRange_EPA.get(parameters.id)
      if (!theEquipmentCapacityRange_EPA) {
        theEquipmentCapacityRange_EPA = new EquipmentCapacityRange_EPA()
      }
      theEquipmentCapacityRange_EPA.properties = parameters;

      theEquipmentCapacityRange_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EquipmentCapacityRange_EPA.get(parameters.id)?.delete()
    }
}
