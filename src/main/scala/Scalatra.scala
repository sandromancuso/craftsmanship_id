import javax.servlet.ServletContext
import org.craftedsw.craftsmanshipid.configuration.ApplicationConfig
import org.craftedsw.craftsmanshipid.controllers.MainController
import org.scalatra.LifeCycle

/**
 * This is the Scalatra bootstrap file. You can use it to mount servlets or
 * filters. It's also a good place to put initialization code which needs to
 * run at application start (e.g. database configurations), and init params.
 */
class Scalatra extends LifeCycle {
	override def init(context: ServletContext) {
		context.mount(new MainController(ApplicationConfig.instance), "/*")
	}
}