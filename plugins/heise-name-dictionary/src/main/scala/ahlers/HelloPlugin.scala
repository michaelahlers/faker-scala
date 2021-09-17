package ahlers

import sbt._
import Keys._

object HelloPlugin extends AutoPlugin {

  object autoImport {
    val helloGreeting = settingKey[String]("greeting")
    val hello = taskKey[Unit]("say hello")
  }

  import autoImport._

  override val globalSettings =
    Seq(
      helloGreeting := "hi"
    )

  override val projectSettings =
    Seq(
      hello := {
        val s = streams.value
        val g = helloGreeting.value
        s.log.info(g)
      }
    )

}
