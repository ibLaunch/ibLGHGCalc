package org.ibLGHGCalc
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

import org.springframework.security.acls.domain.BasePermission

class RefridgerationAirConditioningInfoService {

    static transactional = true
    def aclUtilService
    def springSecurityService


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    RefridgerationAirConditioningInfo findRefridgerationAirConditioningInfo(Long id) {
      RefridgerationAirConditioningInfo.get(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostFilter("hasPermission(filterObject, read) or hasPermission(filterObject, admin)")
    def RefridgerationAirConditioningInfo[] findRefridgerationAirConditioningInfos(Map<String, String> parameters) {
      /*
      def theOrganization = Organization.get(parameters.organizationId)
      def RefridgerationAirConditioningInfo[] refridgerationAirConditioningInfoList
      refridgerationAirConditioningInfoList = RefridgerationAirConditioningInfo.findAllByOrganizationAndMethodType(theOrganization,parameters.methodType)
      return refridgerationAirConditioningInfoList
      */
      //RefridgerationAirConditioningInfo.list()
      def theOrganization

      if (parameters.organizationId) {
          theOrganization = Organization.get(parameters.organizationId)
      } else if (parameters.organizationName){
          theOrganization = Organization.findByOrganizationName(parameters.organizationName)
      } else if (parameters.id) {
           // User has provided the id of the mobile comubstion source, so just provide that and return from here.           
           return RefridgerationAirConditioningInfo.get(parameters.id)
      } else {
          println "-----I don't know organization in refridgerationAirConditioningInfoService.findRefridgerationAirConditioningInfos()"
      }
      //def inventoryYear = parameters.inventoryYear
      def refridgerationAirConditioningInfoList = []
      println "parameters.inventoryYearBeginDate " + parameters.inventoryYearBeginDate
      Date inventoryYearBeginDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearBeginDate)
      Date inventoryYearEndDate = new Date().parse('yyyy-MM-dd', parameters.inventoryYearEndDate)
      //-- Just testing programType for now, it needs to be used properly in future ??
      String programType = parameters.programType
      println "Program Type : " +programType

      //-- Only select the Refridgeration and Air Conditioning sources which are within the inventory year
      int i = 0
      theOrganization.refridgerationAirConditioningInfoList.each {
         if ((it.fuelUsedBeginDate >= inventoryYearBeginDate) && ( it.fuelUsedEndDate <= inventoryYearEndDate) && (it.methodType.equals(parameters.methodType))) {
            refridgerationAirConditioningInfoList[i] = it
            i++
         }
      }
      return refridgerationAirConditioningInfoList
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def RefridgerationAirConditioningInfo save(Map<String, String> parameters) {
      log.info "save( ${parameters} )"
      println parameters

      //- Getting the emission Factors
      //--Get the theRefridgerationAirConditioningInfo
      def theRefridgerationAirConditioningInfo = RefridgerationAirConditioningInfo.get(parameters.id)

      //--Implement proper code for programType-??
      String programType = "US EPA"
      String emissionsType

      if (parameters.methodType?.contains("Refridgeration Air Conditioning")){
            emissionsType = "Refridgeration And Air Conditioning"
      } else if (parameters.methodType?.contains("Fire Suppression")){
            emissionsType = "Fire Suppression"
      } else {
          emissionsType = "I don't know"
      }

      //String emissionsType = "Refridgeration And Air Conditioning"
      //Double CO2Emissions
      //-define emissions map
      def Map<String,String> emissions

      if (!theRefridgerationAirConditioningInfo) {
        println "theRefridgerationAirConditioningInfo is Null"
        theRefridgerationAirConditioningInfo = new RefridgerationAirConditioningInfo()
        def theEmissionsDetails = new EmissionsDetails()
        emissions = calculateEmissions(parameters, programType, emissionsType)

        //set the emissions details
        theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions")?.toDouble()
        theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions")?.toDouble()
        theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions")?.toDouble()
        theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions")?.toDouble()
        theEmissionsDetails.CO2EmissionsUnit = emissions.get("CO2EmissionsUnit")
        theEmissionsDetails.biomassCO2EmissionsUnit = emissions.get("biomassCO2EmissionsUnit")
        theEmissionsDetails.CH4EmissionsUnit = emissions.get("CH4EmissionsUnit")
        theEmissionsDetails.N2OEmissionsUnit = emissions.get("N2OEmissionsUnit")
        theEmissionsDetails.emissionsType = emissions.get("emissionsType")
        theEmissionsDetails.programType = emissions.get("programType")

        if (theEmissionsDetails) println "EMissions Details is Not Empty"
        theRefridgerationAirConditioningInfo.addToEmissionsDetailsList(theEmissionsDetails)
      }
      else
      {
        println "theRefridgerationAirConditioningInfo is Not Null"
        theRefridgerationAirConditioningInfo.emissionsDetailsList.each{
            def theEmissionsDetails = it
            println "calling calculateEmissions"
            emissions = calculateEmissions(parameters, theEmissionsDetails.programType, theEmissionsDetails.emissionsType)
            println "called calculateEmissions"
              //--update the emissionsDetails
            theEmissionsDetails.CO2Emissions = emissions.get("CO2Emissions")?.toDouble()
            theEmissionsDetails.CH4Emissions = emissions.get("CH4Emissions")?.toDouble()
            theEmissionsDetails.N2OEmissions = emissions.get("N2OEmissions")?.toDouble()
            theEmissionsDetails.biomassCO2Emissions = emissions.get("biomassCO2Emissions")?.toDouble()
            theEmissionsDetails.CO2EmissionsUnit = emissions.get("CO2EmissionsUnit")
            theEmissionsDetails.biomassCO2EmissionsUnit = emissions.get("biomassCO2EmissionsUnit")
            theEmissionsDetails.CH4EmissionsUnit = emissions.get("CH4EmissionsUnit")
            theEmissionsDetails.N2OEmissionsUnit = emissions.get("N2OEmissionsUnit")
            theEmissionsDetails.emissionsType = emissions.get("emissionsType")
            theEmissionsDetails.programType = emissions.get("programType")
            }
      }

      //Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedBeginDate)
      //Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", parameters.fuelUsedEndDate)
      Date fuelUsedBeginDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedBeginDate)
      Date fuelUsedEndDate = new Date().parse("yyyy-MM-dd", parameters.fuelUsedEndDate)

      println "fuelUsedBeginDate : " + fuelUsedBeginDate
      println "fuelUsedEndDate : " + fuelUsedEndDate

      theRefridgerationAirConditioningInfo.gasType = parameters.gasType
      theRefridgerationAirConditioningInfo.fuelUsedBeginDate = fuelUsedBeginDate
      theRefridgerationAirConditioningInfo.fuelUsedEndDate = fuelUsedEndDate
      theRefridgerationAirConditioningInfo.methodType = parameters.methodType

//---Swtich statement
      switch (parameters.methodType){
            case ["Refridgeration Air Conditioning - Company-Wide Material Balance Method","Fire Suppression - Company-Wide Material Balance Method"] :
                Double inventoryChange, transferredAmount, capacityChange
                if (parameters.inventoryChange) {inventoryChange = parameters.inventoryChange.toDouble()}  else  {inventoryChange = 0}
                if (parameters.transferredAmount) { transferredAmount = parameters.transferredAmount.toDouble()} else  {transferredAmount = 0}
                if (parameters.capacityChange) {capacityChange = parameters.capacityChange.toDouble()} else  {capacityChange = 0}

                theRefridgerationAirConditioningInfo.inventoryChange = inventoryChange
                theRefridgerationAirConditioningInfo.transferredAmount = transferredAmount
                theRefridgerationAirConditioningInfo.capacityChange = capacityChange
                //Below is to avoid null pointer error  -- ?? in future change it
                theRefridgerationAirConditioningInfo.sourceDescription = "Not Applicable"
                theRefridgerationAirConditioningInfo.typeOfEquipment = "Not Applicable"

            case ["Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method","Fire Suppression - Company-Wide Simplified Material Balance Method"] :
                Double newUnitsCharge, newUnitsCapacity, existingUnitsRecharge, disposedUnitsCapacity, disposedUnitsRecovered
                if (parameters.newUnitsCharge) {newUnitsCharge = parameters.newUnitsCharge.toDouble()}  else  {newUnitsCharge = 0}
                if (parameters.newUnitsCapacity) { newUnitsCapacity = parameters.newUnitsCapacity.toDouble()} else  {newUnitsCapacity = 0}
                if (parameters.existingUnitsRecharge) {existingUnitsRecharge = parameters.existingUnitsRecharge.toDouble()} else {existingUnitsRecharge = 0}
                if (parameters.disposedUnitsCapacity) {disposedUnitsCapacity = parameters.disposedUnitsCapacity.toDouble()} else {disposedUnitsCapacity = 0}
                if (parameters.disposedUnitsRecovered) {disposedUnitsRecovered = parameters.disposedUnitsRecovered.toDouble()} else {disposedUnitsRecovered = 0}

                theRefridgerationAirConditioningInfo.newUnitsCharge = newUnitsCharge
                theRefridgerationAirConditioningInfo.newUnitsCapacity = newUnitsCapacity
                theRefridgerationAirConditioningInfo.existingUnitsRecharge = existingUnitsRecharge
                theRefridgerationAirConditioningInfo.disposedUnitsCapacity = disposedUnitsCapacity
                theRefridgerationAirConditioningInfo.disposedUnitsRecovered = disposedUnitsRecovered

                //Below is to avoid null pointer error  -- ?? in future change it
                theRefridgerationAirConditioningInfo.sourceDescription = "Not Applicable"
                theRefridgerationAirConditioningInfo.typeOfEquipment = "Not Applicable"

            case "Refridgeration Air Conditioning - Source Level Screening Method":
                Double sourceNewUnitsCharge, operatingUnitsCapacity, sourceDisposedUnitsCapacity, timeInYearsUsed

                if (parameters.sourceNewUnitsCharge) {sourceNewUnitsCharge = parameters.sourceNewUnitsCharge.toDouble()}  else  {sourceNewUnitsCharge = 0}
                if (parameters.operatingUnitsCapacity) {operatingUnitsCapacity = parameters.operatingUnitsCapacity.toDouble()}  else  {operatingUnitsCapacity = 0}
                if (parameters.sourceDisposedUnitsCapacity) {sourceDisposedUnitsCapacity = parameters.sourceDisposedUnitsCapacity.toDouble()}  else  {sourceDisposedUnitsCapacity = 0}
                if (parameters.timeInYearsUsed) {timeInYearsUsed = parameters.timeInYearsUsed.toDouble()}  else  {timeInYearsUsed = 0}

                theRefridgerationAirConditioningInfo.sourceDescription = parameters.sourceDescription
                theRefridgerationAirConditioningInfo.typeOfEquipment = parameters.typeOfEquipment
                theRefridgerationAirConditioningInfo.sourceNewUnitsCharge = sourceNewUnitsCharge
                theRefridgerationAirConditioningInfo.operatingUnitsCapacity = operatingUnitsCapacity
                theRefridgerationAirConditioningInfo.sourceDisposedUnitsCapacity = sourceDisposedUnitsCapacity
                theRefridgerationAirConditioningInfo.timeInYearsUsed = timeInYearsUsed

            case "Fire Suppression - Source Level Screening Method":
                Double sourceNewUnitsCharge, operatingUnitsCapacity

                if (parameters.sourceNewUnitsCharge) {sourceNewUnitsCharge = parameters.sourceNewUnitsCharge.toDouble()}  else  {sourceNewUnitsCharge = 0}
                if (parameters.operatingUnitsCapacity) {operatingUnitsCapacity = parameters.operatingUnitsCapacity.toDouble()}  else  {operatingUnitsCapacity = 0}

                theRefridgerationAirConditioningInfo.sourceDescription = parameters.sourceDescription
                theRefridgerationAirConditioningInfo.typeOfEquipment = parameters.typeOfEquipment
                theRefridgerationAirConditioningInfo.sourceNewUnitsCharge = sourceNewUnitsCharge
                theRefridgerationAirConditioningInfo.operatingUnitsCapacity = operatingUnitsCapacity

             default:
                    println "In-correct Method Type for refridgerationAirConditioningInfoservice"
        }

      println "theRefridgerationAirConditioningInfo :----:  " +   theRefridgerationAirConditioningInfo

      //--Save the user reference
      theRefridgerationAirConditioningInfo.lastUpdatedByUserReference = (SecUser) springSecurityService.currentUser
      //--Save the data origin
      theRefridgerationAirConditioningInfo.dataOrigin =  parameters.dataOrigin ? parameters.dataOrigin : "UI"

      def theOrganization =  Organization.get(parameters.organizationId)
      println "The Organization : " + theOrganization

      theRefridgerationAirConditioningInfo.organization = theOrganization
      theRefridgerationAirConditioningInfo.save(flush:true)
      aclUtilService.addPermission(theRefridgerationAirConditioningInfo, springSecurityService.authentication.name, BasePermission.ADMINISTRATION)

      return theRefridgerationAirConditioningInfo
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    def remove(Map<String, String> parameters) {
      log.info "remove( ${parameters} )"
      //RefridgerationAirConditioningInfo.get(parameters.id)?.delete()
      def theRefridgerationAirConditioningInfo = RefridgerationAirConditioningInfo.get(parameters.id)

      if (theRefridgerationAirConditioningInfo){
        delete(theRefridgerationAirConditioningInfo)
      }
      else {
          println "No theRefridgerationAirConditioningInfo found to remove!"
      }
    }

   @Transactional
   @PreAuthorize("hasPermission(#theRefridgerationAirConditioningInfo, delete) or hasPermission(#theRefridgerationAirConditioningInfo, admin)")
   void delete(RefridgerationAirConditioningInfo theRefridgerationAirConditioningInfo) {
      theRefridgerationAirConditioningInfo.delete()
      // Delete the ACL information as well
      aclUtilService.deleteAcl theRefridgerationAirConditioningInfo
   }

    def Map<String,String> calculateEmissions(Map<String, String> parameters, String programType, String emissionsType){

        def emissions = [:]
        def Double CO2Emissions
        def String CO2EmissionsUnit
        def String biomassCO2EmissionsUnit
        def String CH4EmissionsUnit
        def String N2OEmissionsUnit

        if ( (programType.equals("US EPA")) &&
             ((emissionsType.equals("Refridgeration And Air Conditioning"))|| (emissionsType.equals("Fire Suppression")))) {
             //- Get the emission factor object
             def theGWP_RefridgerationAirConditioning_EPA =  GWP_RefridgerationAirConditioning_EPA.findByGasType(parameters.gasType)


//--the big switch statement....
          switch (parameters.methodType){
               case ["Refridgeration Air Conditioning - Company-Wide Material Balance Method","Fire Suppression - Company-Wide Material Balance Method"] :
                    Double inventoryChange, transferredAmount, capacityChange
                    if (parameters.inventoryChange) {inventoryChange = parameters.inventoryChange.toDouble()}  else  {inventoryChange = 0}
                    if (parameters.transferredAmount) { transferredAmount = parameters.transferredAmount.toDouble()} else  {transferredAmount = 0}
                    if (parameters.capacityChange) {capacityChange = parameters.capacityChange.toDouble()} else  {capacityChange = 0}

                    CO2Emissions = (inventoryChange + transferredAmount + capacityChange) * theGWP_RefridgerationAirConditioning_EPA.gasTypeGWP
                    CO2EmissionsUnit = "lb"
                    biomassCO2EmissionsUnit = "lb"
                    CH4EmissionsUnit = "gram"
                    N2OEmissionsUnit = "gram"

                    println " I am Material Balance - Service"
                    break

                case ["Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method", "Fire Suppression - Company-Wide Simplified Material Balance Method"] :
                    println " I am in the Simplified.. Service"
                    Double newUnitsCharge, newUnitsCapacity, existingUnitsRecharge, disposedUnitsCapacity, disposedUnitsRecovered
                    if (parameters.newUnitsCharge) {newUnitsCharge = parameters.newUnitsCharge.toDouble()}  else  {newUnitsCharge = 0}
                    if (parameters.newUnitsCapacity) { newUnitsCapacity = parameters.newUnitsCapacity.toDouble()} else  {newUnitsCapacity = 0}
                    if (parameters.existingUnitsRecharge) {existingUnitsRecharge = parameters.existingUnitsRecharge.toDouble()} else {existingUnitsRecharge = 0}
                    if (parameters.disposedUnitsCapacity) {disposedUnitsCapacity = parameters.disposedUnitsCapacity.toDouble()} else {disposedUnitsCapacity = 0}
                    if (parameters.disposedUnitsRecovered) {disposedUnitsRecovered = parameters.disposedUnitsRecovered.toDouble()} else {disposedUnitsRecovered = 0}

                    CO2Emissions = (newUnitsCharge - newUnitsCapacity +existingUnitsRecharge + disposedUnitsCapacity - disposedUnitsRecovered) * theGWP_RefridgerationAirConditioning_EPA.gasTypeGWP

                    CO2EmissionsUnit = "lb"
                    biomassCO2EmissionsUnit = "lb"
                    CH4EmissionsUnit = "gram"
                    N2OEmissionsUnit = "gram"

                    println " I am in the Simplified.. Service"
                    break

                case "Refridgeration Air Conditioning - Source Level Screening Method":
                    Double sourceNewUnitsCharge, operatingUnitsCapacity, sourceDisposedUnitsCapacity, timeInYearsUsed
                    if (parameters.sourceNewUnitsCharge) {sourceNewUnitsCharge = parameters.sourceNewUnitsCharge.toDouble()}  else  {sourceNewUnitsCharge = 0}
                    if (parameters.operatingUnitsCapacity) {operatingUnitsCapacity = parameters.operatingUnitsCapacity.toDouble()}  else  {operatingUnitsCapacity = 0}
                    if (parameters.sourceDisposedUnitsCapacity) {sourceDisposedUnitsCapacity = parameters.sourceDisposedUnitsCapacity.toDouble()}  else  {sourceDisposedUnitsCapacity = 0}
                    if (parameters.timeInYearsUsed) {timeInYearsUsed = parameters.timeInYearsUsed.toDouble()}  else  {timeInYearsUsed = 0}

                    //variables to get values from EquipmentCapacityRange_EPA
                    def theEquipmentCapacityRange_EPA = EquipmentCapacityRange_EPA.findByTypeOfEquipment(parameters.typeOfEquipment)
                    CO2Emissions =
                                    (
                                    (sourceNewUnitsCharge * (theEquipmentCapacityRange_EPA.kFactor/100))
                                    +
                                    (operatingUnitsCapacity * (theEquipmentCapacityRange_EPA.xFactor/100) * timeInYearsUsed )
                                    +
                                    (sourceDisposedUnitsCapacity * (theEquipmentCapacityRange_EPA.yFactor/100) * (1-theEquipmentCapacityRange_EPA.zFactor/100))
                                    )*theGWP_RefridgerationAirConditioning_EPA.gasTypeGWP
                    CO2EmissionsUnit = "Kg"
                    biomassCO2EmissionsUnit = "Kg"
                    CH4EmissionsUnit = "gram"
                    N2OEmissionsUnit = "gram"
                    break
                    
                case "Fire Suppression - Source Level Screening Method":
                    Double sourceNewUnitsCharge, operatingUnitsCapacity
                    if (parameters.sourceNewUnitsCharge) {sourceNewUnitsCharge = parameters.sourceNewUnitsCharge.toDouble()}  else  {sourceNewUnitsCharge = 0}
                    if (parameters.operatingUnitsCapacity) {operatingUnitsCapacity = parameters.operatingUnitsCapacity.toDouble()}  else  {operatingUnitsCapacity = 0}

                    def theEquipmentCapacityRange_EPA = EquipmentCapacityRange_EPA.findByTypeOfEquipment(parameters.typeOfEquipment)

                    if (parameters.typeOfEquipment.equals("Fixed")) {
                        CO2Emissions = operatingUnitsCapacity * theGWP_RefridgerationAirConditioning_EPA.gasTypeGWP * 0.015
                    } else if (parameters.typeOfEquipment.equals("Portable")) {
                        CO2Emissions = operatingUnitsCapacity * theGWP_RefridgerationAirConditioning_EPA.gasTypeGWP * 0.02
                    }
                    //variables to get values from EquipmentCapacityRange_EPA
                    CO2EmissionsUnit = "Kg"
                    biomassCO2EmissionsUnit = "Kg"
                    CH4EmissionsUnit = "gram"
                    N2OEmissionsUnit = "gram"
                    println " I am in Fire Suppression - Source Level Screening Method - Service"
                    break

                 default:
                        println "In-correct Method Type for refridgerationAirConditioningInfoservice"
            }

//--End of the big switch statement....
             emissions.put("CO2Emissions", CO2Emissions)
             emissions.put("biomassCO2Emissions", 0)
             emissions.put("CH4Emissions", 0)
             emissions.put("N2OEmissions", 0)
             emissions.put("CO2EmissionsUnit", CO2EmissionsUnit)
             emissions.put("biomassCO2EmissionsUnit", biomassCO2EmissionsUnit)
             emissions.put("CH4EmissionsUnit", CH4EmissionsUnit)
             emissions.put("N2OEmissionsUnit", N2OEmissionsUnit)
             emissions.put("emissionsType", emissionsType)
             emissions.put("programType", programType)

             return emissions
        }
    }
}
