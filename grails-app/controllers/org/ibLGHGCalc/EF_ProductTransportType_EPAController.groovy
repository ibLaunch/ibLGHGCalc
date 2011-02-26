package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_ProductTransportType_EPAController {

    def EF_ProductTransportType_EPAService

    //-- Service below is not working, not sure why
    //def eF_ProductTransportType_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_ProductTransportType_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_ProductTransportType_EPAService, hence I put following code commented for now.
    def eF_ProductTransportType_EPAs = EF_ProductTransportType_EPAService.findEF_ProductTransportType_EPAs();
    //Instead calling list directly
    //def eF_ProductTransportType_EPAs = EF_ProductTransportType_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_ProductTransportType_EPAs.each { theEF_ProductTransportType_EPA ->
          flushEF_ProductTransportType_EPA xml, theEF_ProductTransportType_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_ProductTransportType_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_ProductTransportType_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_ProductTransportType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_ProductTransportType_EPA xml, theEF_ProductTransportType_EPA
      }
    }
  }

  def remove = {
    log.info "EF_ProductTransportType_EPAController.remove( ${params} )"
    EF_ProductTransportType_EPAService.remove(params)
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

  private def flushEF_ProductTransportType_EPA = { xml, eF_ProductTransportType_EPA ->
    xml.record(
        id: eF_ProductTransportType_EPA.id,
        transportType: eF_ProductTransportType_EPA.transportType,
        CO2Multiplier: eF_ProductTransportType_EPA.CO2Multiplier,
        CH4Multiplier: eF_ProductTransportType_EPA.CH4Multiplier,
        N2OMultiplier: eF_ProductTransportType_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_ProductTransportType_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_ProductTransportType_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_ProductTransportType_EPA.N2OMultiplierUnit
    )
  }
}
