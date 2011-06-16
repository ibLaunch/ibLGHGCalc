package org.ibLGHGCalc

class GWP_RefridgerationAirConditioning_EPA {

    String gasType
    Double gasTypeGWP
    Boolean isUsedInRefridgeration = Boolean.TRUE
    Boolean isUsedInFireSuppression = Boolean.TRUE

    static constraints = {
            gasType(blank:false)
            gasTypeGWP(nullable:false)
            isUsedInRefridgeration(nullable:false)
            isUsedInFireSuppression(nullable:false)
    }
}
