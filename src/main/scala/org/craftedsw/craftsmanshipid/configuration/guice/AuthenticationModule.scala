package org.craftedsw.craftsmanshipid.configuration.guice

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import org.craftedsw.craftsmanshipid.infrastructure.authorization.filter.{DummyAuthenticationFilter, SiteMinderAuthorizationFilter, AuthorizationFilter}

class AuthenticationModule extends AbstractModule with ScalaModule {

	def configure() {
		bind[AuthorizationFilter].to[DummyAuthenticationFilter]
//		bind[AuthorizationFilter].to[SiteMinderAuthorizationFilter]
	}

}
