package org.craftedsw.craftsmanshipid.configuration

import com.typesafe.config.ConfigFactory

object ApplicationConfig {

	val ENVIRONMENT_NAME = "environment.name"
	val DEBUG_MODE = "environment.debug"
	val SERVER_PORT = "server.port"
	val SERVER_RESOURCE_BASE = "server.resource.base"
	val TESS_URL = "tess.url"
	val TESS_USERNAME = "tess.username"
	val TESS_PASSWORD = "tess.password"
	val TESS_ENCRYPTED = "tess.encrypted"
	val TESS_SECURED = "tess.secured"
	val PRELOAD_DATA = "preload"
	val AUTHENTICATION_MODE = "authenticationMode"
	val DATABASE_ENABLED = "database.enabled"
	val DATABASE_DRIVER = "database.driver"
	val DATABASE_URL = "database.connection.url"
	val DATABASE_USER = "database.connection.user"
	val DATABASE_PASSWORD = "database.connection.password"
	val FEATURE_PHASE_1_VISIBLE = "feature_phase_1_visible"
	val APPLICATION_VERSION= "version"

	lazy val instance = new ApplicationConfig

	def apply() = {
		instance
	}
}

class ApplicationConfig private() {

	private lazy val config = ConfigFactory.load().withFallback(ConfigFactory.load("version.conf"))

	def property(name: String): String = {
		config.getString(name)
	}

	def propertyAsInt(name: String): Int = {
		config.getInt(name)
	}

	def propertyAsBoolean(name: String) = {
		config.getBoolean(name)
	}

}