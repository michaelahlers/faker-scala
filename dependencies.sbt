ThisBuild / scalaVersion := "2.12.11"
ThisBuild / crossScalaVersions += "2.13.2"

ThisBuild / libraryDependencies ++=
  "com.softwaremill.diffx" %% "diffx-scalatest" % "0.3.30" % Test ::
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.14.2" % Test ::
    "org.scalamock" %% "scalamock" % "5.0.0" % Test ::
    "org.scalatest" %% "scalatest" % "3.2.5" % Test ::
    "org.scalatestplus" %% "scalatestplus-scalacheck" % "1.0.0-M2" % Test ::
    Nil
