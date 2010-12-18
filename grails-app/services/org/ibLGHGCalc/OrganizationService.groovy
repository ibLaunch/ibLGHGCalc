package org.ibLGHGCalc

class OrganizationService {

    static transactional = true

    Organization findOrganization(Long id) {
      Organization.get(id)
    }

    def Organization[] findOrganizations() {
      Organization.list()
    }

    def Organization save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      def theOrganization = Organization.get(parameters.id)
      if (!theOrganization) {
        theOrganization = new Organization()
      }
      theOrganization.properties = parameters;

      theOrganization.save()
    }

    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      Organization.get(parameters.id)?.delete()
    }
    
}
