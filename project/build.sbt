lazy val `faker-plugins` =
  RootProject {
    val path: File =
      file(".") /
        "modules" /
        "plugins"

    uri(s"""file:${path.getAbsolutePath}""")
  }

lazy val `faker-project` =
  project
    .in(file("."))
    .aggregate(`faker-plugins`)
    .dependsOn(`faker-plugins`)
