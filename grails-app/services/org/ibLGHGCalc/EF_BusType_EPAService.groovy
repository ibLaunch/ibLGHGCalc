package org.ibLGHGCalc

class EF_BusType_EPAService {

    static transactional = true

    EF_BusType_EPA findEF_BusType_EPA(Long id) {
      EF_BusType_EPA.get(id)
    }

    def EF_BusType_EPA[] findEF_BusType_EPAs() {
      EF_BusType_EPA.list()
    }

    def EF_BusType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_BusType_EPA = EF_BusType_EPA.get(parameters.id)
      if (!theEF_BusType_EPA) {
        theEF_BusType_EPA = new EF_BusType_EPA()
      }
      theEF_BusType_EPA.properties = parameters;

      theEF_BusType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_BusType_EPA.get(parameters.id)?.delete()
    }
}
