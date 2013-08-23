package org.craftedsw.craftsmanshipid.infrastructure.authorization

abstract class UserPrincipal {

	def isEODAdmin() : Boolean

	def isControlViewer() : Boolean

	def displayName : String

	def isAuthenticated : Boolean

	def logonId : String

	def roles : String

}
