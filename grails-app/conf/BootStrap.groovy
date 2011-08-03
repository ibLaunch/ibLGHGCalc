import org.ibLGHGCalc.SecRole
import org.ibLGHGCalc.SecUser
import org.ibLGHGCalc.SecUserSecRole
import org.ibLGHGCalc.Organization

//import org.springframework.security.GrantedAuthority
import org.springframework.security.core.GrantedAuthority

//import org.springframework.security.GrantedAuthorityImpl
import org.springframework.security.core.authority.GrantedAuthorityImpl

import org.springframework.security.acls.domain.BasePermission

//import org.springframework.security.context.SecurityContextHolder as SCH
import org.springframework.security.core.context.SecurityContextHolder as SCH

//import org.springframework.security.providers.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken



class BootStrap {
    def springSecurityService
    def aclUtilService

    def init = { servletContext ->
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
        def switchUserRole = SecRole.findByAuthority('ROLE_SWITCH_USER') ?: new SecRole(authority: 'ROLE_SWITCH_USER').save(failOnError: true)

        println "------------------ I am in BootStrap Init-------------------------------------------"

        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: springSecurityService.encodePassword('admin'),
                enabled: true,
                acceptTerms: true).save(flush: true)

        def userUser = SecUser.findByUsername('user') ?: new SecUser(
                username: 'user',
                password: springSecurityService.encodePassword('user'),
                enabled: true,
                acceptTerms: true).save(flush: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
        }

        if (!adminUser.authorities.contains(switchUserRole)) {
            SecUserSecRole.create adminUser, switchUserRole
        }
        
        if (!userUser.authorities.contains(userRole)) {
            SecUserSecRole.create userUser, userRole
        }

        createAdminOrganization(userUser)

        println "userRole : "+ userRole
        println "adminRole : "+ adminRole
        println "adminUser : "+ adminUser
        println "userUser : "+ userUser
        //println "theOrganization : "+ theOrganization
        //createGrants()
    }

   private void createGrants() {

      loginAsAdmin()

      try {
         // User test3 can see write orgamization ibL0, ibL1, ibL2
         def theSecUser = SecUser.findByUsername('test3')
         (0..2).each {
            def theOrganization = Organization.findByOrganizationName("ibL$it")
            aclUtilService.addPermission(theOrganization, theSecUser.username, BasePermission.ADMINISTRATION)
            
            theOrganization.stationaryCombustionInfoList.each{
                aclUtilService.addPermission(it,theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.emissionsSummaryList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.mobileCombustionInfoList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.refridgerationAirConditioningInfoList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.purchasedElectricityInfoList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.purchasedSteamInfoList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.optionalSourceInfoList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }
            //--
            theOrganization.wasteStreamCombustionInfoList.each{
                  aclUtilService.addPermission(it, theSecUser.username, BasePermission.ADMINISTRATION )
            }

         }

         /*
         // and can edit #3
         aclUtilService.addPermission(Report.findByName('report 3'),
               user.username, BasePermission.WRITE)
         // and edit and delete #4
         aclUtilService.addPermission(Report.findByName('report 4'),
               user.username, BasePermission.WRITE)
         aclUtilService.addPermission(Report.findByName('report 4'),
               user.username, BasePermission.DELETE)

         // user2 can see reports 5, 10
         user = User.findByUsername('user2')
         [5, 10].each {
            def report = Report.findByName("report $it")
            aclUtilService.addPermission(report,
                  user.username, BasePermission.READ)
         }
         */
      }
      finally {
         SCH.clearContext()
      }
   }

    // have to be authenticated as an admin to create ACLs
    private void loginAsAdmin() {
       SCH.context.authentication = new UsernamePasswordAuthenticationToken(
            'admin', 'admin',
            [new GrantedAuthorityImpl('ROLE_ADMIN')] as GrantedAuthority[])
    }

    def destroy = {
    }

    private void createAdminOrganization(SecUser defaultUser){

        loginAsAdmin()

        try {
            def theOrganization =  Organization.findByOrganizationName('GreenLaunch') ?:Organization.newInstance(organizationName: "GreenLaunch",
                                                      currentInventoryBeginDate:new Date(),
                                                      currentInventoryEndDate:new Date(),
                                                      programType:"US EPA",
                                                      pointOfContact:"user",
                                                      userList:defaultUser
                                                     ).save(flush: true)

            aclUtilService.addPermission(theOrganization, defaultUser.username, BasePermission.ADMINISTRATION)
        }
        finally {
            SCH.clearContext()
        }

    }
}
