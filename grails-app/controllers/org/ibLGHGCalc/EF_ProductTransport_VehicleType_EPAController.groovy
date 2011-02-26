package org.ibLGHGCalc
import groovy.xml.MarkupBuilder

class EF_ProductTransport_VehicleType_EPAController {

    def EF_ProductTransport_VehicleType_EPAService

    //-- Service below is not working, not sure why
    //def eF_ProductTransport_VehicleType_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_ProductTransport_VehicleType_EPAController.list( ${params} )"
    //--Not sure why defined service is not working eF_ProductTransport_VehicleType_EPAService, hence I put following code commented for now.
    def eF_ProductTransport_VehicleType_EPAs = EF_ProductTransport_VehicleType_EPAService.findEF_ProductTransport_VehicleType_EPAs();
    //Instead calling list directly
    //def eF_ProductTransport_VehicleType_EPAs = EF_ProductTransport_VehicleType_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_ProductTransport_VehicleType_EPAs.each { theEF_ProductTransport_VehicleType_EPA ->
          flushEF_ProductTransport_VehicleType_EPA xml, theEF_ProductTransport_VehicleType_EPA
        }
      }
    }
  }

//--Not sure why defined service is not working eF_ProductTransport_VehicleType_EPAService, hence I put following code commented for now.

  def save = {
    log.info "EF_ProductTransport_VehicleType_EPAController.add( ${params} )"
    def theEF_PurchasedElectricity_EPA = EF_ProductTransport_VehicleType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_ProductTransport_VehicleType_EPA xml, theEF_ProductTransport_VehicleType_EPA
      }
    }
  }

  def remove = {
    log.info "EF_ProductTransport_VehicleType_EPAController.remove( ${params} )"
    EF_ProductTransport_VehicleType_EPAService.remove(params)
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

  private def flushEF_ProductTransport_VehicleType_EPA = { xml, eF_ProductTransport_VehicleType_EPA ->
    xml.record(
        id: eF_ProductTransport_VehicleType_EPA.id,
        vehicleType: eF_ProductTransport_VehicleType_EPA.vehicleType,
        CO2Multiplier: eF_ProductTransport_VehicleType_EPA.CO2Multiplier,
        CH4Multiplier: eF_ProductTransport_VehicleType_EPA.CH4Multiplier,
        N2OMultiplier: eF_ProductTransport_VehicleType_EPA.N2OMultiplier,
        CO2MultiplierUnit: eF_ProductTransport_VehicleType_EPA.CO2MultiplierUnit,
        CH4MultiplierUnit: eF_ProductTransport_VehicleType_EPA.CH4MultiplierUnit,
        N2OMultiplierUnit: eF_ProductTransport_VehicleType_EPA.N2OMultiplierUnit
    )
  }
}
