import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "WhoIsWho"
    val appVersion      = "3.1416"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
