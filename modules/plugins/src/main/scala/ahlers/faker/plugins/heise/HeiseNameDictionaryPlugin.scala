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
    val heiseNameDictionaryArchiveSourceUrl = settingKey[URL]("""Location of the original archive, described by "Magazin für Computertechnik's "40 000 Namen".""")

    val heiseNameDictionaryDownloadDirectory = settingKey[File]("Destination of downloaded and extracted archive, typically rooted in a task temporary directory, cleaned up after completion.")

    val heiseNameDictionaryFileName = settingKey[String]("Names the file inside the dictionary archive containing classified names.")

    val heiseNameDictionaryOutputFormat = settingKey[DictionaryEntriesOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val heiseNameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val downloadHeiseNameDictionaryFile = taskKey[File]("Fetches and extracts the dictionary file from the original source.")

    val loadHeiseNameDictionaryRegions = taskKey[Seq[Region]]("Loads region definitions from configuration.")

    val loadHeiseNameDictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses the dictionary to models suitable for encoding to standard formats.")

    val writeHeiseNameDictionaryEntries = taskKey[Seq[File]]("Writes entries in the specified output format.")

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

  private val outputDirectorySetting: Setting[File] =
    heiseNameDictionaryOutputDirectory :=
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

  private val loadEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    loadHeiseNameDictionaryEntries := {
      val dictionaryFile = downloadHeiseNameDictionaryFile.value
      val regions = loadHeiseNameDictionaryRegions.value

      val dictionaryLines: Seq[DictionaryLine] =
        IO.readLines(dictionaryFile, StandardCharsets.ISO_8859_1)
          .map(DictionaryLine(_))

      val parseDictionaryEntries =
        DictionaryEntriesParser.using(regions.toIndexedSeq)

      val dictionaryEntries: Seq[DictionaryEntry] =
        parseDictionaryEntries(dictionaryLines
          .toIndexedSeq)

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

      val outputFiles =
        writeClassifiedNames(classifiedNames
          .toIndexedSeq)

      logger.info(
        """Generated files %s in directory "%s"."""
          .format(
            outputFiles
              .map(_.getName)
              .mkString("\"", "\", \"", "\""),
            outputDirectory
          ))

      outputFiles
    }

  override val projectSettings =
    Seq(
      archiveSourceUrlSetting,
      downloadDirectorySetting,
      fileNameSetting,
      outputFormatSetting,
      outputDirectorySetting,
      downloadFileTask,
      loadRegionsTask,
      loadEntriesTask,
      writeEntriesTask,
      Compile / resourceGenerators += writeHeiseNameDictionaryEntries
    )

}
