import com.amazonaws.auth.AWSCredentialsProviderChain

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import fm.sbt.S3URLHandler

ThisBuild / publishMavenStyle := false
ThisBuild / publishTo := Some(Resolver.url("Ahlers Consulting Artifacts (public)", url("s3://ahlers-consulting-artifacts-public.s3.amazonaws.com/"))(Resolver.ivyStylePatterns))
ThisBuild / packageDoc / publishArtifact := false
ThisBuild / doc / sources := Nil

s3CredentialsProvider := { (bucket: String) =>
  new AWSCredentialsProviderChain(
    new EnvironmentVariableCredentialsProvider(),
    new ProfileCredentialsProvider("ahlers-consulting-artifacts-public"))
}
