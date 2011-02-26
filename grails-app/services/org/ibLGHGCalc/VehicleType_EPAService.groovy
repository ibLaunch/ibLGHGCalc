package org.ibLGHGCalc

class VehicleType_EPAService {

    static transactional = true

    VehicleType_EPA findVehicleType_EPA(Long id) {
      VehicleType_EPA.get(id)
    }

    def VehicleType_EPA[] findVehicleType_EPAs() {
      VehicleType_EPA.list()
    }

    def VehicleType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theVehicleType_EPA = VehicleType_EPA.get(parameters.id)
      if (!theVehicleType_EPA) {
        theVehicleType_EPA = new VehicleType_EPA()
      }
      theVehicleType_EPA.properties = parameters;

      theVehicleType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      VehicleType_EPA.get(parameters.id)?.delete()
    }
}
