lazy val `faker` =
  project
    .in(file("."))
    .aggregate(
      `faker-datasets`,
      `faker-plugins`,
      `faker-scalacheck`
    )
lazy val `faker-datasets` =
  project
    .in(file("modules") / "datasets")

lazy val `faker-plugins` =
  project
    .in(file("modules") / "plugins")

lazy val `faker-scalacheck` =
  project
    .in(file("modules") / "scalacheck")
    .dependsOn(`faker-datasets`)

publish / skip := true

//ThisBuild / scalafmtOnCompile := true

ThisBuild / Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
