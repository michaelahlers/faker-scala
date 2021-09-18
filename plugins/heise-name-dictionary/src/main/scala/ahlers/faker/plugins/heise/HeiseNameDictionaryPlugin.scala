package ahlers.faker.plugins.heise

import sbt.Keys._
import sbt._

import java.nio.charset.StandardCharsets
import scala.collection.convert.ImplicitConversionsToScala._

object HeiseNameDictionaryPlugin extends AutoPlugin {

  object autoImport {

    val heiseNameDictionaryArchiveSourceUrl = settingKey[URL]("greeting")

    val heiseNameDictionaryDownloadDirectory = settingKey[File]("greeting")

    val heiseNameDictionaryFileName = settingKey[String]("greeting")

    val downloadHeiseNameDictionaryFile = taskKey[File]("")

    val hello = taskKey[Unit]("say hello")

  }

  import autoImport._

  override val globalSettings =
    Seq(
      heiseNameDictionaryArchiveSourceUrl :=
        url("ftp://ftp.heise.de/pub/ct/listings/0717-182.zip"),
      heiseNameDictionaryDownloadDirectory :=
        taskTemporaryDirectory.value /
          "ftp.heise.de" /
          "pub" /
          "ct" /
          "listings",
      heiseNameDictionaryFileName :=
        "nam_dict.txt"
    )

  override val projectSettings =
    Seq(
      downloadHeiseNameDictionaryFile := {
        val logger = streams.value.log

        val sourceUrl = heiseNameDictionaryArchiveSourceUrl.value
        val downloadDirectory = heiseNameDictionaryDownloadDirectory.value
        val dictionaryFileName = heiseNameDictionaryFileName.value

        HeiseNameDictionaryUtilities
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory,
            dictionaryFileName = dictionaryFileName,
            logger = logger)
      },
      hello := {
        val log = streams.value.log
        val dictionaryFile = downloadHeiseNameDictionaryFile.value

        HeiseNameDictionaryUtilities
          .classifiedNames(dictionaryFile)
          .take(10)
          .map(_.toString)
          .foreach(log.info(_))

      }
    )

}
