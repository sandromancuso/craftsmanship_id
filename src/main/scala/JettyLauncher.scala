import com.google.inject.{Inject, Guice}
import java.util.EnumSet
import javax.servlet.DispatcherType._
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import org.craftedsw.craftsmanshipid.configuration.ApplicationConfig
import ApplicationConfig._
import org.craftedsw.craftsmanshipid.configuration.guice.{AuthenticationModule, MainModule}
import org.craftedsw.craftsmanshipid.controllers.BaseController
import org.craftedsw.craftsmanshipid.infrastructure.authorization.filter.AuthorizationFilter
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletHolder, DefaultServlet, FilterHolder}
import org.eclipse.jetty.servlets.GzipFilter
import org.eclipse.jetty.webapp.WebAppContext

object JettyLauncher extends App {

	val injector = new ScalaInjector(Guice.createInjector(new MainModule(), new AuthenticationModule))

	val launcher = injector.instance[JettyLauncher]
	launcher.start
	launcher.join

}

class JettyLauncher @Inject()(
	                             applicationConfig: ApplicationConfig,
	                             authorizationFilter: AuthorizationFilter) {

	def port = applicationConfig propertyAsInt SERVER_PORT

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
		val gzipFilterHolder = new FilterHolder(classOf[GzipFilter])
		gzipFilterHolder.setInitParameter("mimeTypes", "text/html,text/plain,text/xml,application/xhtml+xml,text/css,application/javascript,image/svg+xml")
		context addFilter(gzipFilterHolder, "/*", EnumSet.of(FORWARD, REQUEST, ASYNC, INCLUDE, ERROR))
	}

	private def addControllers(context: WebAppContext) {
//		addController(context, mainController,              "/*")
//		addController(context, controlController,           "/control/*")
//		addController(context, controlDetailsController,    "/view/control/*")
//		addController(context, kpciController,              "/kpci/*")
//		addController(context, kpciDetailsController,       "/view/kpci/*")
//		addController(context, kpcController,               "/kpc/*")
//		addController(context, shuttleController,           "/shuttle/*")
//		addController(context, shuttleDetailsController,    "/view/shuttle/*")
//		addController(context, userController,              "/user/*")
//		addController(context, priController,               "/pri/*")
//		addController(context, eodClassificationController, "/eod_classification/*")
//		addController(context, sourceSystemController,      "/sourcesystem/*")
	}

	private def addController(context: WebAppContext, controller: BaseController, path: String) {
		context addServlet(new ServletHolder(controller), path)
	}

}

//object JettyLauncher extends App {
//	val injector = new ScalaInjector(Guice.createInjector(new CIAModule, new CIARepositoryModule, authenticationModule()))
//
//	val launcher = injector.instance[JettyLauncher]
//	launcher.start
//	launcher.loadIfNeeded
//	launcher.join
//
//	def authenticationModule(authenticationMode: String = ApplicationConfig().property(AUTHENTICATION_MODE)) = {
//		val dummy = "(dummy.*)".r
//
//		authenticationMode match {
//			case dummy(authenticationMode) => new DummyAuthenticationModule(authenticationMode)
//			case "siteminder" => new SiteMinderAuthenticationModule
//			case _ => throw new IllegalArgumentException("Authentication mode " + authenticationMode + " is not valid, please enter dummy or siteminder in the CIA configuration file!")
//		}
//	}
//}


