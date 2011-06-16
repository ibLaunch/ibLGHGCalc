package org.ibLGHGCalc

class RefridgerationAirConditioningInfo {

	static belongsTo = [organization:Organization]

	//-company-wide material balance fields
	String gasType

	Double inventoryChange
	Double transferredAmount
	Double capacityChange

	String inventoryChangeUnit='lb'
	String transferredAmountUnit='lb'
	String capacityChangeUnit='lb'

	//-company-wide simplified material balance fields
	Double newUnitsCharge
	Double newUnitsCapacity
	Double existingUnitsRecharge
	Double disposedUnitsCapacity
	Double disposedUnitsRecovered

	String newUnitsChargeUnit='lb'
	String newUnitsCapacityUnit='lb'
	String existingUnitsRechargeUnit='lb'
	String disposedUnitsCapacityUnit='lb'
	String disposedUnitsRecoveredUnit='lb'

	//-Source level screening method fields
	String sourceDescription
	String typeOfEquipment
	Double sourceNewUnitsCharge
	Double operatingUnitsCapacity
	Double sourceDisposedUnitsCapacity
        Double timeInYearsUsed
        
	String sourceNewUnitsChargeUnit='kg'
	String operatingUnitsCapacityUnit='kg'
	String sourceDisposedUnitsCapacityUnit='kg'

	String methodType

	Date  fuelUsedBeginDate
	Date  fuelUsedEndDate

	Date dateCreated
	Date lastUpdated

        SecUser lastUpdatedByUserReference
        String dataOrigin //"sourceFileName" or 'UI'

	static hasMany = [emissionsDetailsList: EmissionsDetails]

	static constraints = {
		organization(nullable:false)
		gasType(blank:false, maxsize:255)

		inventoryChange(nullable:true)
		transferredAmount(nullable:true)
		capacityChange(nullable:true)

		newUnitsCharge(nullable:true)
		newUnitsCapacity(nullable:true)
		existingUnitsRecharge(nullable:true)
		disposedUnitsCapacity(nullable:true)
		disposedUnitsRecovered(nullable:true)

		sourceDescription(nullable:true)
		typeOfEquipment(nullable:true)
		sourceNewUnitsCharge(nullable:true)
		operatingUnitsCapacity(nullable:true)
		sourceDisposedUnitsCapacity(nullable:true)
                timeInYearsUsed(nullable:true)

		methodType(blank:false, maxsize:255)
		fuelUsedBeginDate(nullable:false)
		fuelUsedEndDate(nullable:false)
        	emissionsDetailsList(nullable:true)
                lastUpdatedByUserReference(nullable:true)
                dataOrigin(nullable:true)
	}
	static mapping = {
	    emissionsDetailsList cascade: "all-delete-orphan"
	}        
}