package org.ibLGHGCalc

class EF_ProductTransportType_EPAService {

    static transactional = true

    EF_ProductTransportType_EPA findEF_ProductTransportType_EPA(Long id) {
      EF_ProductTransportType_EPA.get(id)
    }

    def EF_ProductTransportType_EPA[] findEF_ProductTransportType_EPAs() {
      EF_ProductTransportType_EPA.list()
    }

    def EF_ProductTransportType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_ProductTransportType_EPA = EF_ProductTransportType_EPA.get(parameters.id)
      if (!theEF_ProductTransportType_EPA) {
        theEF_ProductTransportType_EPA = new EF_ProductTransportType_EPA()
      }
      theEF_ProductTransportType_EPA.properties = parameters;

      theEF_ProductTransportType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_ProductTransportType_EPA.get(parameters.id)?.delete()
    }
}
