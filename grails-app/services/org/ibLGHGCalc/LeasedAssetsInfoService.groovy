package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class LeasedAssetsInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    LeasedAssetsInfo findLeasedAssetsInfo(Long id) {
      LeasedAssetsInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def LeasedAssetsInfo[] findLeasedAssetsInfos(Map<String, String> parameters) {
     
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return LeasedAssetsInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in leasedAssetsInfoListService.findleasedAssetsInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def leasedAssetsInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased LeasedAssets sources which are within the inventory year
      int i = 0
      theOrganization?.leasedAssetsInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            leasedAssetsInfoList[i] = it
            i++
         }
      }
      return leasedAssetsInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def LeasedAssetsInfo[] getLeasedAssetsInfos(Map<String, String> parameters) {
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.leasedAssetsInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def LeasedAssetsInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      LeasedAssetsInfo theLeasedAssetsInfo = LeasedAssetsInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      String emissionsType //= "LeasedAssets Emission" //Method
      String streamType  //Calculated
      
      def theOrganization =  Organization.get(parameters.organizationId)
      
      if ((theOrganization.consolidationApproach.equals("Equity share")|| 
           theOrganization.consolidationApproach.equals("Financial control")) 
           &&
           (parameters.typeOfLeasingArrangement.equals("Operating Lease"))) {           
           
           streamType = "Upstream"
           //emissionsType = "Upstream Leased Assets Emission"     
           
      } else {
           streamType = "Downstream"
           //emissionsType = "Downstream Leased Assets Emission"
      }
        

      emissionsType = streamType + " Leased Assets Emission"
      
      def emissions =[:]
      
      if (!theLeasedAssetsInfo) {
        println "theLeasedAssetsInfo is Null"
        theLeasedAssetsInfo = new LeasedAssetsInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theLeasedAssetsInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theLeasedAssetsInfo is Not Null"
        theLeasedAssetsInfo.emissionsDetailsList.each{
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
            
      theLeasedAssetsInfo.dataBeginDate = dataBeginDate
      theLeasedAssetsInfo.dataEndDate = dataEndDate   
      
      theLeasedAssetsInfo.sourceDescription=parameters.sourceDescription
      theLeasedAssetsInfo.streamType=streamType      // calculated
      theLeasedAssetsInfo.typeOfLeasingArrangement=parameters.typeOfLeasingArrangement
      theLeasedAssetsInfo.lessorOrLessee=parameters.lessorOrLessee
      theLeasedAssetsInfo.leasedAssetName=parameters.leasedAssetName
        
      switch (parameters.methodType){
        case ["Site Specific Method - Leased Assets"] :             
              theLeasedAssetsInfo.scope1EmissionsOfLeasedAsset=parameters.scope1EmissionsOfLeasedAsset?.toDouble()
              theLeasedAssetsInfo.scope1EmissionsOfLeasedAsset_Unit=parameters.scope1EmissionsOfLeasedAsset_Unit      
              theLeasedAssetsInfo.scope2EmissionsOfLeasedAsset=parameters.scope2EmissionsOfLeasedAsset?.toDouble()
              theLeasedAssetsInfo.scope2EmissionsOfLeasedAsset_Unit=parameters.scope2EmissionsOfLeasedAsset_Unit

         break
        case ["Average Data Method - Leased Assets"] :              
              theLeasedAssetsInfo.flag_floorSpaceData=parameters.flag_floorSpaceData.equals("true")? true: false

              if (parameters.flag_floorSpaceData.equals("true")){
                  //theLeasedAssetsInfo.emissionsFromCommercialAsset=parameters.emissionsFromCommercialAsset?.toDouble()
                  //theLeasedAssetsInfo.emissionsFromCommercialAsset_Unit=parameters.emissionsFromCommercialAsset_Unit      
                  theLeasedAssetsInfo.floorSpace=parameters.floorSpace?.toDouble()
                  theLeasedAssetsInfo.floorSpace_Unit=parameters.floorSpace_Unit
                  theLeasedAssetsInfo.average_EF=parameters.average_EF?.toDouble()
                  theLeasedAssetsInfo.average_EF_Unit=parameters.average_EF_Unit    
              } else {
                  //theLeasedAssetsInfo.emissionsFromOtherAsset=parameters.emissionsFromOtherAsset?.toDouble()
                  //theLeasedAssetsInfo.emissionsFromOtherAsset_Unit=parameters.emissionsFromOtherAsset_Unit      
                  theLeasedAssetsInfo.numberOfbuildingsOrAssetTypes=parameters.numberOfbuildingsOrAssetTypes?.toDouble()      
                  theLeasedAssetsInfo.averageEmissionsPerBuildingOrAssetType=parameters.averageEmissionsPerBuildingOrAssetType?.toDouble()
                  theLeasedAssetsInfo.averageEmissionsPerBuildingOrAssetType_Unit=parameters.averageEmissionsPerBuildingOrAssetType_Unit
              }
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theLeasedAssetsInfo.userNotesOnData=parameters.userNotesOnData
      theLeasedAssetsInfo.methodType=parameters.methodType      
            
      //def theOrganization =  Organization.get(parameters.organizationId)
      theLeasedAssetsInfo.organization = theOrganization
      
      //--Save the user reference
      theLeasedAssetsInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theLeasedAssetsInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theLeasedAssetsInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theLeasedAssetsInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theLeasedAssetsInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //LeasedAssetsInfo.get(parameters.id)?.delete()
      def theLeasedAssetsInfo = LeasedAssetsInfo.get(parameters.id)

      if (theLeasedAssetsInfo){
        delete(theLeasedAssetsInfo)
      }
      else {
          println "No theLeasedAssetsInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theLeasedAssetsInfo, delete) or hasPermission(#theLeasedAssetsInfo, admin)")
   void delete(LeasedAssetsInfo theLeasedAssetsInfo) {
      theLeasedAssetsInfo.delete()
      aclUtilService.deleteAcl theLeasedAssetsInfo
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
                case ["Site Specific Method - Leased Assets"] :                                          
                     combinedEmissions= parameters.scope1EmissionsOfLeasedAsset?.toDouble() +
                                        parameters.scope2EmissionsOfLeasedAsset?.toDouble()                      
                     break

                case ["Average Data Method - Leased Assets"] :                
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
