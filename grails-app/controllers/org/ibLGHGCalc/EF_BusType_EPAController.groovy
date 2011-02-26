package org.ibLGHGCalc

import groovy.xml.MarkupBuilder


class EF_BusType_EPAController {

    def EF_BusType_EPAService

    //-- Service below is not working, not sure why
    //def eF_BusType_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_BusType_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_BusType_EPAService, hence I put following code commented for now.
    def eF_BusType_EPAs = EF_BusType_EPAService.findEF_BusType_EPAs();
    //Instead calling list directly
    //def eF_BusType_EPAs = EF_BusType_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_BusType_EPAs.each { theEF_BusType_EPA ->
          flushEF_BusType_EPA xml, theEF_BusType_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_BusType_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_BusType_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_BusType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_BusType_EPA xml, theEF_BusType_EPA
      }
    }
  }

  def remove = {
    log.info "EF_BusType_EPAController.remove( ${params} )"
    EF_BusType_EPAService.remove(params)
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

  private def flushEF_BusType_EPA = { xml, eF_BusType_EPA ->
    xml.record(
        id: eF_BusType_EPA.id,
        busType: eF_BusType_EPA.busType,
        CO2Multiplier: eF_BusType_EPA.CO2Multiplier,
        CH4Multiplier: eF_BusType_EPA.CH4Multiplier,
        N2OMultiplier: eF_BusType_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_BusType_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_BusType_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_BusType_EPA.N2OMultiplierUnit
    )
  }
}
