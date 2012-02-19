package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

class OrganizationService {

    static transactional = true
    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasPermission(#id, 'org.ibLGHGCalc.Organization', read) or hasPermission(#id, 'org.ibLGHGCalc.Organization', admin)")
    Organization findOrganization(Long id) {
      Organization.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def Organization[] findOrganization(Map<String, String> parameters) {
      Organization.findByOrganizationName(parameters.organizationName)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def Organization[] findOrganizations() {
      Organization.list()
    }


    @Transactional
    //@PreAuthorize("hasPermission(#orzanitationId, write) or hasPermission(#orzanitationId, admin)")
    @PreAuthorize("hasRole('ROLE_USER')")
    def Organization save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters
      
      def theOrganization = Organization.get(parameters.id)
      if (!theOrganization) {
        theOrganization = new Organization()
      }
      //theOrganization.properties = parameters;

      //-convert the Date to proper format and re-assign it to theOrganization
      Date currentInventoryBeginDate = new Date().parse('yyyy-MM-dd', parameters.currentInventoryBeginDate)
      Date currentInventoryEndDate = new Date().parse('yyyy-MM-dd', parameters.currentInventoryEndDate)
      println "currentInventoryBeginDate : " + currentInventoryBeginDate
      println "currentInventoryEndDate : " + currentInventoryEndDate
      theOrganization.currentInventoryBeginDate = currentInventoryBeginDate
      theOrganization.currentInventoryEndDate = currentInventoryEndDate

      theOrganization.organizationName= parameters.organizationName
      theOrganization.organizationStreetAddress1= parameters.organizationStreetAddress1
      theOrganization.organizationStreetAddress2= parameters.organizationStreetAddress2
      theOrganization.organizationCity= parameters.organizationCity
      theOrganization.organizationState= parameters.organizationState
      theOrganization.organizationZipCode= parameters.organizationZipCode
      theOrganization.organizationCountry= parameters.organizationCountry
      theOrganization.organizationWebsite= parameters.organizationWebsite
      theOrganization.organizationHQ= parameters.organizationHQ
      theOrganization.pointOfContact= parameters.pointOfContact
      theOrganization.consolidationApproach= parameters.consolidationApproach

      //aclUtilService.addPermission(theOrganization, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)
      println "springSecurityService.authentication.name : " + springSecurityService.authentication.name
      theOrganization.save()
    }

    @Transactional
    @PreAuthorize("hasPermission(#orzanitationId, delete) or hasPermission(#orzanitationId, admin)")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      Organization.get(parameters.id)?.delete()
    }
    
}
