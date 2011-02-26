package org.ibLGHGCalc

class GWP_RefridgerationAirConditioning_EPA {

    String gasType
    Double gasTypeGWP

    static constraints = {
            gasType(blank:false)
            gasTypeGWP(nullable:false)
    }
}
