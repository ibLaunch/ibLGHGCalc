package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class StationaryCombustionInfoController {
  def stationaryCombustionInfoService

  def list = {
    log.info "StationaryCombustionInfoController.list( ${params} )"
    def stationaryCombustionInfos = stationaryCombustionInfoService.findStationaryCombustionInfos(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        stationaryCombustionInfos.each { theStationaryCombustionInfo ->
          flushStationaryCombustionInfo xml, theStationaryCombustionInfo
        }
      }
    }
  }

  def save = {
    log.info "StationaryCombustionInfoController.add( ${params} )"
    def theStationaryCombustionInfo = stationaryCombustionInfoService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushStationaryCombustionInfo xml, theStationaryCombustionInfo
      }
    }
  }

  def remove = {
    log.info "StationaryCombustionInfoController.remove( ${params} )"
    stationaryCombustionInfoService.remove(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      data {
        status(0)
        record {
          id(params.id)
        }
      }
    }
  }

  private def flushStationaryCombustionInfo = { xml, stationaryCombustionInfo ->
    xml.record(
        id: stationaryCombustionInfo.id,
        organizationId: stationaryCombustionInfo.organizationId,
        fuelSourceDescription: stationaryCombustionInfo.fuelSourceDescription,
        fuelType: stationaryCombustionInfo.fuelType,
        fuelQuantity: stationaryCombustionInfo.fuelQuantity,
        fuelUnit: stationaryCombustionInfo.fuelUnit,
        fuelUsedBeginDate:stationaryCombustionInfo.fuelUsedBeginDate,
        fuelUsedEndDate:stationaryCombustionInfo.fuelUsedEndDate
        //isPublic: stationaryCombustionInfo.isPublic
    )
  }
}
