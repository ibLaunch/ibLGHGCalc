package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_CH4N2O_MobileCombustion_EPAController {

//-- services below not working, giving null pointer error. Not sure why.
  //def eF_CH4N2O_MobileCombustion_EPA_2Service
  def eF_CH4N2O_MobileCombustion_EPAService
  //def eFCH4N2OMobileCombustionEPAService

  def list = {
    log.info "EF_CH4N2O_MobileCombustion_EPAController.list( ${params} )"
    //-- No serice is working hence directly calling list() method here ?? in future look at why non of the services are working.
    //def eF_CH4N2O_MobileCombustion_EPAs = eF_CH4N2O_MobileCombustion_EPAService.findEF_CH4N2O_MobileCombustion_EPAs();
    def eF_CH4N2O_MobileCombustion_EPAs = EF_CH4N2O_MobileCombustion_EPA.list();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_CH4N2O_MobileCombustion_EPAs.each { theEF_CH4N2O_MobileCombustion_EPA ->
          flushEF_CH4N2O_MobileCombustion_EPA xml, theEF_CH4N2O_MobileCombustion_EPA
        }
      }
    }
  }

  def save = {
    log.info "EF_CH4N2O_MobileCombustion_EPAController.add( ${params} )"
    def theEF_CH4N2O_MobileCombustion_EPA = eF_CH4N2O_MobileCombustion_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_CH4N2O_MobileCombustion_EPA xml, theEF_CH4N2O_MobileCombustion_EPA
      }
    }
  }

  def remove = {
    log.info "EF_CH4N2O_MobileCombustion_EPAController.remove( ${params} )"
    eF_CH4N2O_MobileCombustion_EPAService.remove(params)
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

  private def flushEF_CH4N2O_MobileCombustion_EPA = { xml, eF_CH4N2O_MobileCombustion_EPA ->
    xml.record(
        id: eF_CH4N2O_MobileCombustion_EPA.id,
        vehicleType: eF_CH4N2O_MobileCombustion_EPA.vehicleType,
        vehicleYear: eF_CH4N2O_MobileCombustion_EPA.vehicleYear,
        CH4MultiplierInGram: eF_CH4N2O_MobileCombustion_EPA.CH4MultiplierInGram,
        N2OMultiplierInGram: eF_CH4N2O_MobileCombustion_EPA.N2OMultiplierInGram
    )
  }
}