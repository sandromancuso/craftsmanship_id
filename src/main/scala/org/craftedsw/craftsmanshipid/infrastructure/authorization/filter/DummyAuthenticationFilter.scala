package org.craftedsw.craftsmanshipid.infrastructure.authorization.filter

import org.craftedsw.craftsmanshipid.infrastructure.authorization.{UserPrincipal, CIAUserPrincipal}
import CIAUserPrincipal._
import DummyAuthenticationFilter._

abstract class DummyAuthenticationFilter extends SiteMinderAuthorizationFilter {

	override def retrieveSSOUserFromRequest() = createUserPrincipal

	def createUserPrincipal(): UserPrincipal

}

class DummyAuthenticationFilterForControlAdmin extends DummyAuthenticationFilter {

	def createUserPrincipal(): UserPrincipal = {
		createUserPrincipalWithRole(ROLE_EOD_ADMINISTRATOR)
	}
}

class DummyAuthenticationFilterForControlViewer extends DummyAuthenticationFilter {

	def createUserPrincipal(): UserPrincipal = {
		createUserPrincipalWithRole(ROLE_CONTROL_VIEWER)
	}
}

object DummyAuthenticationFilter {

	def createUserPrincipalWithRole(roles: String) : UserPrincipal = {
		roles match {
			case ROLE_EOD_ADMINISTRATOR => createUserPrincipalForControlAdmin
			case ROLE_CONTROL_VIEWER => createUserPrincipalForControlViewer
		}
	}

	def createUserPrincipalForControlAdmin(): UserPrincipal = {
		new CIAUserPrincipal(
			logonId = "dummyuser",
			firstName = "Control",
			lastName = "Admin",
			roles = ROLE_EOD_ADMINISTRATOR)
	}

	def createUserPrincipalForControlViewer(): UserPrincipal = {
		new CIAUserPrincipal(
			logonId = "dummyuser",
			firstName = "Control",
			lastName = "Viewer",
			roles = ROLE_CONTROL_VIEWER)
	}

}