package org.ibLGHGCalc

class StationaryCombustionInfoService {

    static transactional = true

  
    StationaryCombustionInfo findStationaryCombustionInfo(Long id) {
      StationaryCombustionInfo.get(id)
    }

    def StationaryCombustionInfo[] findStationaryCombustionInfos() {
      StationaryCombustionInfo.list()
    }

    def StationaryCombustionInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      def theStationaryCombustionInfo = StationaryCombustionInfo.get(parameters.id)
      if (!theStationaryCombustionInfo) {
        theStationaryCombustionInfo = new StationaryCombustionInfo()
      }
      theStationaryCombustionInfo.properties = parameters;
      theStationaryCombustionInfo.isPublic =
         "true".equals(parameters.isPublic) ? true : false
      theStationaryCombustionInfo.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      StationaryCombustionInfo.get(parameters.id)?.delete()
    }
}
