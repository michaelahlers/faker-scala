ThisBuild / scalaVersion := "2.12.15"
ThisBuild / crossScalaVersions += "2.13.6"

ThisBuild / libraryDependencies ++=
  "com.softwaremill.diffx" %% "diffx-scalatest-must" % "0.6.0" % Test ::
    "com.softwaremill.diffx" %% "diffx-scalatest-should" % "0.6.0" % Test ::
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.14.2" % Test ::
    "org.scalamock" %% "scalamock" % "5.1.0" % Test ::
    "org.scalatest" %% "scalatest" % "3.2.10" % Test ::
    "org.scalatestplus" %% "scalatestplus-scalacheck" % "1.0.0-M2" % Test ::
    Nil
