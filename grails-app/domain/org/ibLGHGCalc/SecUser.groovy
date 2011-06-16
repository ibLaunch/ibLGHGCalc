package org.ibLGHGCalc

class SecUser {
/*
        static searchable = {
            mapping {
                boost 2.0
                spellCheck "include"
            }
        }
*/
        String firstName
        String lastName
        String phoneNumber
        String organizationName

	String username
        String email
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true
		password blank: false
                firstName (nullable:true)
                lastName(nullable:true)
                phoneNumber(nullable:true)
                organizationName(nullable:true)
                email(nullable:true)
	}

	static mapping = {
		password column: '`password`'
	}

	Set<SecRole> getAuthorities() {
		SecUserSecRole.findAllBySecUser(this).collect { it.secRole } as Set
	}
}
