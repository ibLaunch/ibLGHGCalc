package org.ibLGHGCalc

class EF_RailType_EPAService {

    static transactional = true

    EF_RailType_EPA findEF_RailType_EPA(Long id) {
      EF_RailType_EPA.get(id)
    }

    def EF_RailType_EPA[] findEF_RailType_EPAs() {
      EF_RailType_EPA.list()
    }

    def EF_RailType_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_RailType_EPA = EF_RailType_EPA.get(parameters.id)
      if (!theEF_RailType_EPA) {
        theEF_RailType_EPA = new EF_RailType_EPA()
      }
      theEF_RailType_EPA.properties = parameters;

      theEF_RailType_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_RailType_EPA.get(parameters.id)?.delete()
    }
}
