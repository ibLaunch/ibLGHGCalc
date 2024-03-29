// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

//--For securing application

import grails.plugins.springsecurity.SecurityConfigType

//grails.plugins.springsecurity.securityConfigType = grails.plugins.springsecurity.SecurityConfigType.Requestmap
//grails.plugins.springsecurity.securityConfigType = 'Requestmap'
//grails.plugins.springsecurity.securityConfigType=SecurityConfigType.Requestmap
grails.plugins.springsecurity.securityConfigType=SecurityConfigType.InterceptUrlMap

grails.plugins.springsecurity.interceptUrlMap = [
    '/stationaryCombustion.gsp':         ['ROLE_USER','ROLE_ADMIN'],
    '/fileUpload'              :         ['ROLE_USER','ROLE_ADMIN'],
    '/reports'                 :         ['ROLE_USER','ROLE_ADMIN'],
    '/*.pdf'                   :         ['ROLE_USER','ROLE_ADMIN'],
    '/j_spring_security_switch_user':    ['ROLE_SWITCH_USER', 'IS_AUTHENTICATED_FULLY'],
    '/aclclass/**'              :         ['ROLE_ADMIN'],
    '/aclentry/**'              :         ['ROLE_ADMIN'],
    '/aclobjectidentity/**'     :         ['ROLE_ADMIN'],
    '/aclsid/**'                :         ['ROLE_ADMIN'],
    '/errors/**'                :         ['permitAll'],
    '/persistentlogin/**'       :         ['ROLE_ADMIN'],
    '/registrationcode/**'      :         ['ROLE_ADMIN'],
    '/requestmap/**'            :         ['ROLE_ADMIN'],
    '/role/**'                  :         ['ROLE_ADMIN'],
    '/securityinfo/**'          :         ['ROLE_ADMIN'],
    '/user/**'                  :         ['ROLE_ADMIN'],
    '/'                         :         ['ROLE_USER','ROLE_ADMIN']
    //'/**'                        :         ['ROLE_ADMIN']
    //'/**'                    :         ['IS_AUTHENTICATED_ANONYMOUSLY']
]

//--Allow ROLE_SWITCH_USER to switch user as someone else.
grails.plugins.springsecurity.useSwitchUserFilter = true

//-- Fail on error if validation fails while saving
grails.gorm.failOnError = true

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        //grails.serverURL = "http://localhost:8080/${appName}"
        //basePath = "/opt/tomcat7/webapps/ROOT"
        //jasper.dir.reports = '/opt/tomcat7/webapps/ROOT/reports'        
        //jasper.dir.reports = '/var/lib/tomcat6/webapps/ROOT/reports'
        //jasper.dir.reports = '/tmp/reports'
        
        //--rackspace
        //jasper.dir.reports = "/usr/share/apache-tomcat-6.0.32/webapps/ibLGHGCalc/reports"
        //basePath = "/usr/share/apache-tomcat-6.0.32/webapps/ibLGHGCalc"
        jasper.dir.reports = "/home/iblaunch/reports"
        basePath = "/home/iblaunch"
        //jasperReportFileName = "ghgReportProdDB.jrxml"
        jasperReportFileName = "ghgReportDevlDB_NEW_20120214_v2_PROD.jrxml"
        termsConditionsFileName = "iGreenLaunchTermsConditions.pdf"
    }
    development {
        //grails.serverURL = "http://localhost:8080/${appName}"
        grails.serverURL = "http://localhost:8080/"
        basePath = "C:/GHG/ibLGHGCalc"
        //jasperReportFileName = "ghgReportDevlDB_NEW.jasper"
        //jasperReportFileName = "ghgReportDevlDB_NEW_20120210.jasper"
        jasperReportFileName = "ghgReportDevlDB_NEW_20120214_v2.jasper"
        jasper.dir.reports = 'C:/GHG/ibLGHGCalc/reports'
        termsConditionsFileName = "iGreenLaunchTermsConditions.pdf"
    }
    test {
        //grails.serverURL = "http://localhost:8080/${appName}"
        grails.serverURL = "http://localhost:8080/"
        basePath = "C:/GHG/ibLGHGCalc"
        jasperReportFileName = "ghgReportProdDB.jrxml"
        jasper.dir.reports = 'C:/GHG/ibLGHGCalc/reports'
        termsConditionsFileName = "iGreenLaunchTermsConditions.pdf"
        //-this is a git test        
        //jasper.dir.reports = '/var/lib/tomcat6/webapps/ROOT/reports'
        //jasper.dir.reports = '/tmp/reports'
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'

    appenders {
        //file name:'file'
        console name: "stdout", threshold: org.apache.log4j.Level.INFO
    }
    root {
        //debug 'stdout'
        additivity = true
    }

}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'org.ibLGHGCalc.SecUser'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'org.ibLGHGCalc.SecUserSecRole'
grails.plugins.springsecurity.authority.className = 'org.ibLGHGCalc.SecRole'
//SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl = '/stationaryCombustion.gsp'
SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl = '/login/auth'
grails.plugins.springsecurity.logout.afterLogoutUrl='/login/auth'
grails.plugins.springsecurity.failureHandler.defaultFailureUrl='/login/authfail?login_error=1'

grails {
   mail {
     host = "smtp.gmail.com"
     port = 465
     //username = "Hemant.Bundele@gmail.com"
     //password = "???????"
     username = "Hemant@ibLaunchEnergy.com"
     password = "hemant123"
     props = ["mail.smtp.auth":"true",
              "mail.smtp.socketFactory.port":465,
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]
   }
}

//grails.plugins.springsecurity.ui.register.emailBody = 'This is a test body'
grails.plugins.springsecurity.ui.register.emailFrom = 'Hemant@ibLaunchEnergy.com'
grails.plugins.springsecurity.ui.register.emailSubject = 'Registration'

def requestContextPath = request.contextPath
def registrationToken = registrationCode.token
def url = "<link>${requestContextPath}/register/verifyRegistration?t=${registrationToken}</link>"
//def username = registrationCode.username

//grails.plugins.springsecurity.ui.register.emailBody = 'Hi $user.firstName \\n You (or someone pretending to be you) created an account with this email address. \\nIf you made the request, please <a href="$url">Click here</a> to finish the registration'

grails.plugins.springsecurity.ui.register.emailBody =
'''\
Hi $user.firstName,<br/>
<br/>
Welcome to iGreenLaunch.com!
<br/>
<br/>
Please allow us one business day to activate your account. We will get back to you as we activate your account. <br/>
<br/>
Thank you very much for your interest. <br/>
<br/>
Best Regards! <br/>
iGreenLaunch Team <br/>
'''

//-Code below in-activated to allow manual activation of the user
/*
grails.plugins.springsecurity.ui.register.emailBody =
'''\
Hi $user.firstName,<br/>
<br/>
Welcome to iGreenLaunch.com!
<br/>
You (or someone pretending to be you) created an account with this email address.<br/>
<br/>
If you made the request, please click <a href="$url">Launch</a> to finish the registration.
'''
*/
//spring.security.ui.login.signin = "Hello My Friend"