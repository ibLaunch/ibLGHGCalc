package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class WasteOutputFrom_T1S_InfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    WasteOutputFrom_T1S_Info findWasteOutputFrom_T1S_Info(Long id) {
      WasteOutputFrom_T1S_Info.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def WasteOutputFrom_T1S_Info[] findWasteOutputFrom_T1S_Infos(Map<String, String> parameters) {
      def thePurchasedProductInfo

      if (parameters.purchasedProductInfoId) {
          thePurchasedProductInfo = PurchasedProductInfo.get(parameters.purchasedProductInfoId)
      }else if (parameters.purchasedProductName){
          thePurchasedProductInfo = PurchasedProductInfo.findByPurchasedProductName(parameters.purchasedProductName)
      } else if (parameters.id) {
           // User has provided the id of the source, so just provide that and return from here.
           return WasteOutputFrom_T1S_Info.get(parameters.id)
      } else {
          println "-----I don't know purchasedProductInfo in wasteOutputFrom_T1S_InfoListService.findwasteOutputFrom_T1S_Infos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def wasteOutputFrom_T1S_InfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      /*
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType      
      */

      //-- Only select the Purchased Product sources which are within the inventory year
      int i = 0
      thePurchasedProductInfo?.wasteOutputFrom_T1S_InfoList?.each {
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate)) {
            wasteOutputFrom_T1S_InfoList[i] = it
            i++
         }
      }
      
      return wasteOutputFrom_T1S_InfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def WasteOutputFrom_T1S_Info[] getWasteOutputFrom_T1S_Infos(Map<String, String> parameters) {
      def thePurchasedProductInfo = PurchasedProductInfo.get(parameters.purchasedProductInfoId)
      return thePurchasedProductInfo.wasteOutputFrom_T1S_InfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def WasteOutputFrom_T1S_Info save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      println "----------------I am in WasteOutputFrom_T1S_Info --------------------"
      WasteOutputFrom_T1S_Info theWasteOutputFrom_T1S_Info = WasteOutputFrom_T1S_Info.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Product"
      //String emissionsType = "Purchased "+ parameters.purchasedProductType

      //-define emissions map
      def Map<String,String> emissions
        
      if (!theWasteOutputFrom_T1S_Info) {
        println "theWasteOutputFrom_T1S_Info is Null"
        theWasteOutputFrom_T1S_Info = new WasteOutputFrom_T1S_Info()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        //Emissions Type from the parameter
        String emissionsType = "Purchased "+ parameters.purchasedProductType

        theEmissionsDetails = calculateEmissions(parameters, programType, emissionsType)

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        theWasteOutputFrom_T1S_Info.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "theWasteOutputFrom_T1S_Info is Not Null"
        theWasteOutputFrom_T1S_Info.emissionsDetailsList.each{
          EmissionsDetails theEmissionsDetails = it

          //Emissions Type from the parameter
          String emissionsType = theEmissionsDetails.emissionsType
                
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

      theWasteOutputFrom_T1S_Info.sourceDescription=parameters.sourceDescription
      theWasteOutputFrom_T1S_Info.massOfWasteFrom_T1S_ForPurchasedProduct=parameters.massOfWasteFrom_T1S_ForPurchasedProduct?.toDouble()
      theWasteOutputFrom_T1S_Info.massOfWasteFrom_T1S_ForPurchasedProduct_Unit=parameters.massOfWasteFrom_T1S_ForPurchasedProduct_Unit
      
      theWasteOutputFrom_T1S_Info.EF_WasteActivity=parameters.EF_WasteActivity?.toDouble()
      theWasteOutputFrom_T1S_Info.EF_WasteActivity_Unit=parameters.EF_WasteActivity_Unit

      theWasteOutputFrom_T1S_Info.dataBeginDate = dataBeginDate
      theWasteOutputFrom_T1S_Info.dataEndDate = dataEndDate           
      theWasteOutputFrom_T1S_Info.userNotesOnData=parameters.userNotesOnData

      def thePurchasedProductInfo =  PurchasedProductInfo.get(parameters.purchasedProductInfoId)
      theWasteOutputFrom_T1S_Info.purchasedProductInfo = thePurchasedProductInfo
      
      //--Save the user reference
      theWasteOutputFrom_T1S_Info.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      theWasteOutputFrom_T1S_Info.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      theWasteOutputFrom_T1S_Info.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(theWasteOutputFrom_T1S_Info, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theWasteOutputFrom_T1S_Info
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //WasteOutputFrom_T1S_Info.get(parameters.id)?.delete()
      def theWasteOutputFrom_T1S_Info = WasteOutputFrom_T1S_Info.get(parameters.id)

      if (theWasteOutputFrom_T1S_Info){
        delete(theWasteOutputFrom_T1S_Info)
      }
      else {
          println "No theWasteOutputFrom_T1S_Info found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theWasteOutputFrom_T1S_Info, delete) or hasPermission(#theWasteOutputFrom_T1S_Info, admin)")
   void delete(WasteOutputFrom_T1S_Info theWasteOutputFrom_T1S_Info) {
      theWasteOutputFrom_T1S_Info.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theWasteOutputFrom_T1S_Info
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0

        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        /*
        if ( (programType.equals("US EPA")) &&
             ((emissionsType.equals("Purchased Capital Goods")||emissionsType.equals("Purchased Goods") ||emissionsType.equals("Purchased Services")))) {

            switch (parameters.methodType){
                case ["Purchased Goods and Services - Product Level Method"] :
                     break

                case ["Purchased Goods and Services - Supplier Specific Method"] :
                     if ((parameters.massOfWasteFrom_T1S_ForPurchasedProduct.toDouble() > 0) && (parameters.EF_WasteActivity.toDouble() > 0)){             
                        combinedEmissions= parameters.massOfWasteFrom_T1S_ForPurchasedProduct.toDouble() * parameters.EF_WasteActivity.toDouble()
                     }                
                     break

                case ["Purchased Goods and Services - Material Or Spend Based Method"] :
                
                     break

                default:                 
                     println "In-correct Method Type for refridgerationAirConditioningInfoservice"                        
            } // Switch
         }  // if statement
         */
         if (programType.equals("US EPA")){
             if ((parameters.massOfWasteFrom_T1S_ForPurchasedProduct.toDouble() > 0) && (parameters.EF_WasteActivity.toDouble() > 0)){             
                combinedEmissions= parameters.massOfWasteFrom_T1S_ForPurchasedProduct.toDouble() * parameters.EF_WasteActivity.toDouble()
             }                                 
         }
         
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
         println "I finished calculating emission details in purchased Product"
         return emissions        
    }                
}
