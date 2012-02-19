package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class PurchasedEnergyInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    PurchasedEnergyInfo findPurchasedEnergyInfo(Long id) {
      PurchasedEnergyInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def PurchasedEnergyInfo[] findPurchasedEnergyInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedEnergyInfoList
      //PurchasedEnergyInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return PurchasedEnergyInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in purchasedEnergyInfoListService.findpurchasedEnergyInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def purchasedEnergyInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Product sources which are within the inventory year
      int i = 0
      theOrganization?.purchasedEnergyInfoList?.each {
         //---using actvityType instead of methodType
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.activityType.equals(parameters.activityType))) {
            purchasedEnergyInfoList[i] = it
            i++
         }
      }
      return purchasedEnergyInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def PurchasedEnergyInfo[] getPurchasedEnergyInfos(Map<String, String> parameters) {
      //PurchasedEnergyInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedEnergyInfoList
      //PurchasedEnergyInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def PurchasedEnergyInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      PurchasedEnergyInfo thePurchasedEnergyInfo = PurchasedEnergyInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Product"
      String emissionsType = parameters.activityType //Method

      def emissions =[:]

      
      if (!thePurchasedEnergyInfo) {
        println "thePurchasedEnergyInfo is Null"
        thePurchasedEnergyInfo = new PurchasedEnergyInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        thePurchasedEnergyInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "thePurchasedEnergyInfo is Not Null"
        thePurchasedEnergyInfo.emissionsDetailsList.each{
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
            
      thePurchasedEnergyInfo.dataBeginDate = dataBeginDate
      thePurchasedEnergyInfo.dataEndDate = dataEndDate   
      thePurchasedEnergyInfo.activityType=parameters.activityType
      thePurchasedEnergyInfo.fuelType=parameters.fuelType
      thePurchasedEnergyInfo.energyType=parameters.energyType
      thePurchasedEnergyInfo.sourceDescription=parameters.sourceDescription
      thePurchasedEnergyInfo.supplierName=parameters.supplierName
      thePurchasedEnergyInfo.supplierContact=parameters.supplierContact
      
      thePurchasedEnergyInfo.energyPurchased=parameters.energyPurchased?.toDouble()
      thePurchasedEnergyInfo.energyPurchased_Unit=parameters.energyPurchased_Unit      
      //thePurchasedEnergyInfo.flag_upstreamEF=parameters.flag_upstreamEF
      thePurchasedEnergyInfo.flag_upstreamEF=parameters.flag_upstreamEF.equals("true")? true: false
      thePurchasedEnergyInfo.EF_UpstreamEnergy=parameters.EF_UpstreamEnergy?.toDouble()
      thePurchasedEnergyInfo.EF_UpstreamEnergy_Unit=parameters.EF_UpstreamEnergy_Unit
      thePurchasedEnergyInfo.cradleToGate_EF_Energy=parameters.cradleToGate_EF_Energy?.toDouble()
      thePurchasedEnergyInfo.cradleToGate_EF_Energy_Unit=parameters.cradleToGate_EF_Energy_Unit
      thePurchasedEnergyInfo.combustion_EF_Energy=parameters.combustion_EF_Energy?.toDouble()
      thePurchasedEnergyInfo.combustion_EF_Energy_Unit=parameters.combustion_EF_Energy_Unit
      
      thePurchasedEnergyInfo.transAndDistDataType=parameters.transAndDistDataType
      thePurchasedEnergyInfo.transAndDistLossRate=parameters.transAndDistLossRate?.toDouble()        
      thePurchasedEnergyInfo.scope2EmissionsOfEnergyUse=parameters.scope2EmissionsOfEnergyUse?.toDouble()
      thePurchasedEnergyInfo.scope2EmissionsOfEnergyUse_Unit=parameters.scope2EmissionsOfEnergyUse_Unit
        
      thePurchasedEnergyInfo.userNotesOnData=parameters.userNotesOnData
      thePurchasedEnergyInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      thePurchasedEnergyInfo.organization = theOrganization
      
      //--Save the user reference
      thePurchasedEnergyInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      thePurchasedEnergyInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      thePurchasedEnergyInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(thePurchasedEnergyInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return thePurchasedEnergyInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //PurchasedEnergyInfo.get(parameters.id)?.delete()
      def thePurchasedEnergyInfo = PurchasedEnergyInfo.get(parameters.id)

      if (thePurchasedEnergyInfo){
        delete(thePurchasedEnergyInfo)
      }
      else {
          println "No thePurchasedEnergyInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#thePurchasedEnergyInfo, delete) or hasPermission(#thePurchasedEnergyInfo, admin)")
   void delete(PurchasedEnergyInfo thePurchasedEnergyInfo) {
      thePurchasedEnergyInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl thePurchasedEnergyInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0
        println "I am in..............calculateEmissions"
        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if (programType.equals("US EPA")) {
            println "I am in..............if program type"
            switch (parameters.activityType){
                case ["Upstream Emissions Of Purchased Fuels",
                      "Upstream Emissions Of Purchased Energy",
                      "Generation Of Purchased Energy That Is Sold To End Users"] :
                     
                     if ((parameters.energyPurchased?.toDouble() > 0) && (parameters.EF_UpstreamEnergy?.toDouble() > 0)){             

                        combinedEmissions= parameters.energyPurchased.toDouble() * parameters.EF_UpstreamEnergy.toDouble()
                        println "I am in..............1"
                     }                              
                     else if ((parameters.energyPurchased?.toDouble() > 0) && (parameters.cradleToGate_EF_Energy?.toDouble() > 0)  &&
                         (parameters.combustion_EF_Energy?.toDouble() > 0)){             

                        combinedEmissions= parameters.energyPurchased.toDouble() *
                                           (parameters.cradleToGate_EF_Energy.toDouble() - parameters.combustion_EF_Energy.toDouble())
                        println "I am in..............1.1"
                     }
                     
                     break

                case ["Transmission & Distribution Losses"] :                
                     if ((parameters.energyPurchased?.toDouble() > 0) && (parameters.EF_UpstreamEnergy?.toDouble() > 0) &&                       
                        (parameters.transAndDistLossRate?.toDouble() > 0)){             

                        combinedEmissions= parameters.energyPurchased.toDouble() * parameters.EF_UpstreamEnergy.toDouble() *
                                           parameters.transAndDistLossRate.toDouble()
                        println "I am in..............2"
                     }                     
                     else if ((parameters.energyPurchased?.toDouble() > 0) && (parameters.cradleToGate_EF_Energy?.toDouble() > 0)  &&
                         (parameters.combustion_EF_Energy?.toDouble() > 0) && (parameters.transAndDistLossRate?.toDouble() > 0)){             

                        combinedEmissions= parameters.energyPurchased.toDouble() *
                                           (parameters.cradleToGate_EF_Energy.toDouble() - parameters.combustion_EF_Energy.toDouble()) *
                                           parameters.transAndDistLossRate.toDouble()
                        println "I am in..............2.1"
                     }
                     else if ((parameters.scope2EmissionsOfEnergyUse?.toDouble() > 0) &&(parameters.transAndDistLossRate?.toDouble() > 0)){             

                        combinedEmissions= parameters.scope2EmissionsOfEnergyUse.toDouble() * 
                                           parameters.transAndDistLossRate.toDouble()
                        println "I am in..............2.2"
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
