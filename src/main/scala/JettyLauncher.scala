import com.google.inject.{Inject, Guice}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import org.craftedsw.craftsmanshipid.configuration.ApplicationConfig
import ApplicationConfig._
import org.craftedsw.craftsmanshipid.configuration.guice.{WebModule, AuthenticationModule, MainModule}
import org.craftedsw.craftsmanshipid.controllers.{MainController, BaseController}
import org.craftedsw.craftsmanshipid.infrastructure.authorization.filter.AuthorizationFilter
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletHolder, DefaultServlet}
import org.eclipse.jetty.webapp.WebAppContext

object JettyLauncher extends App {

	override def main(args: Array[String]) {
		val injector = new ScalaInjector(Guice.createInjector(
											new WebModule(),
											new MainModule(),
											new AuthenticationModule))

		val launcher = injector.instance[JettyLauncher]
		launcher.start
		launcher.join
	}

}

class JettyLauncher @Inject()(
	                             applicationConfig: ApplicationConfig,
	                             authorizationFilter: AuthorizationFilter,
	                             mainController: MainController) {

	def port = if(System.getenv("PORT") != null) {
					System.getenv("PORT").toInt
				} else {
					applicationConfig propertyAsInt SERVER_PORT
				}

	private val server = createServer

	def createServer = {
		val server = new Server(port)
		server setHandler createContext
		server
	}

	def createContext: WebAppContext = {
		val context = createWebContext
		addFilters(context)
		addControllers(context)
		context
	}

	def resourceBase: String = {
		applicationConfig property SERVER_RESOURCE_BASE
	}

	def start {
		server start
	}

	def join {
		server join
	}

	def stop {
		server stop
	}

	private def createWebContext: WebAppContext = {
		val context = new WebAppContext()
		context addServlet(classOf[DefaultServlet], "/")
		context setContextPath "/"
		context setResourceBase resourceBase

		if ("LOCAL" == (applicationConfig property ENVIRONMENT_NAME)) {
			context.setInitParameter("org.eclipse.jetty.servlet.Default.maxCachedFiles", "0")
			context.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false")
		}

		context
	}

	private def addFilters(context: WebAppContext) {
//		val gzipFilterHolder = new FilterHolder(classOf[GzipFilter])
//		gzipFilterHolder.setInitParameter("mimeTypes", "text/html,text/plain,text/xml,application/xhtml+xml,text/css,application/javascript,image/svg+xml")
//		context addFilter(gzipFilterHolder, "/*", EnumSet.of(FORWARD, REQUEST, ASYNC, INCLUDE, ERROR))
	}

	private def addControllers(context: WebAppContext) {
		addController(context, mainController, "/*")
	}

	private def addController(context: WebAppContext, controller: BaseController, path: String) {
		context addServlet(new ServletHolder(controller), path)
	}

}
