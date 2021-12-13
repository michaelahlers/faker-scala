import com.amazonaws.auth.AWSCredentialsProviderChain

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import fm.sbt.S3URLHandler

ThisBuild / publishMavenStyle := false

ThisBuild / publishTo := {
  implicit val patterns: Patterns = Resolver.ivyStylePatterns

  sys.env.get("PUBLISH_TO_URL").map(url) match {

    case Some(publishToUrl) =>
      val name = s"Environment (PUBLISH_TO_URL=$publishToUrl)"
      Some(IO.urlAsFile(publishToUrl)
        .map(Resolver.file(name, _))
        .getOrElse(Resolver.url(name, publishToUrl)))

    case None =>
      val name = "Ahlers Consulting Artifacts (public)"
      val publishToUrl = url("s3://ahlers-consulting-artifacts-public.s3.amazonaws.com/")
      Some(Resolver.url(name, publishToUrl))

  }
}

ThisBuild / packageDoc / publishArtifact := false

ThisBuild / doc / sources := Nil

s3CredentialsProvider := { (bucket: String) =>
  new AWSCredentialsProviderChain(
    new EnvironmentVariableCredentialsProvider(),
    new ProfileCredentialsProvider("ahlers-consulting-artifacts-public"))
}
