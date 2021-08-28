ThisBuild / scalaVersion := "2.12.14"
ThisBuild / crossScalaVersions += "2.13.6"

ThisBuild / libraryDependencies ++=
  "com.softwaremill.diffx" %% "diffx-scalatest" % "0.3.30" % Test ::
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.14.2" % Test ::
    "org.scalamock" %% "scalamock" % "5.0.0" % Test ::
    "org.scalatest" %% "scalatest" % "3.1.4" % Test ::
    "org.scalatestplus" %% "scalatestplus-scalacheck" % "1.0.0-M2" % Test ::
    Nil
