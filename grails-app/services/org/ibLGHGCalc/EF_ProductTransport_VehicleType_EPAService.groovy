package org.ibLGHGCalc

class EF_ProductTransport_VehicleType_EPAService {

    static transactional = true

    EF_ProductTransport_VehicleType_EPA findEF_ProductTransport_VehicleType_EPA(Long id) {
      EF_ProductTransport_VehicleType_EPA.get(id)
    }

    def EF_ProductTransport_VehicleType_EPA[] findEF_ProductTransport_VehicleType_EPAs() {
      EF_ProductTransport_VehicleType_EPA.list()
    }

    def EF_ProductTransport_VehicleType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_ProductTransport_VehicleType_EPA = EF_ProductTransport_VehicleType_EPA.get(parameters.id)
      if (!theEF_ProductTransport_VehicleType_EPA) {
        theEF_ProductTransport_VehicleType_EPA = new EF_ProductTransport_VehicleType_EPA()
      }
      theEF_ProductTransport_VehicleType_EPA.properties = parameters;

      theEF_ProductTransport_VehicleType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_ProductTransport_VehicleType_EPA.get(parameters.id)?.delete()
    }
}
