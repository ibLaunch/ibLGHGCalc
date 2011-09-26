package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission


class EmissionsSummaryController {
  def emissionsSummaryService
  def aclUtilService
  def springSecurityService
  def grailsApplication
  
  def list = {
    log.info "EmissionsSummaryController.list( ${params} )"
    println "params: -----"+ params
    def emissionsSummarys = emissionsSummaryService.findEmissionsSummarys(params);
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        emissionsSummarys.each { theEmissionsSummary ->
          flushEmissionsSummary xml, theEmissionsSummary
        }
      }
    }
  }

  def save = {
    log.info "EmissionsSummaryController.add( ${params} )"
    //def theEmissionsSummary = emissionsSummaryService.save(params)
    def theEmissionsSummary

    try {
        theEmissionsSummary = emissionsSummaryService.save(params)
        //-- temporary approach below for now, need more better approach??
        aclUtilService.addPermission(theEmissionsSummary, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theEmissionsSummary not Saved"
    }

    println "theEmissionsSummary from Controller is: " + theEmissionsSummary

    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEmissionsSummary xml, theEmissionsSummary
      }
    }
  }

  def remove = {
    log.info "EmissionsSummaryController.remove( ${params} )"
    emissionsSummaryService.remove(params)
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

  private def flushEmissionsSummary = { xml, emissionsSummary ->
    xml.record(
        id: emissionsSummary.id,
        organizationId: emissionsSummary.organizationId,
    	
	stationaryCombustionEmissions: emissionsSummary.stationaryCombustionEmissions,
    	mobileCombustionEmissions: emissionsSummary.mobileCombustionEmissions,
    	refridgerationAirConditioningEmissions: emissionsSummary.refridgerationAirConditioningEmissions,
    	fireSuppressantEmissions: emissionsSummary.fireSuppressantEmissions,
    	wasteStreamCombustionEmissions: emissionsSummary.wasteStreamCombustionEmissions,

        purchasedElectricityEmissions: emissionsSummary.purchasedElectricityEmissions,
        purchasedSteamEmissions: emissionsSummary.purchasedSteamEmissions,

        employeeBusinessTravelByVehicleEmissions:  emissionsSummary.employeeBusinessTravelByVehicleEmissions,
        employeeBusinessTravelByRailEmissions:emissionsSummary.employeeBusinessTravelByRailEmissions,
        employeeBusinessTravelByBusEmissions:emissionsSummary.employeeBusinessTravelByBusEmissions,
        employeeBusinessTravelByAirEmissions:emissionsSummary.employeeBusinessTravelByAirEmissions,

        employeeCommutingByVehicleEmissions:emissionsSummary.employeeCommutingByVehicleEmissions,
        employeeCommutingByRailEmissions:emissionsSummary.employeeCommutingByRailEmissions,
        employeeCommutingByBusEmissions:emissionsSummary.employeeCommutingByBusEmissions,

        productTransportByVehicleEmissions:emissionsSummary.productTransportByVehicleEmissions,
        productTransportByHeavyDutyTrucksEmissions:emissionsSummary.productTransportByHeavyDutyTrucksEmissions,
        productTransportByRailEmissions:emissionsSummary.productTransportByRailEmissions,
        productTransportByWaterAirEmissions:emissionsSummary.productTransportByWaterAirEmissions,

        biomassStationaryCombustionEmissions: emissionsSummary.biomassStationaryCombustionEmissions,
        biomassMobileCombustionEmissions: emissionsSummary.biomassMobileCombustionEmissions,

    	totalEmissions: emissionsSummary.totalEmissions,
        totalOptionalEmissions: emissionsSummary.totalOptionalEmissions,
        totalDirectEmissions: emissionsSummary.totalDirectEmissions,
        totalInDirectEmissions: emissionsSummary.totalInDirectEmissions,
        totalNumberOfSources: emissionsSummary.totalNumberOfSources,
        programType: emissionsSummary.programType,        
    	emissionsBeginDate: emissionsSummary.emissionsBeginDate?.format("yyyy-MM-dd"),
    	emissionsEndDate: emissionsSummary.emissionsEndDate?.format("yyyy-MM-dd"),
        reportFileName: emissionsSummary?.reportFileName,
        lastUpdated: emissionsSummary.lastUpdated.format("yyyy-MM-dd")
    )
  }

  def calculateEmissionsSummary = {
    log.info "EmissionsSummaryController.calculateEmissionsSummary( ${params} )"
    def theEmissionsSummary = emissionsSummaryService.calculateEmissionsSummary(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushEmissionsSummary xml, theEmissionsSummary
      }
    }
  }

def serveReportFile = {
    
    println "params:======"+params
    def  emissionsSummaryReportId = Long.decode(params.emissionsSummaryReportId)
    //def  emissionsSummaryReportId = params.emissionsSummaryReportId
    def theEmissionsSummary = emissionsSummaryService.findEmissionsSummary(emissionsSummaryReportId);

    if (theEmissionsSummary) {
        println "params:---"+params
        String fileName = theEmissionsSummary?.reportFileName
        def reportFile
        
        //Get the basePAth
        def basePath = grailsApplication.parentContext.getResource("/").file.toString()
        println "----basePath while serving report----:" +basePath



        def basePath2 = grailsAttributes.getApplicationContext().getResource("/").getFile()
        println "----basePath2 while serving report----?????????:" +basePath2

        def basePath3 =grailsApplication.config.basePath
        println "----basePath3 while serving report----?????????:" +basePath3

        try {
            //reportFile = new File(System.properties['base.dir']+"/reports/"+params.organizationId+"/"+fileName);
            reportFile = new File(basePath3+"/reports/"+params.organizationId+"/"+fileName);
            //response.setContentType("application/x-download");
        }
        catch(FileNotFoundException e) {
//            println "Report not Generated!!!!"
            log.error e            
            //reportFound = 0
        }
        catch (IOException e) {
            println "Caught IOException: " + e.getMessage()
        }

        if (reportFile.size() > 0){
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.outputStream << reportFile.readBytes()            
        } else {
            response.outputStream << "Report file not generated"
        }

    } else {
        println " No report found ---------------"
        response.outputStream << " Either report not generated or you don't have access to this report, contact support if needed"
    }

/*
    if (theEmissionsSummary) {
        println "params:---"+params
        String fileName = theEmissionsSummary?.reportFileName


        if (fileName){
            def rFile = new File(System.properties['base.dir']+"/reports/"+params.organizationId+"/"+fileName);
            //response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.outputStream << rFile.readBytes()
        } 
        else {       
            log.error e
            println "Report not Generated!"
            response.outputStream << " No report found!!!!!"
        }
    } else {
        println " No report found ---------------"
        response.outputStream << " Either report not generated or you don't have access to this report, contact support if needed"
    }
*/

 }
 
}