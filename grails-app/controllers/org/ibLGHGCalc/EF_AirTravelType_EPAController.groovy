package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_AirTravelType_EPAController {

    def EF_AirTravelType_EPAService

    //-- Service below is not working, not sure why
    //def eF_AirTravelType_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_AirTravelType_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_AirTravelType_EPAService, hence I put following code commented for now.
    def eF_AirTravelType_EPAs = EF_AirTravelType_EPAService.findEF_AirTravelType_EPAs();
    //Instead calling list directly
    //def eF_AirTravelType_EPAs = EF_AirTravelType_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_AirTravelType_EPAs.each { theEF_AirTravelType_EPA ->
          flushEF_AirTravelType_EPA xml, theEF_AirTravelType_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_AirTravelType_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_AirTravelType_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_AirTravelType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_AirTravelType_EPA xml, theEF_AirTravelType_EPA
      }
    }
  }

  def remove = {
    log.info "EF_AirTravelType_EPAController.remove( ${params} )"
    EF_AirTravelType_EPAService.remove(params)
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

  private def flushEF_AirTravelType_EPA = { xml, eF_AirTravelType_EPA ->
    xml.record(
        id: eF_AirTravelType_EPA.id,
        airTravelType: eF_AirTravelType_EPA.airTravelType,
        CO2Multiplier: eF_AirTravelType_EPA.CO2Multiplier,
        CH4Multiplier: eF_AirTravelType_EPA.CH4Multiplier,
        N2OMultiplier: eF_AirTravelType_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_AirTravelType_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_AirTravelType_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_AirTravelType_EPA.N2OMultiplierUnit
    )
  }
}
