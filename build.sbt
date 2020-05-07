lazy val `faker` =
  (project in file("."))
    .aggregate(`internet`)

lazy val `internet` = project in file("modules") / "internet"

publish / skip := true

ThisBuild / scalafmtOnCompile := true
