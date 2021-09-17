lazy val `faker-heise-name-dictionary` =
  RootProject {
    val path: File =
      file(".") /
        "plugins" /
        "heise-name-dictionary"

    uri(s"""file:${path.getAbsolutePath}""")
  }

lazy val `faker-project` =
  project
    .in(file("."))
    .aggregate(`faker-heise-name-dictionary`)
    .dependsOn(`faker-heise-name-dictionary`)
