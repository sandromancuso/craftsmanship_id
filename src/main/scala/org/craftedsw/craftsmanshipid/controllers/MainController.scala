package org.craftedsw.craftsmanshipid.controllers

import com.google.inject.Inject
import org.craftedsw.craftsmanshipid.configuration.ApplicationConfig
import org.json4s.{DefaultFormats, Formats}

class MainController @Inject()(applicationConfig: ApplicationConfig)
			extends BaseController(applicationConfig) {

	get("/") {
		displayPage("index")
	}

}