//class JettyLauncher @Inject()(
//	                             applicationConfig: ApplicationConfig,
//	                             mainController: MainController,
//	                             controlController: ControlController,
//	                             controlDetailsController: ControlDetailsController,
//	                             kpciController: KPCiController,
//	                             kpciDetailsController: KPCiDetailsController,
//	                             kpcController: KPCController,
//	                             shuttleController: ShuttleController,
//	                             shuttleDetailsController: ShuttleDetailsController,
//	                             userController: UserController,
//	                             priController: PRIController,
//	                             eodClassificationController: EODClassificationController,
//	                             sourceSystemController: SourceSystemController,
//	                             ratingController: RatingController,
//	                             ratingDetailsController: RatingDetailsController,
//	                             metricController: MetricController,
//	                             authorizationFilter: AuthorizationFilter,
//	                             browserCompatibilityFilter: BrowserCompatibilityFilter,
//	                             clientTestController: ClientTestController) {
//
//	def loadIfNeeded = if (applicationConfig propertyAsBoolean PRELOAD_DATA) TestDataLoader.main(Array())
//
//	private val server = createServer
//
//	def createServer = {
//		val server = new Server(port)
//		server setHandler createContext
//		server
//	}
//
//	def port = applicationConfig propertyAsInt SERVER_PORT
//
//	def createContext: WebAppContext = {
//		val context = createWebContext
//		addFilters(context)
//		addMiddleOfficeControllers(context)
//		addRiskMetricsControllers(context)
//		context
//	}
//
//	def resourceBase: String = {
//		applicationConfig property SERVER_RESOURCE_BASE
//	}
//
//	def start {
//		server start
//	}
//
//	def join {
//		server join
//	}
//
//	def stop {
//		server stop
//	}
//
//	private def createWebContext: WebAppContext = {
//		val context = new WebAppContext()
//		context addServlet(classOf[DefaultServlet], "/")
//		context setContextPath "/"
//		context setResourceBase resourceBase
//
//		if ("LOCAL" == (applicationConfig property ENVIRONMENT_NAME)) {
//			context.setInitParameter("org.eclipse.jetty.servlet.Default.maxCachedFiles", "0")
//			context.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false")
//		}
//
//		context
//	}
//
//	private def addFilters(context: WebAppContext) {
//		val gzipFilterHolder = new FilterHolder(classOf[GzipFilter])
//		gzipFilterHolder.setInitParameter("mimeTypes", "text/html,text/plain,text/xml,application/xhtml+xml,text/css,application/javascript,image/svg+xml")
//		context addFilter(gzipFilterHolder, "/*", EnumSet.of(FORWARD, REQUEST, ASYNC, INCLUDE, ERROR))
//
//		context addFilter(classOf[WroFilter], "/wro/*", EnumSet.of(FORWARD, REQUEST, ASYNC, INCLUDE, ERROR))
//		context addFilter(new FilterHolder(browserCompatibilityFilter), "/*", EnumSet.of(FORWARD, REQUEST, ASYNC))
//		context addFilter(new FilterHolder(authorizationFilter), "/*", EnumSet.of(FORWARD, REQUEST, ASYNC))
//	}
//
//	private def addMiddleOfficeControllers(context: WebAppContext) {
//		addController(context, mainController,              "/*")
//		addController(context, controlController,           "/control/*")
//		addController(context, controlDetailsController,    "/view/control/*")
//		addController(context, kpciController,              "/kpci/*")
//		addController(context, kpciDetailsController,       "/view/kpci/*")
//		addController(context, kpcController,               "/kpc/*")
//		addController(context, shuttleController,           "/shuttle/*")
//		addController(context, shuttleDetailsController,    "/view/shuttle/*")
//		addController(context, userController,              "/user/*")
//		addController(context, priController,               "/pri/*")
//		addController(context, eodClassificationController, "/eod_classification/*")
//		addController(context, sourceSystemController,      "/sourcesystem/*")
//		if (applicationConfig propertyAsBoolean DEBUG_MODE) {
//			addController(context, clientTestController, "/test/*")
//		}
//	}
//
//	private def addRiskMetricsControllers(context: WebAppContext) {
//		addController(context, metricController, "/riskmetrics/metric/*")
//		addController(context, ratingController, "/rating/*")
//		addController(context, ratingDetailsController, "/view/rating/*")
//	}
//
//	private def addController(context: WebAppContext, controller: BaseController, path: String) {
//		context addServlet(new ServletHolder(controller), path)
//	}
//
//}
//
