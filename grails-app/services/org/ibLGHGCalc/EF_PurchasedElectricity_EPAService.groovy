package org.ibLGHGCalc

class EF_PurchasedElectricity_EPAService {

    static transactional = true

    EF_PurchasedElectricity_EPA findEF_PurchasedElectricity_EPA(Long id) {
      EF_PurchasedElectricity_EPA.get(id)
    }

    def EF_PurchasedElectricity_EPA[] findEF_PurchasedElectricity_EPAs() {
      EF_PurchasedElectricity_EPA.list()
    }

    def EF_PurchasedElectricity_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_PurchasedElectricity_EPA = EF_PurchasedElectricity_EPA.get(parameters.id)
      if (!theEF_PurchasedElectricity_EPA) {
        theEF_PurchasedElectricity_EPA = new EF_PurchasedElectricity_EPA()
      }
      theEF_PurchasedElectricity_EPA.properties = parameters;

      theEF_PurchasedElectricity_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_PurchasedElectricity_EPA.get(parameters.id)?.delete()
    }
}
