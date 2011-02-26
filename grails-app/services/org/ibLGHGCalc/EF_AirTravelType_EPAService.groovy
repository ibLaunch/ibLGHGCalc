package org.ibLGHGCalc

class EF_AirTravelType_EPAService {

    static transactional = true

    EF_AirTravelType_EPA findEF_AirTravelType_EPA(Long id) {
      EF_AirTravelType_EPA.get(id)
    }

    def EF_AirTravelType_EPA[] findEF_AirTravelType_EPAs() {
      EF_AirTravelType_EPA.list()
    }

    def EF_AirTravelType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_AirTravelType_EPA = EF_AirTravelType_EPA.get(parameters.id)
      if (!theEF_AirTravelType_EPA) {
        theEF_AirTravelType_EPA = new EF_AirTravelType_EPA()
      }
      theEF_AirTravelType_EPA.properties = parameters;

      theEF_AirTravelType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_AirTravelType_EPA.get(parameters.id)?.delete()
    }
}
