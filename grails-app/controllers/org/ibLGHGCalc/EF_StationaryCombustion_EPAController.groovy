package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class EF_StationaryCombustion_EPAController {

    def loadEmissionFactorsService

    //-- Service below is not working, not sure why
    //def eF_StationaryCombustion_EPAService

//--Following methods are for list,save,update and remove
  def list = {
    log.info "EF_StationaryCombustion_EPAController.list( ${params} )"
    def eF_StationaryCombustion_EPAs = loadEmissionFactorsService.findEF_StationaryCombustion_EPAs();
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        eF_StationaryCombustion_EPAs.each { theEF_StationaryCombustion_EPA ->
          flushEF_StationaryCombustion_EPA xml, theEF_StationaryCombustion_EPA
        }
      }
    }
  }

  def save = {
    log.info "EF_StationaryCombustion_EPAController.add( ${params} )"
    def theEF_StationaryCombustion_EPA = loadEmissionFactorsService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEF_StationaryCombustion_EPA xml, theEF_StationaryCombustion_EPA
      }
    }
  }

  def remove = {
    log.info "EF_StationaryCombustion_EPAController.remove( ${params} )"
    loadEmissionFactorsService.remove(params)
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

  private def flushEF_StationaryCombustion_EPA = { xml, eF_StationaryCombustion_EPA ->
    xml.record(
        id: eF_StationaryCombustion_EPA.id,
        fuelType: eF_StationaryCombustion_EPA.fuelType,
        fuelUnit: eF_StationaryCombustion_EPA.fuelUnit,
        CO2MultiplierInKg: eF_StationaryCombustion_EPA.CO2MultiplierInKg,
        CH4MultiplierInGram: eF_StationaryCombustion_EPA.CH4MultiplierInGram,
        N2OMultiplierInGram: eF_StationaryCombustion_EPA.N2OMultiplierInGram,
        isPublic: eF_StationaryCombustion_EPA.isPublic
    )
  }
  
  /*
//-- This service is the loas emission factors files
    def uploadEmissionFactorFile = {
        def returnString
        println "params are-------:" + params
        String fileLoaded = loadEmissionFactorsService.emissionFactorFileUpload(params)
        println fileLoaded
        //returnString = "Hello, I am done"
        
        render(view:"/stationaryCombustion")
        //render "File Uploaded :"

        sendMail {
           to "Hemant.Bundele@gmail.com"
           subject "Hello Billionaire"
           body 'Yes you can!'
        }   
    }
    */
}
