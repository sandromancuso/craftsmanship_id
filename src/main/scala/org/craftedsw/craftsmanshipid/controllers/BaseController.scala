package org.craftedsw.craftsmanshipid.controllers

import org.scalatra._
import org.scalatra.scalate.ScalateSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.ActionResult
import scala.Some
import org.craftedsw.craftsmanshipid.configuration.ApplicationConfig
import ApplicationConfig._
import org.craftedsw.craftsmanshipid.infrastructure.authorization.{NullUserPrincipal, UserPrincipal}

abstract class BaseController(applicationConfig: ApplicationConfig)
	extends ScalatraServlet
	with ScalateSupport
	with FlashMapSupport
	with JacksonJsonSupport {

	def noCache(result: String): ActionResult = {
		Ok(
			result
			, Map("Cache-Control" -> "no-cache, no-store, must-revalidate", "Pragma" -> "no-cache", "Expires" -> "0"))
	}

	def displayPage(path: String, attributes: (String, Any)*) = {
		contentType = "text/html"
		val allAttributes: Seq[(String, Any)] = attributes :+
			("environment" -> (applicationConfig property ENVIRONMENT_NAME)) :+
			("userPrincipal" -> (userPrincipal())) :+
			("isPhaseOneVisible" -> (applicationConfig propertyAsBoolean(FEATURE_PHASE_1_VISIBLE)))
		jade(path, allAttributes: _*)
	}

	protected def userPrincipal() : UserPrincipal = {
		Option(session.getAttribute("ciaUser")) match {
			case Some(userPrincipal: UserPrincipal) => userPrincipal
			case _ => new NullUserPrincipal
		}
	}

	notFound {
		response.setHeader("Expires", "Thu, 15 Apr 2040 20:00:00 GMT")
		serveStaticResource() getOrElse notFoundPage()
	}

	error {
		case t: Exception => {
			t.printStackTrace()
			throw t
		}
	}

	def notFoundPage() = {
		NotFound(jade("/not_found"))
	}
}
