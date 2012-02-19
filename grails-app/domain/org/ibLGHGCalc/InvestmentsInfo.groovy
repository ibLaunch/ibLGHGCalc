package org.ibLGHGCalc

class InvestmentsInfo {

    static belongsTo = [organization:Organization]
    String sourceDescription    
    
    //========================================
    // Investment-Specific Approach
    //========================================
    String investmentType  //1.Equity 2.Debt with know use of proceeds 3.Project Finance 4.Debt without known use of proceeds 5.Managed investments and client services 6.Other investment category
    Double scope1Emissions
    String scope1Emissions_Unit
    Double scope2Emissions
    String scope2Emissions_Unit

    Double percentShareOfInvestment //(%) Make sure clientShareOfServices percent is captured by explicitly telling user

    //-------Optional Reporting------------------------
    Double anticipatedLifetimeScope1Emissions
    String anticipatedLifetimeScope1Emissions_Unit
    Double anticipatedLifetimeScope2Emissions
    String anticipatedLifetimeScope2Emissions_Unit

    //========================================
    // Economic Data Method
    //========================================
    String investmentSector  //Anything that user uses and has EF available
    Double investmentAmount
    String investmentAmount_Unit //($)
    Double EF_SectorSpecific
    String EF_SectorSpecific_Unit //(Kg CO2e/$)

    //Other fields    
    String methodType
    String userNotesOnData  //User can keep the notes here, in terms of where the data was coming from    
    //Double calculatedEmissions  //calculated emissions based on the use data
    //String calculatedEmissions_Unit
    
    Date dataBeginDate
    Date dataEndDate

    Date dateCreated
    Date lastUpdated

    SecUser lastUpdatedByUserReference
    String dataOrigin //"sourceFileName" or 'UI'    
    
    static hasMany = [emissionsDetailsList: EmissionsDetails]
    
    static constraints = {
        
        organization(nullable:false)
        sourceDescription(nullable:true, maxsize:255)        

        investmentType(blank:false, maxsize:255)
        scope1Emissions(nullable:true)
        scope1Emissions_Unit(nullable:true, maxsize:255)
        scope2Emissions(nullable:true)
        scope2Emissions_Unit(nullable:true, maxsize:255)

        percentShareOfInvestment(nullable:true)

        anticipatedLifetimeScope1Emissions(nullable:true)
        anticipatedLifetimeScope1Emissions_Unit(nullable:true, maxsize:255)
        anticipatedLifetimeScope2Emissions(nullable:true)
        anticipatedLifetimeScope2Emissions_Unit(nullable:true, maxsize:255)

        investmentSector (nullable:true, maxsize:255)
        investmentAmount(nullable:true)
        investmentAmount_Unit(nullable:true, maxsize:255)
        EF_SectorSpecific(nullable:true)
        EF_SectorSpecific_Unit (nullable:true, maxsize:255)
        
        methodType(blank:false, maxsize:255)
        userNotesOnData(nullable:true, maxsize:255)
        //calculatedEmissions(nullable:true)
        //calculatedEmissions_Unit(nullable:true, maxsize:255)
        dataBeginDate(nullable:false)
        dataEndDate(nullable:false)		
        emissionsDetailsList(nullable:true)
        lastUpdatedByUserReference(nullable:true)
        dataOrigin(nullable:true, maxsize:255)                                                
        
    }
}