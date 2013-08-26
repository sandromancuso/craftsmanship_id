import sbt._
import Keys._

object CraftsmanshipID_Build extends Build
{
	lazy val root =
		Project("root", file("."))
			.configs( PageTest )
			.settings( inConfig( PageTest )(Defaults.testSettings) : _*)
			.settings( libraryDependencies += specs )

	lazy val PageTest = config("page") extend(Test)
	lazy val specs =  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
}