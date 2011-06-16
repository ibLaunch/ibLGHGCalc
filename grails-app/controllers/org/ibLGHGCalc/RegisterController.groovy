package org.ibLGHGCalc
import groovy.text.SimpleTemplateEngine

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode

import org.springframework.security.acls.domain.BasePermission

class RegisterController extends grails.plugins.springsecurity.ui.RegisterController {
	def mailService
	def saltSource
        def aclUtilService
        
	def index = {
		[command: new RegisterCommand()]
	}

	def register = { RegisterCommand command ->

		if (command.hasErrors()) {
			render view: 'index', model: [command: command]
			return
		}

		String salt = saltSource instanceof NullSaltSource ? null : command.username
		String password = springSecurityService.encodePassword(command.password, salt)
		def user = lookupUserClass().newInstance(firstName: command.firstName,lastName: command.lastName,
                                phoneNumber: command.phoneNumber,organizationName: command.organizationName,
                                email: command.email, username: command.username,
				password: password, accountLocked: true, enabled: true)
		if (!user.validate() || !user.save()) {
			// TODO
		}

//- Below is temporary fix it in future
                def theOrganization = Organization.findByOrganizationName(command.organizationName)
                if(!theOrganization){
                 theOrganization = Organization.newInstance(organizationName: command.organizationName)
                     if (!theOrganization.validate() || !theOrganization.save(flush:true)) {
			// TODO
                     }
                }
                aclUtilService.addPermission(theOrganization, user.username, BasePermission.ADMINISTRATION)

                println "theOrganization----------"+ theOrganization

                //if the organization already exists then get all the related objects and give admin permission to new user                
                theOrganization.stationaryCombustionInfoList.each {
                    //aclUtilService.addPermission(it, user.username, BasePermission.ADMINISTRATION)
                    println "it-------------" + it
                }
                
//--end of temporary code

		def registrationCode = new RegistrationCode(username: user.username).save()
		String url = generateLink('verifyRegistration', [t: registrationCode.token])

		def conf = SpringSecurityUtils.securityConfig
		def body = conf.ui.register.emailBody
		if (body.contains('$')) {
			body = evaluate(body, [user: user, url: url])
		}
		mailService.sendMail {
			to command.email
			from conf.ui.register.emailFrom
			subject conf.ui.register.emailSubject
			html body.toString()
		}

		render view: 'index', model: [emailSent: true]
	}
    
}
class RegisterCommand {

        String firstName
        String lastName
        String phoneNumber
        String organizationName

	String username
	String email
	String password
	String password2

	static constraints = {
                firstName blank: false, firstName: true
                lastName blank: false, lastName: true
                phoneNumber blank: false, phoneNumber: true
                organizationName blank: false, organizationName: true
                
		username blank: false, validator: { value, command ->
			if (value) {
				def User = AH.application.getDomainClass(
					SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
				if (User.findByUsername(value)) {
					return 'registerCommand.username.unique'
				}
			}
		}
		email blank: false, email: true
		password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator
		password2 validator: RegisterController.password2Validator
	}
}
