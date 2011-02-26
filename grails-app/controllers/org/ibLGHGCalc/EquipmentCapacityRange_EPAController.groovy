package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EquipmentCapacityRange_EPAController {

//-- services below not working, giving null pointer error. Not sure why.
  //def equipmentCapacityRange_EPA_2Service
  def equipmentCapacityRange_EPAService
  //def eFCH4N2OMobileCombustionEPAService

  def list = {
    log.info "EquipmentCapacityRange_EPAController.list( ${params} )"
    //-- No serice is working hence directly calling list() method here ?? in future look at why non of the services are working.
    //def equipmentCapacityRange_EPAs = equipmentCapacityRange_EPAService.findEquipmentCapacityRange_EPAs();
    def equipmentCapacityRange_EPAs = EquipmentCapacityRange_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        equipmentCapacityRange_EPAs.each { theEquipmentCapacityRange_EPA ->
          flushEquipmentCapacityRange_EPA xml, theEquipmentCapacityRange_EPA
        }
      }
    }
  }

  def save = {
    log.info "EquipmentCapacityRange_EPAController.add( ${params} )"
    def theEquipmentCapacityRange_EPA = equipmentCapacityRange_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEquipmentCapacityRange_EPA xml, theEquipmentCapacityRange_EPA
      }
    }
  }

  def remove = {
    log.info "EquipmentCapacityRange_EPAController.remove( ${params} )"
    equipmentCapacityRange_EPAService.remove(params)
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

  private def flushEquipmentCapacityRange_EPA = { xml, equipmentCapacityRange_EPA ->
    xml.record(
        id: equipmentCapacityRange_EPA.id,
        typeOfEquipment: equipmentCapacityRange_EPA.typeOfEquipment,
        capacityRange: equipmentCapacityRange_EPA.capacityRange,
        capacityRangeUnit: equipmentCapacityRange_EPA.capacityRangeUnit,
        kFactor: equipmentCapacityRange_EPA.kFactor,
        xFactor: equipmentCapacityRange_EPA.xFactor,
        yFactor: equipmentCapacityRange_EPA.yFactor,
        zFactor: equipmentCapacityRange_EPA.zFactor
    )
  }
}