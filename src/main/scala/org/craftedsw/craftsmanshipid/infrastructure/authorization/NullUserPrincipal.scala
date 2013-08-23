package org.craftedsw.craftsmanshipid.infrastructure.authorization

class NullUserPrincipal extends UserPrincipal {

	def isEODAdmin(): Boolean = false

	def isControlViewer(): Boolean = true

	def displayName: String = "Null"

	def isAuthenticated: Boolean = true

	def logonId: String = ""

	def roles: String = ""
}