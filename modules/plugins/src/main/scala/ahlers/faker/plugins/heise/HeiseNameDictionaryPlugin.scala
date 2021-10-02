package ahlers.faker.plugins.heise

import sbt.Keys._
import sbt._

import java.nio.charset.StandardCharsets

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

    /** @todo Set description. */
    val heiseNameDictionaryOutputFormat = settingKey[DictionaryEntriesOutputFormat]("")

    /** @todo Set description. */
    val heiseNameDictionaryOutputDirectory = settingKey[File]("")

    /** @todo Set description. */
    val heiseNameDictionaryResourceDirectory = settingKey[File]("")

    val downloadHeiseNameDictionaryFile = taskKey[File]("Fetches and extracts the dictionary file from the original source.")

    val loadHeiseNameDictionaryRegions = taskKey[Seq[Region]]("Loads region definitions from configuration.")

    val loadHeiseNameDictionaryEntries = taskKey[Iterator[DictionaryEntry]]("Loads and parses the dictionary to classified name models, suitable for serialization to standard formats.")

    /** @todo Set description. */
    val writeHeiseNameDictionaryEntries = taskKey[Seq[File]]("")

    /** @todo Set description. */
    val generateHeiseNameDictionaryEntries = taskKey[Seq[File]]("")

  }

  import autoImport._

  private val archiveSourceUrlSetting: Setting[URL] =
    heiseNameDictionaryArchiveSourceUrl :=
      url("ftp://ftp.heise.de/pub/ct/listings/0717-182.zip")

  private val downloadDirectorySetting: Setting[File] =
    heiseNameDictionaryDownloadDirectory :=
      taskTemporaryDirectory.value /
        "ftp.heise.de" /
        "pub" /
        "ct" /
        "listings"

  private val fileNameSetting: Setting[String] =
    heiseNameDictionaryFileName :=
      "nam_dict.txt"

  private val outputFormatSetting: Setting[DictionaryEntriesOutputFormat] =
    heiseNameDictionaryOutputFormat :=
      DictionaryEntriesOutputFormat.Csv

  private val outputDirectorySetting: Setting[sbt.File] =
    heiseNameDictionaryOutputDirectory :=
      taskTemporaryDirectory.value /
        "ftp.heise.de" /
        "pub" /
        "ct" /
        "listings"

  private val resourceDirectorySetting: Setting[File] =
    heiseNameDictionaryResourceDirectory :=
      (Compile / resourceManaged).value /
        "ftp.heise.de" /
        "pub" /
        "ct" /
        "listings"

  private val downloadFileTask: Setting[Task[File]] =
    downloadHeiseNameDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = heiseNameDictionaryArchiveSourceUrl.value
      val downloadDirectory = heiseNameDictionaryDownloadDirectory.value
      val dictionaryFileName = heiseNameDictionaryFileName.value

      val dictionaryIO = DictionaryIO(logger)

      val dictionaryFile =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory,
            dictionaryFileName = dictionaryFileName)

      dictionaryFile
    }

  private val loadRegionsTask: Setting[Task[Seq[Region]]] =
    loadHeiseNameDictionaryRegions := {
      val logger = streams.value.log
      val regionIO = RegionIO(logger)
      val regions = regionIO.loadRegions()
      regions
    }

  private val loadEntriesTask: Setting[Task[Iterator[DictionaryEntry]]] =
    loadHeiseNameDictionaryEntries := {
      val dictionaryFile = downloadHeiseNameDictionaryFile.value
      val regions = loadHeiseNameDictionaryRegions.value

      val dictionaryLines: Iterator[DictionaryLine] =
        IO.readLines(dictionaryFile, StandardCharsets.ISO_8859_1)
          .map(DictionaryLine(_))
          .toIterator

      val parseDictionaryEntries =
        DictionaryEntriesParser.using(regions.toIndexedSeq)

      val dictionaryEntries: Iterator[DictionaryEntry] =
        parseDictionaryEntries(dictionaryLines)

      dictionaryEntries
    }

  private val writeEntriesTask: Setting[Task[Seq[File]]] =
    writeHeiseNameDictionaryEntries := {
      val logger = streams.value.log
      val outputFormat = heiseNameDictionaryOutputFormat.value
      val outputDirectory = heiseNameDictionaryOutputDirectory.value
      val classifiedNames = loadHeiseNameDictionaryEntries.value
      val writeClassifiedNames: DictionaryEntriesWriter =
        outputFormat match {
          case DictionaryEntriesOutputFormat.Csv =>
            DictionaryEntriesCsvWriter(
              outputDirectory = outputDirectory,
              logger = logger)
        }

      val outputFiles = writeClassifiedNames(classifiedNames)
      outputFiles
    }

  private val generateEntriesTask: Setting[Task[Seq[File]]] =
    generateHeiseNameDictionaryEntries := {
      val logger = streams.value.log
      val outputFiles = writeHeiseNameDictionaryEntries.value
      val resourceDirectory = heiseNameDictionaryResourceDirectory.value
      val resourceFiles =
        outputFiles
          .map(_.getName)
          .map(resourceDirectory / _)

      resourceDirectory.mkdirs()
      IO.move(outputFiles.zip(resourceFiles))

      logger.info(
        """Generated files %s in directory "%s"."""
          .format(
            outputFiles
              .map(_.getName)
              .mkString("\"", "\", \"", "\""),
            resourceDirectory
          ))

      resourceFiles
    }

  override val projectSettings =
    Seq(
      archiveSourceUrlSetting,
      downloadDirectorySetting,
      fileNameSetting,
      outputFormatSetting,
      outputDirectorySetting,
      resourceDirectorySetting,
      downloadFileTask,
      loadRegionsTask,
      loadEntriesTask,
      writeEntriesTask,
      generateEntriesTask,
      Compile / resourceGenerators +=
        generateHeiseNameDictionaryEntries
    )

}
