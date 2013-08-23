package org.craftedsw.craftsmanshipid.infrastructure.authorization.filter

import org.craftedsw.craftsmanshipid.infrastructure.authorization.{CIAUserPrincipal, UserPrincipal}

class SiteMinderAuthorizationFilter extends AuthorizationFilter {

	get("/*") {
		authenticateAndAuthorize(ensureLoggedInUserInSession)
	}
	post("/*") {
		authenticateAndAuthorize(ensureLoggedInUserInSession)
	}


	def ensureLoggedInUserInSession: UserPrincipal = {
		Option(session.getAttribute("ciaUser")) match {
			case Some(userPrincipal: UserPrincipal) => userPrincipal
			case _ => {
				val ssoUser = retrieveSSOUserFromRequest()
				if (ssoUser.isAuthenticated) {
					session.setAttribute("ciaUser", ssoUser)
				}
				ssoUser
			}
		}
	}

	def retrieveSSOUserFromRequest(): UserPrincipal = {
		val logonId = request.getHeader("SSO_LOGONID")
		val firstName = request.getHeader("SSO_GIVENNAME")
		val lastName = request.getHeader("SSO_SURNAME")
		val roles = request.getHeader("Role")
		val principal = new CIAUserPrincipal(logonId, firstName, lastName, roles)
		println(principal + "retrieved from http header")
		principal
	}

	def authenticateAndAuthorize(userInSession: UserPrincipal) = {
		if (userInSession.isAuthenticated && (userInSession.isEODAdmin() || userInSession.isControlViewer())) {
			pass()
		}
		<html>
			<p>
				<b><font color="red">{"User " + userInSession.logonId  +  " is not setup in CIA."}</font> </b>
				<p><b>For accessing CIA please <a href="http://goto/arp">http://goto/arp</a> and request for appropriate role.</b></p>
			</p>
		</html>
	}

}
