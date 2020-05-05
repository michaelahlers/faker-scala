scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 13)) =>
      "-Ymacro-annotations" ::
        Nil
    case _ =>
      Nil
  }
}
