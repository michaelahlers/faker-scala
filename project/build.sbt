lazy val `faker-plugins` =
  RootProject(
    IO.toURI((file(".") /
      "modules" /
      "plugins")
      .getAbsoluteFile))

lazy val `faker-project` =
  project
    .in(file("."))
    .aggregate(`faker-plugins`)
    .dependsOn(`faker-plugins`)
