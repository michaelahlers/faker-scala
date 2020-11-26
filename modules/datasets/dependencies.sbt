libraryDependencies ++=
  "com.nrinaudo" %% "kantan.csv" % "0.6.0" ::
    "com.nrinaudo" %% "kantan.csv-generic" % "0.6.0" ::
    "com.nrinaudo" %% "kantan.csv-refined" % "0.6.0" ::
    "eu.timepit" %% "refined" % "0.9.15" ::
    "eu.timepit" %% "refined-scalacheck" % "0.9.15" ::
    "eu.timepit" %% "refined-shapeless" % "0.9.15" ::
    "io.estatico" %% "newtype" % "0.4.4" ::
    "org.typelevel" %% "cats-core" % "2.3.0" ::
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
