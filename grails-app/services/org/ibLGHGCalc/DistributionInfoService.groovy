package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class DistributionInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    DistributionInfo findDistributionInfo(Long id) {
      DistributionInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def DistributionInfo[] findDistributionInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.distributionInfoList
      //DistributionInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return DistributionInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in distributionInfoListService.finddistributionInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def distributionInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Distribution sources which are within the inventory year
      int i = 0
      theOrganization?.distributionInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            distributionInfoList[i] = it
            i++
         }
      }
      return distributionInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def DistributionInfo[] getDistributionInfos(Map<String, String> parameters) {
      //DistributionInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.distributionInfoList
      //DistributionInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def DistributionInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      DistributionInfo theDistributionInfo = DistributionInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Distribution"
      String emissionsType = parameters.streamType + " Distribution Emission" //Method

      def emissions =[:]
      
      if (!theDistributionInfo) {
        println "theDistributionInfo is Null"
        theDistributionInfo = new DistributionInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theDistributionInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "theDistributionInfo is Not Null"
        theDistributionInfo.emissionsDetailsList.each{
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
            
      theDistributionInfo.dataBeginDate = dataBeginDate
      theDistributionInfo.dataEndDate = dataEndDate   
      
      theDistributionInfo.sourceDescription=parameters.sourceDescription
      theDistributionInfo.streamType=parameters.streamType      
      theDistributionInfo.serviceProviderName=parameters.serviceProviderName
      theDistributionInfo.serviceProviderContact=parameters.serviceProviderContact
      theDistributionInfo.storageFacility=parameters.storageFacility
        
      switch (parameters.methodType){
        case ["Site Specific Method - Distribution Emissions"] :
              theDistributionInfo.fuelType=parameters.fuelType              
              theDistributionInfo.fuelConsumed=parameters.fuelConsumed?.toDouble()
              theDistributionInfo.fuelConsumed_Unit=parameters.fuelConsumed_Unit      
              theDistributionInfo.EF_Fuel=parameters.EF_Fuel?.toDouble()
              theDistributionInfo.EF_Fuel_Unit=parameters.EF_Fuel_Unit

              theDistributionInfo.electricityConsumed=parameters.electricityConsumed?.toDouble()
              theDistributionInfo.electricityConsumed_Unit=parameters.electricityConsumed_Unit
              theDistributionInfo.EF_Electricity=parameters.EF_Electricity?.toDouble()
              theDistributionInfo.EF_Electricity_Unit=parameters.EF_Electricity_Unit

              theDistributionInfo.refrigerantType=parameters.refrigerantType              
              theDistributionInfo.refrigerantLeakage=parameters.refrigerantLeakage?.toDouble()
              theDistributionInfo.refrigerantLeakage_Unit=parameters.refrigerantLeakage_Unit      
              theDistributionInfo.EF_Refrigerant=parameters.EF_Refrigerant?.toDouble()
              theDistributionInfo.EF_Refrigerant_Unit=parameters.EF_Refrigerant_Unit

              theDistributionInfo.volumeOfReportingCompanysPurchasedGoods=parameters.volumeOfReportingCompanysPurchasedGoods?.toDouble()
              theDistributionInfo.volumeOfReportingCompanysPurchasedGoods_Unit=parameters.volumeOfReportingCompanysPurchasedGoods_Unit      
              theDistributionInfo.totalVolumeOfGoodsInStorageFacility=parameters.totalVolumeOfGoodsInStorageFacility?.toDouble()
              theDistributionInfo.totalVolumeOfGoodsInStorageFacility_Unit=parameters.totalVolumeOfGoodsInStorageFacility_Unit

         break
        case ["Average Data Method - Distribution Emissions"] :                
              theDistributionInfo.storedGoodsInReportingYear=parameters.storedGoodsInReportingYear?.toDouble()
              theDistributionInfo.storedGoodsInReportingYear_Unit=parameters.storedGoodsInReportingYear_Unit      
              theDistributionInfo.EF_StorageFacility=parameters.EF_StorageFacility?.toDouble()
              theDistributionInfo.EF_StorageFacility_Unit=parameters.EF_StorageFacility_Unit
              
         break

        default:                 
                 println "In-correct Method Type for Info service"                        
      } // Switch                        
        
      theDistributionInfo.userNotesOnData=parameters.userNotesOnData
      theDistributionInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      theDistributionInfo.organization = theOrganization
      
      //--Save the user reference
      theDistributionInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theDistributionInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theDistributionInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theDistributionInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theDistributionInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //DistributionInfo.get(parameters.id)?.delete()
      def theDistributionInfo = DistributionInfo.get(parameters.id)

      if (theDistributionInfo){
        delete(theDistributionInfo)
      }
      else {
          println "No theDistributionInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theDistributionInfo, delete) or hasPermission(#theDistributionInfo, admin)")
   void delete(DistributionInfo theDistributionInfo) {
      theDistributionInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theDistributionInfo
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
                case ["Site Specific Method - Distribution Emissions"] :                                          
                     combinedEmissions= ( parameters.fuelConsumed?.toDouble() *
                                        parameters.EF_Fuel?.toDouble() +
                                        parameters.electricityConsumed?.toDouble()*
                                        parameters.EF_Electricity?.toDouble()+
                                        parameters.refrigerantLeakage?.toDouble() *
                                        parameters.EF_Refrigerant?.toDouble() ) *
                                        (parameters.volumeOfReportingCompanysPurchasedGoods?.toDouble() /
                                        parameters.totalVolumeOfGoodsInStorageFacility?.toDouble())                     
                     
                     break

                case ["Average Data Method - Distribution Emissions"] :                
                    combinedEmissions= parameters.storedGoodsInReportingYear?.toDouble() *
                                       parameters.EF_StorageFacility?.toDouble()
                
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
