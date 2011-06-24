package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class FileUploadController {

    def loadEmissionFactorsService

    def index = { }
    
    def uploadFile = {
        println "params are-------:" + params
        String fileLoaded = loadEmissionFactorsService.emissionFactorFileUpload(params)
        println fileLoaded
        render(view:"/stationaryCombustion")
        //render "File Uploaded :"
        /*
        def xml = new MarkupBuilder(response.writer)
        xml.response() {
          status(0)
          data {
            "File Uploaded :"
          }
        }
        */
    }
}
