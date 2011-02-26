package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_PurchasedSteam_EPAController {

    def EF_PurchasedSteam_EPAService

    //-- Service below is not working, not sure why
    //def eF_PurchasedSteam_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_PurchasedSteam_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_PurchasedSteam_EPAService, hence I put following code commented for now.
    def eF_PurchasedSteam_EPAs = EF_PurchasedSteam_EPAService.findEF_PurchasedSteam_EPAs();
    //Instead calling list directly
    //def eF_PurchasedSteam_EPAs = EF_PurchasedSteam_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_PurchasedSteam_EPAs.each { theEF_PurchasedSteam_EPA ->
          flushEF_PurchasedSteam_EPA xml, theEF_PurchasedSteam_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_PurchasedSteam_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_PurchasedSteam_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_PurchasedSteam_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_PurchasedSteam_EPA xml, theEF_PurchasedSteam_EPA
      }
    }
  }

  def remove = {
    log.info "EF_PurchasedSteam_EPAController.remove( ${params} )"
    EF_PurchasedSteam_EPAService.remove(params)
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

  private def flushEF_PurchasedSteam_EPA = { xml, eF_PurchasedSteam_EPA ->
    xml.record(
        id: eF_PurchasedSteam_EPA.id,
        fuelType: eF_PurchasedSteam_EPA.fuelType,
        CO2Multiplier: eF_PurchasedSteam_EPA.CO2Multiplier,
        CH4Multiplier: eF_PurchasedSteam_EPA.CH4Multiplier,
        N2OMultiplier: eF_PurchasedSteam_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_PurchasedSteam_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_PurchasedSteam_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_PurchasedSteam_EPA.N2OMultiplierUnit,
    )
  }
}
