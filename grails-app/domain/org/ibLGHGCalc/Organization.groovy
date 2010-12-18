package org.ibLGHGCalc

class Organization {

    //List stationaryCombustionInfoList
    String organizationName
    
    static hasMany = [stationaryCombustionInfoList:StationaryCombustionInfo]

    static constraints = {
        organizationName(blank:false, unique:true)
        stationaryCombustionInfoList(nullable:true)
    }

    static mapping = {
        stationaryCombustionInfoList cascade: "all-delete-orphan"
        //tablePerHierarchy false
    }

}
