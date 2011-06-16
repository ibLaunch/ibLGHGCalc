package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class GWP_RefridgerationAirConditioning_EPAController {

//-- services below not working, giving null pointer error. Not sure why.
  //def gWP_RefridgerationAirConditioning_EPA_2Service
  def gWP_RefridgerationAirConditioning_EPAService
  //def eFCH4N2OMobileCombustionEPAService

  def list = {
    log.info "GWP_RefridgerationAirConditioning_EPAController.list( ${params} )"
    println "---params---" + params
    
    //-- No serice is working hence directly calling list() method here ?? in future look at why non of the services are working.
    //def gWP_RefridgerationAirConditioning_EPAs = gWP_RefridgerationAirConditioning_EPAService.findGWP_RefridgerationAirConditioning_EPAs();
    def gWP_RefridgerationAirConditioning_EPAs = GWP_RefridgerationAirConditioning_EPA.list();
    def xml = new MarkupBuilder(response.writer)

        
    /*
    def typeOfData
    if (params.fetchType.equals("RefridgerationAirConditioning")) {typeOfData = 1 }
    else if (params.fetchType.equals("FireSuppression")) {typeOfData = 2}
    */

    xml.response() {
      status(0)
      data {
        gWP_RefridgerationAirConditioning_EPAs.each { theGWP_RefridgerationAirConditioning_EPA ->
          if (params.fetchType.equals("RefridgerationAirConditioning")){
              if (theGWP_RefridgerationAirConditioning_EPA.isUsedInRefridgeration){
                  flushGWP_RefridgerationAirConditioning_EPA xml, theGWP_RefridgerationAirConditioning_EPA
              }
          } else if (params.fetchType.equals("FireSuppression")){
              if (theGWP_RefridgerationAirConditioning_EPA.isUsedInFireSuppression){
                  flushGWP_RefridgerationAirConditioning_EPA xml, theGWP_RefridgerationAirConditioning_EPA
              }
          }
          //flushGWP_RefridgerationAirConditioning_EPA xml, theGWP_RefridgerationAirConditioning_EPA
        }
      }
    }
  }

  def save = {
    log.info "GWP_RefridgerationAirConditioning_EPAController.add( ${params} )"
    def theGWP_RefridgerationAirConditioning_EPA = gWP_RefridgerationAirConditioning_EPAService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushGWP_RefridgerationAirConditioning_EPA xml, theGWP_RefridgerationAirConditioning_EPA
      }
    }
  }

  def remove = {
    log.info "GWP_RefridgerationAirConditioning_EPAController.remove( ${params} )"
    gWP_RefridgerationAirConditioning_EPAService.remove(params)
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

  private def flushGWP_RefridgerationAirConditioning_EPA = { xml, gWP_RefridgerationAirConditioning_EPA ->
    xml.record(
        id: gWP_RefridgerationAirConditioning_EPA.id,
        gasType: gWP_RefridgerationAirConditioning_EPA.gasType,
        gasTypeGWP: gWP_RefridgerationAirConditioning_EPA.gasTypeGWP
    )
  }
}