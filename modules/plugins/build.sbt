//import org.scalafmt.sbt.ScalafmtPlugin

enablePlugins(SbtPlugin)

configs(IntegrationTest)
Defaults.itSettings
//inConfig(IntegrationTest)(ScalafmtPlugin.scalafmtConfigSettings)
