name := "faker"
description := "Realistic sample value generators for Scala."

ThisBuild / organization := "ahlers.faker"
ThisBuild / organizationName := "Michael Ahlers"

// ThisBuild / homepage := Some(new URL("http://faker-scala.github.io"))
ThisBuild / startYear := Some(2020)

ThisBuild / developers :=
  Developer("michaelahlers", "Michael Ahlers", "michael@ahlers.consulting", url("http://github.com/michaelahlers")) ::
    Nil

ThisBuild / scmInfo :=
  Some(ScmInfo(
    browseUrl = url("https://github.com/michaelahlers/faker-scala"),
    connection = "https://github.com/michaelahlers/faker-scala.git",
    devConnection = Some("git@github.com:michaelahlers/faker-scala.git")
  ))

ThisBuild / licenses += "MIT" -> new URL("http://opensource.org/licenses/MIT")
