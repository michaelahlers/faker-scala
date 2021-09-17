package ahlers.faker.plugins.heise

import sbt.Keys._
import sbt._
import better.files._

import java.io.InputStream
import java.nio.file.Path
import java.util.zip.ZipInputStream
import scala.io.Codec
import scala.io.Source

object HeiseNameDictionaryPlugin extends AutoPlugin {

  case class CharacterEncoding(pattern: String, substitution: String)

  def linesF(archive: ZipInputStream): Dispose[Iterator[String]] = {
    while (!(archive.getNextEntry().getName() == "0717-182/nam_dict.txt")) {}

    Source
      .fromInputStream(archive)(Codec.ISO8859)
      .autoClosed
      .map(_.getLines())
  }

  def linesF(resource: Path): Dispose[Iterator[String]] =
    File(resource)
      .newZipInputStream
      .autoClosed
      .flatMap(linesF(_))

  def linesF(): Dispose[Iterator[String]] =
    Resource
      .getAsStream("ftp.heise.de/pub/ct/listings/0717-182.zip")
      .asZipInputStream()
      .autoClosed
      .flatMap(linesF(_))

  object autoImport {
    //val helloGreeting = settingKey[String]("greeting")
    val hello = taskKey[Unit]("say hello")
  }

  import autoImport._

  //override val globalSettings =
  Seq(
    //helloGreeting := "hi"
  )

  override val projectSettings =
    Seq(
      hello := {
        val log = streams.value.log

        linesF(((Compile / resourceDirectory).value /
          "ftp.heise.de" /
          "pub" /
          "ct" /
          "listings" /
          "0717-182.zip")
          .toPath) { lines =>
          lines
            .take(10)
            .foreach(log.info(_))
        }

      }
    )

}
