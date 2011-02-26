package org.ibLGHGCalc

class GWP_RefridgerationAirConditioning_EPAService {

    static transactional = true

    GWP_RefridgerationAirConditioning_EPA findGWP_RefridgerationAirConditioning_EPA(Long id) {
      GWP_RefridgerationAirConditioning_EPA.get(id)
    }

    def GWP_RefridgerationAirConditioning_EPA[] findGWP_RefridgerationAirConditioning_EPAs() {
      GWP_RefridgerationAirConditioning_EPA.list()
    }

    def GWP_RefridgerationAirConditioning_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theGWP_RefridgerationAirConditioning_EPA = GWP_RefridgerationAirConditioning_EPA.get(parameters.id)
      if (!theGWP_RefridgerationAirConditioning_EPA) {
        theGWP_RefridgerationAirConditioning_EPA = new GWP_RefridgerationAirConditioning_EPA()
      }
      theGWP_RefridgerationAirConditioning_EPA.properties = parameters;

      theGWP_RefridgerationAirConditioning_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      GWP_RefridgerationAirConditioning_EPA.get(parameters.id)?.delete()
    }

}
