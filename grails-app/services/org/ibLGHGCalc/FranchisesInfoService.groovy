package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class FranchisesInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    FranchisesInfo findFranchisesInfo(Long id) {
      FranchisesInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def FranchisesInfo[] findFranchisesInfos(Map<String, String> parameters) {
     
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return FranchisesInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in franchisesInfoListService.findfranchisesInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def franchisesInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Franchises sources which are within the inventory year
      int i = 0
      theOrganization?.franchisesInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            franchisesInfoList[i] = it
            i++
         }
      }
      return franchisesInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def FranchisesInfo[] getFranchisesInfos(Map<String, String> parameters) {
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.franchisesInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def FranchisesInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      FranchisesInfo theFranchisesInfo = FranchisesInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      String emissionsType //= "Franchises Emission" //Method      
      
      emissionsType = "Franchises Emission"
      
      def emissions =[:]
      
      if (!theFranchisesInfo) {
        println "theFranchisesInfo is Null"
        theFranchisesInfo = new FranchisesInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theFranchisesInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theFranchisesInfo is Not Null"
        theFranchisesInfo.emissionsDetailsList.each{
          EmissionsDetails theEmissionsDetails = it
          emissions = calculateEmissions(parameters, programType, emissionsType)
          
          theEmissionsDetails.combinedEmissions = emissions.get("combinedEmissions").toDouble()		 
          theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions").toDouble()
          theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions").toDouble()
          theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions").toDouble()
          theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions").toDouble()

          theEmissionsDetails.combinedEmissionsUnit = emissions.get("combinedEmissionsUnit")		
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
            
      theFranchisesInfo.dataBeginDate = dataBeginDate
      theFranchisesInfo.dataEndDate = dataEndDate   
      
      theFranchisesInfo.sourceDescription=parameters.sourceDescription
      theFranchisesInfo.franchiseName=parameters.franchiseName      // calculated
        
      switch (parameters.methodType){
        case ["Franchise Specific Method"] :             
              theFranchisesInfo.scope1EmissionsOfFranchise=parameters.scope1EmissionsOfFranchise?.toDouble()
              theFranchisesInfo.scope1EmissionsOfFranchise_Unit=parameters.scope1EmissionsOfFranchise_Unit      
              theFranchisesInfo.scope2EmissionsOfFranchise=parameters.scope2EmissionsOfFranchise?.toDouble()
              theFranchisesInfo.scope2EmissionsOfFranchise_Unit=parameters.scope2EmissionsOfFranchise_Unit

         break
        case ["Average Data Method"] :              
              theFranchisesInfo.flag_floorSpaceData=parameters.flag_floorSpaceData.equals("true")? true: false

              if (parameters.flag_floorSpaceData.equals("true")){
                  //theFranchisesInfo.emissionsFromCommercialAsset=parameters.emissionsFromCommercialAsset?.toDouble()
                  //theFranchisesInfo.emissionsFromCommercialAsset_Unit=parameters.emissionsFromCommercialAsset_Unit      
                  theFranchisesInfo.floorSpace=parameters.floorSpace?.toDouble()
                  theFranchisesInfo.floorSpace_Unit=parameters.floorSpace_Unit
                  theFranchisesInfo.average_EF=parameters.average_EF?.toDouble()
                  theFranchisesInfo.average_EF_Unit=parameters.average_EF_Unit    
              } else {
                  theFranchisesInfo.buildingOrAssetName=parameters.buildingOrAssetName      
                  theFranchisesInfo.numberOfbuildingsOrAssetTypes=parameters.numberOfbuildingsOrAssetTypes?.toDouble()      
                  theFranchisesInfo.averageEmissionsPerBuildingOrAssetType=parameters.averageEmissionsPerBuildingOrAssetType?.toDouble()
                  theFranchisesInfo.averageEmissionsPerBuildingOrAssetType_Unit=parameters.averageEmissionsPerBuildingOrAssetType_Unit
              }
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theFranchisesInfo.userNotesOnData=parameters.userNotesOnData
      theFranchisesInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theFranchisesInfo.organization = theOrganization
      
      //--Save the user reference
      theFranchisesInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theFranchisesInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theFranchisesInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theFranchisesInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theFranchisesInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //FranchisesInfo.get(parameters.id)?.delete()
      def theFranchisesInfo = FranchisesInfo.get(parameters.id)

      if (theFranchisesInfo){
        delete(theFranchisesInfo)
      }
      else {
          println "No theFranchisesInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theFranchisesInfo, delete) or hasPermission(#theFranchisesInfo, admin)")
   void delete(FranchisesInfo theFranchisesInfo) {
      theFranchisesInfo.delete()
      aclUtilService.deleteAcl theFranchisesInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0
        println "I am in..............calculateEmissions"
        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if (programType.equals("US EPA")) {
            println "I am in..............if program type"
            switch (parameters.methodType){
                case ["Franchise Specific Method"] :                                          
                     combinedEmissions= parameters.scope1EmissionsOfFranchise?.toDouble() +
                                        parameters.scope2EmissionsOfFranchise?.toDouble()                      
                     break

                case ["Average Data Method"] :                
                      if (parameters.flag_floorSpaceData.equals("true")){
                          combinedEmissions= parameters.floorSpace?.toDouble() *
                                             parameters.average_EF?.toDouble()
                      } else {                         
                          combinedEmissions= parameters.numberOfbuildingsOrAssetTypes?.toDouble() *
                                             parameters.averageEmissionsPerBuildingOrAssetType?.toDouble()                    
                      }                                
                     break

                default:                 
                     println "In-correct Method Type for Info service"                        
            } // Switch                        
         }  // if statement
         
         emissions.put("combinedEmissions", combinedEmissions)		 
         emissions.put("CO2Emissions", 0)
         emissions.put("biomassCO2Emissions", 0)
         emissions.put("CH4Emissions", 0)
         emissions.put("N2OEmissions", 0)

         emissions.put("combinedEmissionsUnit", "Kg")
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
