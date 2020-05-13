lazy val `faker` =
  (project in file("."))
    .aggregate(
      `samples`,
      `scalacheck`
    )

lazy val `samples` = project in file("modules") / "samples"
lazy val `scalacheck` = project in file("modules") / "scalacheck"

publish / skip := true

ThisBuild / scalafmtOnCompile := true
