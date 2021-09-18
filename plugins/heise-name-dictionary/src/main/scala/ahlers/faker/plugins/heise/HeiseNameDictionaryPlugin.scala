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
      heiseNameDictionaryFileName := "nam_dict.txt"
    )

  override val projectSettings =
    Seq(
      downloadHeiseNameDictionaryFile := {
        val log = streams.value.log

        val sourceUrl = heiseNameDictionaryArchiveSourceUrl.value
        val downloadDirectory = heiseNameDictionaryDownloadDirectory.value

        val downloadFiles =
          IO.unzipURL(
            sourceUrl,
            downloadDirectory)

        log.info(s"""Extracted ${downloadFiles.size} files from archive "$sourceUrl" to "$downloadDirectory":""")

        downloadFiles
          .map(_
            .toPath
            .drop(downloadDirectory
              .toPath
              .size)
            .reduce(_.resolve(_))
            .toString)
          .foreach(log.info(_))

        val dictionaryFileName = heiseNameDictionaryFileName.value
        val dictionaryFile =
          downloadFiles
            .find(_.getName() == dictionaryFileName)
            .getOrElse(throw new MessageOnlyException(s"""File "$dictionaryFileName" wasn't found in archive "$sourceUrl"."""))

        log.info(s"""Heise name dictionary saved to "$dictionaryFile".""")

        dictionaryFile
      },
      hello := {
        val log = streams.value.log
        val dictionaryFile = downloadHeiseNameDictionaryFile.value

        IO.readLines(dictionaryFile, StandardCharsets.ISO_8859_1)
          .take(10)
          .foreach(log.info(_))

      }
    )

}
