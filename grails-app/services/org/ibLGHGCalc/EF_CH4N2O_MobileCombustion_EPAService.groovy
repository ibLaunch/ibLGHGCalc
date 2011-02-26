package org.ibLGHGCalc

class EF_CH4N2O_MobileCombustion_EPAService {

    static transactional = true
    //static expose = ["gwt:org.ibLGHGCalc.client"]

    EF_CH4N2O_MobileCombustion_EPA findEF_CH4N2O_MobileCombustion_EPA(Long id) {
      EF_CH4N2O_MobileCombustion_EPA.get(id)
    }

    def EF_CH4N2O_MobileCombustion_EPA[] findEF_CH4N2O_MobileCombustion_EPAs() {
      EF_CH4N2O_MobileCombustion_EPA.list()
    }

    def EF_CH4N2O_MobileCombustion_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_CH4N2O_MobileCombustion_EPA = EF_CH4N2O_MobileCombustion_EPA.get(parameters.id)
      if (!theEF_CH4N2O_MobileCombustion_EPA) {
        theEF_CH4N2O_MobileCombustion_EPA = new EF_CH4N2O_MobileCombustion_EPA()
      }
      theEF_CH4N2O_MobileCombustion_EPA.properties = parameters;

      theEF_CH4N2O_MobileCombustion_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_CH4N2O_MobileCombustion_EPA.get(parameters.id)?.delete()
    }

}
