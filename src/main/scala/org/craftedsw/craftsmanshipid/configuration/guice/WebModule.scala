package org.craftedsw.craftsmanshipid.configuration.guice

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import org.craftedsw.craftsmanshipid.controllers.MainController

class WebModule extends AbstractModule with ScalaModule {
	def configure() {
		bind[MainController]
	}
}
