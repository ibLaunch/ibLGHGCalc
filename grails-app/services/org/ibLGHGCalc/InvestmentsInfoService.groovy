package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class InvestmentsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    InvestmentsInfo findInvestmentsInfo(Long id) {
      InvestmentsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def InvestmentsInfo[] findInvestmentsInfos(Map<String, String> parameters) {
     
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return InvestmentsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in investmentsInfoListService.findinvestmentsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def investmentsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Investments sources which are within the inventory year
      int i = 0
      theOrganization?.investmentsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            investmentsInfoList[i] = it
            i++
         }
      }
      return investmentsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def InvestmentsInfo[] getInvestmentsInfos(Map<String, String> parameters) {
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.investmentsInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def InvestmentsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      InvestmentsInfo theInvestmentsInfo = InvestmentsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      String emissionsType //= "Investments Emission" //Method
      //String optionalEmissions  //Calculated
            
      if (parameters.investmentType.equals("Debt without known use of proceeds")|| 
           parameters.investmentType.equals("Managed investments and client services")||
           parameters.investmentType.equals("Other investment category")) {           
           
           emissionsType = "Optional "+ "Investments Emission"
           //emissionsType = "Upstream Leased Assets Emission"     
           
      } else {
           emissionsType = "Investments Emission"
           //emissionsType = "Downstream Leased Assets Emission"
      }
      
      def emissions =[:]
      
      if (!theInvestmentsInfo) {
        println "theInvestmentsInfo is Null"
        theInvestmentsInfo = new InvestmentsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theInvestmentsInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theInvestmentsInfo is Not Null"
        theInvestmentsInfo.emissionsDetailsList.each{
          EmissionsDetails theEmissionsDetails = it
          emissions = calculateEmissions(parameters, programType, emissionsType)
          
          theEmissionsDetails.combinedEmissions = emissions.get("combinedEmissions").toDouble()		 
          theEmissionsDetails.anticipatedLifetimeProjectInvestmentsEmissions = emissions.get("anticipatedLifetimeProjectInvestmentsEmissions").toDouble()		 
          theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions").toDouble()
          theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions").toDouble()
          theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions").toDouble()
          theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions").toDouble()

          theEmissionsDetails.combinedEmissionsUnit = emissions.get("combinedEmissionsUnit")		
          theEmissionsDetails.anticipatedLifetimeProjectInvestmentsEmissionsUnit = emissions.get("anticipatedLifetimeProjectInvestmentsEmissionsUnit")		
          theEmissionsDetails.CO2EmissionsUnit = emissions.get("CO2EmissionsUnit")
          theEmissionsDetails.biomassCO2EmissionsUnit = emissions.get("biomassCO2EmissionsUnit")
          theEmissionsDetails.CH4EmissionsUnit = emissions.get("CH4EmissionsUnit")
          theEmissionsDetails.N2OEmissionsUnit = emissions.get("N2OEmissionsUnit")

          theEmissionsDetails.emissionsType = emissions.get("emissionsType")
          theEmissionsDetails.programType = emissions.get("programType")
        }        
      }
      
      Date dataBeginDate = new Date().parse("yyyy-MM-dd", parameters.dataBeginDate)
      Date dataEndDate = new Date().parse("yyyy-MM-dd", parameters.dataEndDate)
            
      theInvestmentsInfo.dataBeginDate = dataBeginDate
      theInvestmentsInfo.dataEndDate = dataEndDate   
      
      theInvestmentsInfo.sourceDescription=parameters.sourceDescription
      theInvestmentsInfo.investmentType=parameters.investmentType
        
      switch (parameters.methodType){
        case ["Investment Specific Method"] :             
              theInvestmentsInfo.scope1Emissions=parameters.scope1Emissions?.toDouble()
              theInvestmentsInfo.scope1Emissions_Unit=parameters.scope1Emissions_Unit      
              theInvestmentsInfo.scope2Emissions=parameters.scope2Emissions?.toDouble()
              theInvestmentsInfo.scope2Emissions_Unit=parameters.scope2Emissions_Unit
              theInvestmentsInfo.percentShareOfInvestment=parameters.percentShareOfInvestment?.toDouble()
              
              if (parameters.investmentType.equals("Project Finance")){
                  theInvestmentsInfo.anticipatedLifetimeScope1Emissions=parameters.anticipatedLifetimeScope1Emissions?.toDouble()
                  theInvestmentsInfo.anticipatedLifetimeScope1Emissions_Unit=parameters.anticipatedLifetimeScope1Emissions_Unit      
                  theInvestmentsInfo.anticipatedLifetimeScope2Emissions=parameters.anticipatedLifetimeScope2Emissions?.toDouble()
                  theInvestmentsInfo.anticipatedLifetimeScope2Emissions_Unit=parameters.anticipatedLifetimeScope2Emissions_Unit
              }
            
         break
        case ["Economic Data Method"] :              
                  theInvestmentsInfo.investmentSector=parameters.investmentSector      
                  theInvestmentsInfo.investmentAmount=parameters.investmentAmount?.toDouble()      
                  theInvestmentsInfo.investmentAmount_Unit=parameters.investmentAmount_Unit
                  theInvestmentsInfo.EF_SectorSpecific=parameters.EF_SectorSpecific?.toDouble()
                  theInvestmentsInfo.EF_SectorSpecific_Unit=parameters.EF_SectorSpecific_Unit
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theInvestmentsInfo.userNotesOnData=parameters.userNotesOnData
      theInvestmentsInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theInvestmentsInfo.organization = theOrganization
      
      //--Save the user reference
      theInvestmentsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theInvestmentsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theInvestmentsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theInvestmentsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theInvestmentsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //InvestmentsInfo.get(parameters.id)?.delete()
      def theInvestmentsInfo = InvestmentsInfo.get(parameters.id)

      if (theInvestmentsInfo){
        delete(theInvestmentsInfo)
      }
      else {
          println "No theInvestmentsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theInvestmentsInfo, delete) or hasPermission(#theInvestmentsInfo, admin)")
   void delete(InvestmentsInfo theInvestmentsInfo) {
      theInvestmentsInfo.delete()
      aclUtilService.deleteAcl theInvestmentsInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0
        Double anticipatedLifetimeProjectInvestmentsEmissions =0
        println "I am in..............calculateEmissions"
        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if (programType.equals("US EPA")) {
            println "I am in..............if program type"
            switch (parameters.methodType){
                case ["Investment Specific Method"] :
                
                     combinedEmissions= ( parameters.scope1Emissions?.toDouble() +
                                        parameters.scope2Emissions?.toDouble()  ) *
                                        parameters.percentShareOfInvestment?.toDouble()
                
                     if (parameters.investmentType.equals("Project Finance")){
                        anticipatedLifetimeProjectInvestmentsEmissions = ( parameters.anticipatedLifetimeScope1Emissions?.toDouble() +
                                        parameters.anticipatedLifetimeScope2Emissions?.toDouble()  ) *
                                        parameters.percentShareOfInvestment?.toDouble()
                      }                                                                                        
                     break

                case ["Economic Data Method"] :                
                     combinedEmissions= parameters.investmentAmount?.toDouble()   *
                                        parameters.EF_SectorSpecific?.toDouble()
                     break

                default:                 
                     println "In-correct Method Type for Info service"                        
            } // Switch                        
         }  // if statement
         
         emissions.put("combinedEmissions", combinedEmissions)		 
         emissions.put("anticipatedLifetimeProjectInvestmentsEmissions", anticipatedLifetimeProjectInvestmentsEmissions)		 
         emissions.put("CO2Emissions", 0)
         emissions.put("biomassCO2Emissions", 0)
         emissions.put("CH4Emissions", 0)
         emissions.put("N2OEmissions", 0)

         emissions.put("combinedEmissionsUnit", "Kg")
         emissions.put("anticipatedLifetimeProjectInvestmentsEmissionsUnit", "Kg")
         emissions.put("CO2EmissionsUnit", "lb")
         emissions.put("biomassCO2EmissionsUnit", "lb")
         emissions.put("CH4EmissionsUnit", "lb")
         emissions.put("N2OEmissionsUnit", "lb")

         emissions.put("emissionsType", emissionsType)
         emissions.put("programType", programType)
         
         println "I finished calculating emission details in purchased Energy"
         return emissions        
    }                
}
