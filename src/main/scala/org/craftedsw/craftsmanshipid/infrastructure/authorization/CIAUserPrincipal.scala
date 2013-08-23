package org.craftedsw.craftsmanshipid.infrastructure.authorization

import CIAUserPrincipal._

class CIAUserPrincipal(val logonId: String = null,
                       val firstName: String = null,
                       val lastName: String = null,
                       val roles: String = null)
	extends UserPrincipal {

	def isEODAdmin():Boolean = {
		roles != null && roles.contains(ROLE_EOD_ADMINISTRATOR)
	}

	def isControlViewer() : Boolean = {
		roles != null &&
			(isEODAdmin() || roles.contains(ROLE_CONTROL_VIEWER))
	}

	def displayName: String = {
		if (firstName != null && lastName != null) {
			firstName + " " + lastName
		}else if (logonId!=null){
			logonId
		}else {
			"<no name>"
		}
	}

	def isAuthenticated: Boolean = !Option(logonId).getOrElse("").isEmpty
}


object CIAUserPrincipal{
	val ROLE_EOD_ADMINISTRATOR = "EOD_Administrator"
	val ROLE_CONTROL_VIEWER = "CIA_Readonly"
}
