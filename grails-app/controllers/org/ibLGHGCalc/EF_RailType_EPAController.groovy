package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_RailType_EPAController {

    def EF_RailType_EPAService

    //-- Service below is not working, not sure why
    //def eF_RailType_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_RailType_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_RailType_EPAService, hence I put following code commented for now.
    def eF_RailType_EPAs = EF_RailType_EPAService.findEF_RailType_EPAs();
    //Instead calling list directly
    //def eF_RailType_EPAs = EF_RailType_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_RailType_EPAs.each { theEF_RailType_EPA ->
          flushEF_RailType_EPA xml, theEF_RailType_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_RailType_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_RailType_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_RailType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_RailType_EPA xml, theEF_RailType_EPA
      }
    }
  }

  def remove = {
    log.info "EF_RailType_EPAController.remove( ${params} )"
    EF_RailType_EPAService.remove(params)
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

  private def flushEF_RailType_EPA = { xml, eF_RailType_EPA ->
    xml.record(
        id: eF_RailType_EPA.id,
        railType: eF_RailType_EPA.railType,
        CO2Multiplier: eF_RailType_EPA.CO2Multiplier,
        CH4Multiplier: eF_RailType_EPA.CH4Multiplier,
        N2OMultiplier: eF_RailType_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_RailType_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_RailType_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_RailType_EPA.N2OMultiplierUnit
    )
  }
}
