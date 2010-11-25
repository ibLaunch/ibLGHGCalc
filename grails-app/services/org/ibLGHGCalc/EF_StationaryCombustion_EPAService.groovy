package org.ibLGHGCalc

class EF_StationaryCombustion_EPAService {

    static transactional = true
    static expose = ["gwt:org.ibLGHGCalc.client"]
  
    EF_StationaryCombustion_EPA findEF_StationaryCombustion_EPA(Long id) {
      EF_StationaryCombustion_EPA.get(id)
    }

    def EF_StationaryCombustion_EPA[] findEF_StationaryCombustion_EPAs() {
      EF_StationaryCombustion_EPA.list()
    }

    def EF_StationaryCombustion_EPA save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theEF_StationaryCombustion_EPA = EF_StationaryCombustion_EPA.get(parameters.id)
      if (!theEF_StationaryCombustion_EPA) {
        theEF_StationaryCombustion_EPA = new EF_StationaryCombustion_EPA()
      }
      theEF_StationaryCombustion_EPA.properties = parameters;
      theEF_StationaryCombustion_EPA.isPublic =
         "true".equals(parameters.isPublic) ? true : false
      theEF_StationaryCombustion_EPA.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      EF_StationaryCombustion_EPA.get(parameters.id)?.delete()
    }
}
