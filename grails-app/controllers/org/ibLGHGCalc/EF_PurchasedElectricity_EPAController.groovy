package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_PurchasedElectricity_EPAController {

    def eF_PurchasedElectricity_EPAService

    //-- Service below is not working, not sure why
    //def eF_PurchasedElectricity_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_PurchasedElectricity_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_PurchasedElectricity_EPAService, hence I put following code commented for now.
    //def eF_PurchasedElectricity_EPAs = eF_PurchasedElectricity_EPAService.findEF_PurchasedElectricity_EPAs();
    //Instead calling list directly
    def eF_PurchasedElectricity_EPAs = EF_PurchasedElectricity_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_PurchasedElectricity_EPAs.each { theEF_PurchasedElectricity_EPA ->
          flushEF_PurchasedElectricity_EPA xml, theEF_PurchasedElectricity_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_PurchasedElectricity_EPAService, hence I put following code commented for now.
/*
  def save = {
    log.info "EF_PurchasedElectricity_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = eF_PurchasedElectricity_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_PurchasedElectricity_EPA xml, theEF_PurchasedElectricity_EPA
      }
    }
  }

  def remove = {
    log.info "EF_PurchasedElectricity_EPAController.remove( ${params} )"
    eF_PurchasedElectricity_EPAService.remove(params)
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
*/
  private def flushEF_PurchasedElectricity_EPA = { xml, eF_PurchasedElectricity_EPA ->
    xml.record(
        id: eF_PurchasedElectricity_EPA.id,
        eGRIDSubregion: eF_PurchasedElectricity_EPA.eGRIDSubregion,
        CO2Multiplier: eF_PurchasedElectricity_EPA.CO2Multiplier,
        CH4Multiplier: eF_PurchasedElectricity_EPA.CH4Multiplier,
        N2OMultiplier: eF_PurchasedElectricity_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_PurchasedElectricity_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_PurchasedElectricity_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_PurchasedElectricity_EPA.N2OMultiplierUnit,
        dataYear: eF_PurchasedElectricity_EPA.dataYear
    )
  }
}
