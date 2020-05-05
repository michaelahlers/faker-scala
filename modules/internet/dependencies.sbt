libraryDependencies ++=
  "eu.timepit" %% "refined" % "0.9.14" ::
    "eu.timepit" %% "refined-scalacheck" % "0.9.14" ::
    "eu.timepit" %% "refined-shapeless" % "0.9.14" ::
    "io.estatico" %% "newtype" % "0.4.3" ::
    "org.scalacheck" %% "scalacheck" % "1.14.3" ::
    Nil

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12)) =>
      compilerPlugin(("org.scalamacros" % "paradise" % "2.1.1").cross(CrossVersion.full)) ::
        Nil
    case _ =>
      Nil
  }
}
