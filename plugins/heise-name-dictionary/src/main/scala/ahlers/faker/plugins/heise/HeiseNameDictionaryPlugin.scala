package ahlers.faker.plugins.heise

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import sbt.Keys._
import sbt._

object HeiseNameDictionaryPlugin extends AutoPlugin {

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.heise.de/ct/ftp/07/17/182/ Magazin für Computertechnik: ''40 000 Namen'']]
     */
    val heiseNameDictionaryArchiveSourceUrl = settingKey[URL]("""Location of the original archive, described by Magazin für Computertechnik's "40 000 Namen".""")

    val heiseNameDictionaryDownloadDirectory = settingKey[File]("Destination of downloaded and extracted archive.")

    val heiseNameDictionaryFileName = settingKey[String]("Names the file inside the dictionary archive containing classified names.")

    val downloadHeiseNameDictionaryFile = taskKey[File]("Fetches and extracts the dictionary file from the original source.")

    val loadHeiseNameDictionaryRegions = taskKey[Set[Region]]("Loads region definitions from configuration.")

    val loadHeiseNameDictionaryClassifiedNames = taskKey[Iterator[ClassifiedName]]("Loads and parses the dictionary to classified name models, suitable for serialization to standard formats.")

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
