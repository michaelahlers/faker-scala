lazy val `faker` =
  (project in file("."))
    .aggregate(
      `internet`,
      `refined-extras`
    )

lazy val `internet` =
  (project in file("modules") / "internet")
    .dependsOn(`refined-extras`)

lazy val `refined-extras` = project in file("modules") / "refined-extras"

publish / skip := true

ThisBuild / scalafmtOnCompile := true
