ThisBuild / scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12)) =>
      "-Ypartial-unification" ::
        Nil
    case _ =>
      Nil
  }
}
