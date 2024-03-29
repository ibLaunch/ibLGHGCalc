package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.commons.io.FileUtils

class EmissionsSummaryController {
  def emissionsSummaryService
  def aclUtilService
  def springSecurityService
  def grailsApplication
  def jasperService  
  
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
        
        purchasedGoodsAndServicesEmissions:emissionsSummary.purchasedGoodsAndServicesEmissions,        
        purchasedCapitalGoodsEmissions:emissionsSummary.purchasedCapitalGoodsEmissions,        
        
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
        lastUpdated: emissionsSummary.lastUpdated
        //lastUpdated: emissionsSummary.lastUpdated.format("yyyy-MM-dd")
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
        
       def reportParameters = [:]
       
       Integer orgId =  (Integer) Long.decode(params?.organizationId)
       Date beginDate = new Date().parse('yyyy-MM-dd',theEmissionsSummary?.emissionsBeginDate.toString())
       Date endDate = new Date().parse('yyyy-MM-dd',theEmissionsSummary?.emissionsEndDate.toString())

       println "---begin date is: ${theEmissionsSummary?.emissionsBeginDate}"
       println "---end date is: ${theEmissionsSummary?.emissionsEndDate}"
            
       reportParameters.put("report_organization_id",orgId)
       reportParameters.put("report_begin_date",beginDate)
       reportParameters.put("report_end_date",endDate)
       
       def jasperReportFileName = ConfigurationHolder.config.jasperReportFileName
       def reportDef = new JasperReportDef(name:jasperReportFileName,fileFormat:JasperExportFormat.PDF_FORMAT, parameters:reportParameters)       
       def reportFileName = "Report-"+orgId+ "-"+ (new Date().format('yyyy-MM-dd-HH-mm-ssZ')).toString()+".pdf"
       
        //Get the basePath for file location
        def basePath = ConfigurationHolder.config.basePath
                
        //--Generate report now
        try {
            //FileUtils.writeByteArrayToFile(new File(basePath+"/reports/"+orgId+"/"+reportFileName), jasperService.generateReport(reportDef).toByteArray())
            //response.outputStream << "Report generated"
            response.setHeader("Content-Disposition", "attachment; filename=" + reportFileName);
            response.outputStream << jasperService.generateReport(reportDef).toByteArray()            
        } catch (Exception e) {
            println "----------------Exception Caught-----------------------"
            log.error e
            println e
            println e.printStackTrace()
            println "Report not Generated!"
        }        
                        
    } else{
        println " No report found ---------------"
        response.outputStream << " Either report not generated or you don't have access to this report, contact support if needed"
    }
    
 }
 
}