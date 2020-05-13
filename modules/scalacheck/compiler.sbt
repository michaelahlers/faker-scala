scalacOptions --=
  "-Wunused:implicits" ::
    "-Wunused:imports" ::
    "-Wunused:locals" ::
    "-Wunused:params" ::
    "-Wunused:privates" ::
    "-Ywarn-unused-import" ::
    "-Ywarn-unused:implicits" ::
    "-Ywarn-unused:imports" ::
    "-Ywarn-unused:locals" ::
    "-Ywarn-unused:params" ::
    "-Ywarn-unused:privates" ::
    Nil

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 13)) =>
      "-Ymacro-annotations" ::
        Nil
    case _ =>
      Nil
  }
}
