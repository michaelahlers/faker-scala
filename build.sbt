lazy val `faker` =
  (project in file("."))
    .aggregate(
      `internet`,
      `social`
    )

lazy val `internet` = project in file("modules") / "internet"
lazy val `social` = project in file("modules") / "social"

publish / skip := true

ThisBuild / scalafmtOnCompile := true
