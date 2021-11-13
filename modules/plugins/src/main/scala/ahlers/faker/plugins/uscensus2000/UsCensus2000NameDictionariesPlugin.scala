package ahlers.faker.plugins.uscensus2000

import sbt.Keys._
import sbt._

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object UsCensus2000NameDictionariesPlugin extends AutoPlugin {

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.census.gov/topics/population/genealogy/data/2000_surnames.html United States Census Bureau: Frequently Occurring Surnames from the Census 2000]]
     */
    val usCensus2000SurnameDictionarySourceUrl = settingKey[URL]("""Location of the surnames names dictionary.""")

    val usCensus2000NameDictionariesDownloadDirectory = settingKey[File]("Destination of downloaded name dictionaries, typically rooted in a task temporary directory, cleaned up after completion.")

    val usCensus2000NameDictionariesOutputFormat = settingKey[DictionaryOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val usCensus2000NameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val downloadUsCensus2000SurnameDictionaryFile = taskKey[File]("Fetches the surname dictionary file from the original source.")

    val readUsCensus2000DictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses all name dictionary entries obtained from downloads.")

    val writeUsCensus2000DictionaryEntries = taskKey[Seq[File]]("Writes all name dictionary entries in the specified output format and directory, serves as a resource generator.")

  }

  import autoImport._

  private val nameDictionarySourceUrlSetting: Setting[URL] =
    usCensus2000SurnameDictionarySourceUrl :=
      url("https://www2.census.gov/topics/genealogy/2000surnames/names.zip")

  private val downloadDirectorySetting: Setting[File] =
    usCensus2000NameDictionariesDownloadDirectory :=
      taskTemporaryDirectory.value /
        "www2.census.gov" /
        "topics" /
        "genealogy" /
        "2000surnames"

  private val outputFormatSetting: Setting[DictionaryOutputFormat] =
    usCensus2000NameDictionariesOutputFormat :=
      DictionaryOutputFormat.Csv

  private val outputDirectorySetting: Setting[File] =
    usCensus2000NameDictionaryOutputDirectory :=
      (Compile / resourceManaged).value /
        "www2.census.gov" /
        "topics" /
        "genealogy" /
        "2000surnames"

  private val downloadLastNameDictionaryFileTask: Setting[Task[File]] =
    downloadUsCensus2000SurnameDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = usCensus2000SurnameDictionarySourceUrl.value
      val downloadDirectory = usCensus2000NameDictionariesDownloadDirectory.value
      val downloadFile = downloadDirectory / sourceUrl.getFile

      val dictionaryIO: DictionaryIO = DictionaryIO.using(logger)

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory,
            dictionaryFileName = "app_c.csv")

      dictionaryFile
    }

  private val readDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    readUsCensus2000DictionaryEntries := {
      val logger = streams.value.log

      val surnameFile: File = downloadUsCensus2000SurnameDictionaryFile.value

      val parseEntries: DictionaryEntriesReader =
        DictionaryEntriesReader.using(DictionaryEntryParser.default)

      val dictionaryEntries: Seq[DictionaryEntry] =
        parseEntries(Usage.Sur, surnameFile)

      dictionaryEntries
    }

  private val writeDictionaryEntriesTask: Setting[Task[Seq[File]]] =
    writeUsCensus2000DictionaryEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = readUsCensus2000DictionaryEntries.value
      val outputDirectory: File = usCensus2000NameDictionaryOutputDirectory.value

      val writeEntries: DictionaryEntriesWriter =
        usCensus2000NameDictionariesOutputFormat.value match {
          case DictionaryOutputFormat.Csv =>
            DictionaryEntriesCsvWriter.using(
              outputDirectory = outputDirectory,
              logger = logger
            )
        }

      val outputFiles: Seq[File] =
        writeEntries(dictionaryEntries.toIndexedSeq)

      outputFiles
    }

  override val projectSettings =
    Seq(
      nameDictionarySourceUrlSetting,
      downloadDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      downloadLastNameDictionaryFileTask,
      readDictionaryEntriesTask,
      writeDictionaryEntriesTask,
      Compile / resourceGenerators += writeUsCensus2000DictionaryEntries
    )

}
