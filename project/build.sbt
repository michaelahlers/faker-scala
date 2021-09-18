lazy val `faker-heise-name-dictionary` =
  RootProject(
    IO.toURI((file(".") /
      "plugins" /
      "heise-name-dictionary")
      .getAbsoluteFile))

lazy val `faker-project` =
  project
    .in(file("."))
    .aggregate(`faker-heise-name-dictionary`)
    .dependsOn(`faker-heise-name-dictionary`)
