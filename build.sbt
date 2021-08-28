lazy val `faker` =
  (project in file("."))
    .aggregate(
      `datasets`,
      `scalacheck`
    )

lazy val `datasets` = project in file("modules") / "datasets"

lazy val `scalacheck` =
  (project in file("modules") / "scalacheck")
    .dependsOn(`datasets`)

publish / skip := true

//ThisBuild / scalafmtOnCompile := true

ThisBuild / Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
