package org.craftedsw.craftsmanshipid.configuration.guice

import net.codingwell.scalaguice.ScalaModule
import com.google.inject.AbstractModule
import org.craftedsw.craftsmanshipid.configuration.ApplicationConfig

class MainModule extends AbstractModule with ScalaModule {

	def configure() {

		bind[ApplicationConfig]

	}
}
