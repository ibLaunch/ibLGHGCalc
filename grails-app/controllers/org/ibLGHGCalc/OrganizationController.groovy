package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class OrganizationController {
  def organizationService

  def list = {
    log.info "OrganizationController.list( ${params} )"
    //def organizations = organizationService.findOrganizations();
    //def organizations = organizationService.findOrganization(params);
    def organizations
    if (params.organizationName){
        organizations = organizationService.findOrganization(params);
    } else {
        organizations = organizationService.findOrganizations();
    }
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        organizations.each { theOrganization ->
          flushOrganization xml, theOrganization
        }
      }
    }
  }

  def save = {
    log.info "OrganizationController.add( ${params} )"
    def theOrganization = organizationService.save(params)
    def xml = new MarkupBuilder(response.writer)
    xml.response() {
      status(0)
      data {
        flushOrganization xml, theOrganization
      }
    }
  }

  def remove = {
    log.info "OrganizationController.remove( ${params} )"
    organizationService.remove(params)
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

  private def flushOrganization = { xml, organization ->
    xml.record(
        id: organization.id,
        organizationName: organization.organizationName,
	organizationStreetAddress1: organization.organizationStreetAddress1,
	organizationStreetAddress2: organization.organizationStreetAddress2,
	organizationCity: organization.organizationCity,
	organizationState: organization.organizationState,
	organizationZipCode: organization.organizationZipCode,
	organizationCountry: organization.organizationCountry,
	organizationWebsite: organization.organizationWebsite,
	organizationHQ: organization.organizationHQ,
	pointOfContact: organization.pointOfContact,
        currentInventoryBeginDate:organization.currentInventoryBeginDate,
        currentInventoryEndDate:organization.currentInventoryEndDate,
	dateCreated: organization.dateCreated,
	lastUpdated: organization.lastUpdated

        //stationaryCombustionInfos: organization.stationaryCombustionInfos
    )
  }
}
