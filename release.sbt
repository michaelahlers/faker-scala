import sbtrelease.ReleaseStateTransformations

lazy val `faker-plugins` =
  project
    .in(file("modules") / "plugins")

releaseCrossBuild := true

releaseProcess ~= (_.flatMap {
  case step if step == ReleaseStateTransformations.publishArtifacts =>
    Seq(
      ReleaseStateTransformations.publishArtifacts,
      releaseStepTask(`faker-plugins` / publish): ReleaseStep
    )
  case step =>
    Seq(step)
})
