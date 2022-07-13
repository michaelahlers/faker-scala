scalaVersion := "2.12.16"
crossScalaVersions := Nil

libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"

libraryDependencies ++=
  "com.iheart" %% "ficus" % "1.5.1" ::
    "com.typesafe" % "config" % "1.4.1" ::
    Nil

libraryDependencies ++=
  "com.nrinaudo" %% "kantan.csv" % "0.6.0" ::
    "com.nrinaudo" %% "kantan.csv-generic" % "0.6.0" ::
    "com.nrinaudo" %% "kantan.csv-refined" % "0.6.0" ::
    Nil

libraryDependencies ++=
  "com.softwaremill.diffx" %% "diffx-scalatest-must" % "0.7.0" % IntegrationTest ::
    "com.softwaremill.diffx" %% "diffx-scalatest-should" % "0.7.0" % IntegrationTest ::
    "org.scalatest" %% "scalatest" % "3.2.10" % IntegrationTest ::
    Nil
