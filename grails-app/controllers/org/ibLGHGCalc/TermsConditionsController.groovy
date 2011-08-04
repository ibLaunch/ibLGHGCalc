package org.ibLGHGCalc

class TermsConditionsController {
    def grailsApplication
    
    def index = { }

    def serveTermsConditionFile = {
       //String fileName="iGreenLaunchTermsConditions.pdf"
       
       def fileName=grailsApplication.config.termsConditionsFileName      
       def basePath=grailsApplication.config.basePath
        
       println "I am terms ....."
       if (fileName){
            def rFile = new File(basePath+"/data/"+fileName)                 
            //response.setContentType("application/x-downloads");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName)
            response.outputStream << rFile.readBytes()
        } 
        else {       
            log.error e
            println "Report not Generated!"
            response.outputStream << "Sorry about incovenience, Terms and Conditions not found, please contact us at support@ibLaunchEnergy.com"
        }
     }         
}
