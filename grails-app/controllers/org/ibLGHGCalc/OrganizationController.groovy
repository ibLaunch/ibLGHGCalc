package org.ibLGHGCalc

import groovy.xml.MarkupBuilder

class OrganizationController {
  def organizationService

  def list = {
    log.info "OrganizationController.list( ${params} )"
    def organizations = organizationService.findOrganizations();
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
        organizationName: organization.organizationName
        //stationaryCombustionInfos: organization.stationaryCombustionInfos
    )
  }
}
