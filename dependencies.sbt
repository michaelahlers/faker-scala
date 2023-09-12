ThisBuild / scalaVersion := "2.12.15"
ThisBuild / crossScalaVersions ++=
  "2.13.7" ::
    "3.1.0" ::
    Nil

ThisBuild / libraryDependencies ++=
  "com.softwaremill.diffx" %% "diffx-scalatest-must" % "0.7.0" % Test ::
    "com.softwaremill.diffx" %% "diffx-scalatest-should" % "0.7.0" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.15.4" % Test ::
    "org.scalatest" %% "scalatest" % "3.2.17" % Test ::
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % Test ::
    Nil
