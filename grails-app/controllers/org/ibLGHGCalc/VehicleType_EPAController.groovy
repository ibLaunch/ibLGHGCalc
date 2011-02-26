package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class VehicleType_EPAController {
  def vehicleType_EPAService

  def list = {
    log.info "VehicleType_EPAController.list( ${params} )"
    def vehicleType_EPAs = vehicleType_EPAService.findVehicleType_EPAs();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        vehicleType_EPAs.each { theVehicleType_EPA ->
          flushVehicleType_EPA xml, theVehicleType_EPA
        }
      }
    }
  }

  def save = {
    log.info "VehicleType_EPAController.add( ${params} )"
    def theVehicleType_EPA = vehicleType_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushVehicleType_EPA xml, theVehicleType_EPA
      }
    }
  }

  def remove = {
    log.info "VehicleType_EPAController.remove( ${params} )"
    vehicleType_EPAService.remove(params)
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

  private def flushVehicleType_EPA = { xml, vehicleType_EPA ->
    xml.record(
        id: vehicleType_EPA.id,
        vehicleType: vehicleType_EPA.vehicleType,
        fuelUnit: vehicleType_EPA.fuelUnit,
        fuelType: vehicleType_EPA.fuelType
    )
  }
}
