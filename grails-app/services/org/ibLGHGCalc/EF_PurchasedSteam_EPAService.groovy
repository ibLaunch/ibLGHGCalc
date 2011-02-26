package org.ibLGHGCalc

class EF_PurchasedSteam_EPAService {

    static transactional = true

    EF_PurchasedSteam_EPA findEF_PurchasedSteam_EPA(Long id) {
      EF_PurchasedSteam_EPA.get(id)
    }

    def EF_PurchasedSteam_EPA[] findEF_PurchasedSteam_EPAs() {
      EF_PurchasedSteam_EPA.list()
    }

    def EF_PurchasedSteam_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_PurchasedSteam_EPA = EF_PurchasedSteam_EPA.get(parameters.id)
      if (!theEF_PurchasedSteam_EPA) {
        theEF_PurchasedSteam_EPA = new EF_PurchasedSteam_EPA()
      }
      theEF_PurchasedSteam_EPA.properties = parameters;

      theEF_PurchasedSteam_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_PurchasedSteam_EPA.get(parameters.id)?.delete()
    }
}
