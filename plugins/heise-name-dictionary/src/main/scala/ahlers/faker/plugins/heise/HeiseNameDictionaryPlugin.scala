package ahlers.faker.plugins.heise

import com.typesafe.config.ConfigFactory
import sbt.Keys._
import sbt._
import scala.collection.convert.ImplicitConversionsToScala._
import net.ceedubs.ficus.Ficus._

object HeiseNameDictionaryPlugin extends AutoPlugin {

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    val heiseNameDictionaryArchiveSourceUrl = settingKey[URL]("greeting")

    val heiseNameDictionaryDownloadDirectory = settingKey[File]("greeting")

    val heiseNameDictionaryFileName = settingKey[String]("greeting")

    val downloadHeiseNameDictionaryFile = taskKey[File]("")

    val loadHeiseNameDictionaryRegions = taskKey[Set[Region]]("")

    val loadHeiseNameDictionaryClassifiedNames = taskKey[Iterator[ClassifiedName]]("say hello")

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

        DictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory,
            dictionaryFileName = dictionaryFileName,
            logger = logger)
      },
      loadHeiseNameDictionaryRegions := {
        ConfigFactory
          .load(getClass.getClassLoader)
          .as[Set[Region]]("regions")
      },
      loadHeiseNameDictionaryClassifiedNames := {
        val dictionaryFile = downloadHeiseNameDictionaryFile.value
        val regions = loadHeiseNameDictionaryRegions.value

        DictionaryParsing
          .classifiedNames(
            dictionaryFile = dictionaryFile,
            regions = regions)
      }
    )

}
