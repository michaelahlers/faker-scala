libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"

libraryDependencies ++=
  "com.iheart" %% "ficus" % "1.5.1" ::
    "com.typesafe" % "config" % "1.4.1" ::
    Nil

libraryDependencies ++=
  "com.nrinaudo" %% "kantan.csv" % "0.6.0" ::
    "com.nrinaudo" %% "kantan.csv-generic" % "0.6.0" ::
    "com.nrinaudo" %% "kantan.csv-refined" % "0.6.0" ::
    Nil