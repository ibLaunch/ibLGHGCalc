package org.ibLGHGCalc

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class PurchasedProductInfoService {

    static transactional = true

    def aclUtilService
    def springSecurityService

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    PurchasedProductInfo findPurchasedProductInfo(Long id) {
      PurchasedProductInfo.get(id)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")    
    def PurchasedProductInfo[] findPurchasedProductInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedProductInfoList
      //PurchasedProductInfo.findAllByOrganization(theOrganization)
      */
      
      println "parameters-----------==============:"+parameters
      
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      }  else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.
           return PurchasedProductInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in purchasedProductInfoListService.findpurchasedProductInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def purchasedProductInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Purchased Product sources which are within the inventory year
      int i = 0
      theOrganization?.purchasedProductInfoList?.each {
         if ((it.dataBeginDate >= inventoryYearBeginDate) && ( it.dataEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            purchasedProductInfoList[i] = it
            i++
         }
      }
      return purchasedProductInfoList
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def PurchasedProductInfo[] getPurchasedProductInfos(Map<String, String> parameters) {
      //PurchasedProductInfo.list()
      def theOrganization = Organization.get(parameters.organizationId)
      return theOrganization.purchasedProductInfoList
      //PurchasedProductInfo.findAllByOrganization(theOrganization)
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def PurchasedProductInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      PurchasedProductInfo thePurchasedProductInfo = PurchasedProductInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      //String emissionsType = "Purchased Product"
      String emissionsType = "Purchased "+ parameters.purchasedProductType

      def emissions =[:]

      
      if (!thePurchasedProductInfo) {
        println "thePurchasedProductInfo is Null"
        thePurchasedProductInfo = new PurchasedProductInfo()
        EmissionsDetails theEmissionsDetails = new EmissionsDetails()
        
        emissions = calculateEmissions(parameters, programType, emissionsType)        
        theEmissionsDetails = emissions

        //--Below 2 lines are temporary. App was giving problem for emissionsDetails domain for null values for following 2 fields. CHECK
        theEmissionsDetails.N2OEmissions = 0
        theEmissionsDetails.N2OEmissionsUnit = ""                

        thePurchasedProductInfo.addToEmissionsDetailsList(theEmissionsDetails)
                
      }
      else
      {
        println "thePurchasedProductInfo is Not Null"
        thePurchasedProductInfo.emissionsDetailsList.each{
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

/*        

      def processedParameters = parameters      
      def keysToRemove = ["organizationId", "action", "controller", "_componentId","_operationType","isc_metaDataPrefix","isc_dataFormat","_dataSource","fuelUsedBeginDate","fuelUsedEndDate"]
      def processedParameters = [:]
      processedParameters = parameters.findAll({!keysToRemove.contains(it.key)})      
      processedParameters.dataBeginDate = dataBeginDate
      processedParameters.dataEndDate = dataEndDate            
      println "processedParameters:-----]" +processedParameters
      thePurchasedProductInfo = processedParameters    
*/

      thePurchasedProductInfo.dataBeginDate = dataBeginDate
      thePurchasedProductInfo.dataEndDate = dataEndDate   
      thePurchasedProductInfo.sourceDescription=parameters.sourceDescription
      thePurchasedProductInfo.supplierName=parameters.supplierName
      thePurchasedProductInfo.supplierContact=parameters.supplierContact      
      thePurchasedProductInfo.purchasedProductType=parameters.purchasedProductType
      thePurchasedProductInfo.purchasedProductName=parameters.purchasedProductName
      
      thePurchasedProductInfo.quantityOfPurchasedProduct=parameters.quantityOfPurchasedProduct?.toDouble()
      thePurchasedProductInfo.quantityOfPurchasedProduct_Unit=parameters.quantityOfPurchasedProduct_Unit

      switch (parameters.methodType){
          case ["Purchased Goods and Services - Product Level Method"] :
                thePurchasedProductInfo.supplierSpecific_EF_ForPurchasedProduct=parameters.supplierSpecific_EF_ForPurchasedProduct?.toDouble()
                thePurchasedProductInfo.supplierSpecific_EF_ForPurchasedProduct_Unit=parameters.supplierSpecific_EF_ForPurchasedProduct_Unit
          
                break

          case ["Purchased Goods and Services - Supplier Specific Method"] :
                thePurchasedProductInfo.scope1EmissionsOf_T1S_ForPurchasedProduct=parameters.scope1EmissionsOf_T1S_ForPurchasedProduct?.toDouble()
                thePurchasedProductInfo.scope1EmissionsOf_T1S_ForPurchasedProduct_Unit=parameters.scope1EmissionsOf_T1S_ForPurchasedProduct_Unit

                thePurchasedProductInfo.scope2EmissionsOf_T1S_ForPurchasedProduct=parameters.scope2EmissionsOf_T1S_ForPurchasedProduct?.toDouble()
                thePurchasedProductInfo.scope2EmissionsOf_T1S_ForPurchasedProduct_Unit=parameters.scope2EmissionsOf_T1S_ForPurchasedProduct_Unit

                break

          case ["Purchased Goods and Services - Material Or Spend Based Method"] :
                thePurchasedProductInfo.secondary_EF=parameters.secondary_EF?.toDouble()
                thePurchasedProductInfo.secondary_EF_Unit=parameters.secondary_EF_Unit

                break

          default:                 
               println "In-correct Method Type for purchasedProductInfoService"                        
        } // Switch
                      
      thePurchasedProductInfo.userNotesOnData=parameters.userNotesOnData
      thePurchasedProductInfo.methodType=parameters.methodType      
            
      def theOrganization =  Organization.get(parameters.organizationId)
      thePurchasedProductInfo.organization = theOrganization
      
      //--Save the user reference
      thePurchasedProductInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser

     //--Save the data origin
      thePurchasedProductInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      thePurchasedProductInfo.save(flush:true)
      
      //--Save the permissions on this object
      aclUtilService.addPermission(thePurchasedProductInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return thePurchasedProductInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //PurchasedProductInfo.get(parameters.id)?.delete()
      def thePurchasedProductInfo = PurchasedProductInfo.get(parameters.id)

      if (thePurchasedProductInfo){
        delete(thePurchasedProductInfo)
      }
      else {
          println "No thePurchasedProductInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#thePurchasedProductInfo, delete) or hasPermission(#thePurchasedProductInfo, admin)")
   void delete(PurchasedProductInfo thePurchasedProductInfo) {
      thePurchasedProductInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl thePurchasedProductInfo
   }

   def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){
        def emissions = [:]
        Double combinedEmissions = 0

        //--biomass CO2 is zero by default
        Double biomassCO2Emissions = 0
        if ( (programType.equals("US EPA")) &&
             ((emissionsType.equals("Purchased Capital Goods")||emissionsType.equals("Purchased Goods") ||emissionsType.equals("Purchased Services")))) {

            switch (parameters.methodType){
                case ["Purchased Goods and Services - Product Level Method"] :
                     if ((parameters.quantityOfPurchasedProduct.toDouble() > 0) && (parameters.supplierSpecific_EF_ForPurchasedProduct.toDouble() > 0)){             
                        combinedEmissions= parameters.quantityOfPurchasedProduct.toDouble() * parameters.supplierSpecific_EF_ForPurchasedProduct.toDouble()
                     }                
                     break

                case ["Purchased Goods and Services - Supplier Specific Method"] :

                     break

                case ["Purchased Goods and Services - Material Or Spend Based Method"] :
                     if ((parameters.quantityOfPurchasedProduct.toDouble() > 0) && (parameters.secondary_EF.toDouble() > 0)){             
                        combinedEmissions= parameters.quantityOfPurchasedProduct.toDouble() * parameters.secondary_EF.toDouble()
                     }                
                
                     break

                default:                 
                     println "In-correct Method Type for refridgerationAirConditioningInfoservice"                        
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
         
         println "I finished calculating emission details in purchased Product"
         return emissions        
    }                
}
