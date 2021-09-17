lazy val `faker` =
  project
    .in(file("."))
    .aggregate(
      `faker-datasets`,
      `faker-scalacheck`
    )

lazy val `faker-heise-name-dictionary` =
  project
    .in(file("plugins") / "heise-name-dictionary")

lazy val `faker-datasets` =
  project
    .in(file("modules") / "datasets")

lazy val `faker-scalacheck` =
  project
    .in(file("modules") / "scalacheck")
    .dependsOn(`faker-datasets`)

publish / skip := true

//ThisBuild / scalafmtOnCompile := true

ThisBuild / Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
