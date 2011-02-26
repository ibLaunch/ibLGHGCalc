package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_VehicleType_EPAController {

    def EF_VehicleType_EPAService

    //-- Service below is not working, not sure why
    //def eF_VehicleType_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_VehicleType_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_VehicleType_EPAService, hence I put following code commented for now.
    def eF_VehicleType_EPAs = EF_VehicleType_EPAService.findEF_VehicleType_EPAs();
    //Instead calling list directly
    //def eF_VehicleType_EPAs = EF_VehicleType_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_VehicleType_EPAs.each { theEF_VehicleType_EPA ->
          flushEF_VehicleType_EPA xml, theEF_VehicleType_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_VehicleType_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_VehicleType_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_VehicleType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_VehicleType_EPA xml, theEF_VehicleType_EPA
      }
    }
  }

  def remove = {
    log.info "EF_VehicleType_EPAController.remove( ${params} )"
    EF_VehicleType_EPAService.remove(params)
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

  private def flushEF_VehicleType_EPA = { xml, eF_VehicleType_EPA ->
    xml.record(
        id: eF_VehicleType_EPA.id,
        vehicleType: eF_VehicleType_EPA.vehicleType,
        CO2Multiplier: eF_VehicleType_EPA.CO2Multiplier,
        CH4Multiplier: eF_VehicleType_EPA.CH4Multiplier,
        N2OMultiplier: eF_VehicleType_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_VehicleType_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_VehicleType_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_VehicleType_EPA.N2OMultiplierUnit
    )
  }
}
