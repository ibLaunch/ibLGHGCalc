package org.ibLGHGCalc

class EF_VehicleType_EPAService {

    static transactional = true

    EF_VehicleType_EPA findEF_VehicleType_EPA(Long id) {
      EF_VehicleType_EPA.get(id)
    }

    def EF_VehicleType_EPA[] findEF_VehicleType_EPAs() {
      EF_VehicleType_EPA.list()
    }

    def EF_VehicleType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_VehicleType_EPA = EF_VehicleType_EPA.get(parameters.id)
      if (!theEF_VehicleType_EPA) {
        theEF_VehicleType_EPA = new EF_VehicleType_EPA()
      }
      theEF_VehicleType_EPA.properties = parameters;

      theEF_VehicleType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_VehicleType_EPA.get(parameters.id)?.delete()
    }
}
