package org.ibLGHGCalc

import groovy.xml.MarkupBuilder
import org.springframework.security.acls.domain.BasePermission

class InvestmentsInfoController {

  def investmentsInfoService
  def aclUtilService
  def springSecurityService

  def list = {
    log.info "InvestmentsInfoController.list( ${params} )"

    def investmentsInfos = investmentsInfoService.findInvestmentsInfos(params);
                                                        
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        investmentsInfos.each { theInvestmentsInfo ->
          flushInvestmentsInfo xml, theInvestmentsInfo
        }
      }
    }
  }

  def save = {
    log.info "InvestmentsInfoController.add( ${params} )"        
    def theInvestmentsInfo
        
    try {
        theInvestmentsInfo = investmentsInfoService.save(params)
        println "springSecurityService.authentication.name: " + springSecurityService.authentication.name
    }
    catch (Exception e) {
        log.error e
        println "theInvestmentsInfo not Saved"
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushInvestmentsInfo xml, theInvestmentsInfo
      }
    }
  }

  def remove = {
    log.info "InvestmentsInfoController.remove( ${params} )"
    investmentsInfoService.remove(params)
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

  private def flushInvestmentsInfo = { xml, investmentsInfo ->
    xml.record(
        id: investmentsInfo.id,
        organizationId: investmentsInfo.organizationId,
        
        sourceDescription: investmentsInfo.sourceDescription,        
        investmentType: investmentsInfo.investmentType,

        //Investment Specific Method 
        scope1Emissions: investmentsInfo.scope1Emissions,
        scope1Emissions_Unit: investmentsInfo.scope1Emissions_Unit,        
        scope2Emissions: investmentsInfo.scope2Emissions,
        scope2Emissions_Unit: investmentsInfo.scope2Emissions_Unit,
        
        percentShareOfInvestment:investmentsInfo.percentShareOfInvestment,

        anticipatedLifetimeScope1Emissions: investmentsInfo.anticipatedLifetimeScope1Emissions,
        anticipatedLifetimeScope1Emissions_Unit: investmentsInfo.anticipatedLifetimeScope1Emissions_Unit,        
        anticipatedLifetimeScope2Emissions: investmentsInfo.anticipatedLifetimeScope2Emissions,
        anticipatedLifetimeScope2Emissions_Unit: investmentsInfo.anticipatedLifetimeScope2Emissions_Unit,
            
        //Average Data Method -  investments emission 
        investmentSector: investmentsInfo.investmentSector,
        
        investmentAmount: investmentsInfo.investmentAmount,
        investmentAmount_Unit: investmentsInfo.investmentAmount_Unit,

        EF_SectorSpecific: investmentsInfo.EF_SectorSpecific,            
        EF_SectorSpecific_Unit: investmentsInfo.EF_SectorSpecific_Unit,
                    
        //---General fields
        userNotesOnData:investmentsInfo.userNotesOnData,            
        methodType:investmentsInfo.methodType,
        dataBeginDate:investmentsInfo.dataBeginDate?.format("yyyy-MM-dd"),
        dataEndDate:investmentsInfo.dataEndDate?.format("yyyy-MM-dd")
    )
  }    
        
    def index = { println "I don't know what to do"}
}
