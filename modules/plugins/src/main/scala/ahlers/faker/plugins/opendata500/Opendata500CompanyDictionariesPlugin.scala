package ahlers.faker.plugins.opendata500

import sbt.Keys._
import sbt._

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object Opendata500CompanyDictionariesPlugin extends AutoPlugin {

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.opendata500.com/us/ Open Data 500: United States Companies]]
     */
    val opendata500UsCompaniesDictionarySourceUrl = settingKey[URL]("""Location of the States companies dictionary.""")

    /**
     * @see [[https://www.opendata500.com/kr/ Open Data 500: Korean Companies]]
     */
    val opendata500KrCompaniesDictionaryFileSourceUrl = settingKey[URL]("""Location of the Korean companies dictionary.""")

    val opendata500CompanyDictionariesDownloadDirectory = settingKey[File]("Destination of downloaded company dictionaries, typically rooted in a task temporary directory, cleaned up after completion.")

    val opendata500NameDictionariesOutputFormat = settingKey[DictionaryOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val opendata500NameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val downloadOpendata500UsCompaniesDictionaryFile = taskKey[File]("Fetches the United States companies dictionary file from the original source.")

    val downloadOpendata500KoreanNameDictionaryFile = taskKey[File]("Fetches the Korean companies dictionary file from the original source.")

    val readOpendata500DictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses all company dictionary entries obtained from downloads.")

    val writeOpendata500DictionaryEntries = taskKey[Seq[File]]("Writes all companies dictionary entries in the specified output format and directory, serves as a resource generator.")

  }

  import autoImport._

  private val lastNameDictionarySourceUrlSetting: Setting[URL] =
    opendata500UsCompaniesDictionarySourceUrl :=
      url("https://www.opendata500.com/us/download/us_companies.csv")

  private val femaleFirstNameDictionarySourceUrlSetting: Setting[URL] =
    opendata500KrCompaniesDictionaryFileSourceUrl :=
      url("https://www.opendata500.com/kr/download/kr_companies.csv")

  private val downloadDirectorySetting: Setting[File] =
    opendata500CompanyDictionariesDownloadDirectory :=
      taskTemporaryDirectory.value /
        "www.opendata500.com" /
        "download"

  private val outputFormatSetting: Setting[DictionaryOutputFormat] =
    opendata500NameDictionariesOutputFormat :=
      DictionaryOutputFormat.Csv

  private val outputDirectorySetting: Setting[File] =
    opendata500NameDictionaryOutputDirectory :=
      (Compile / resourceManaged).value /
        "www.opendata500.com"

  private val downloadLastNameDictionaryFileTask: Setting[Task[File]] =
    downloadOpendata500UsCompaniesDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = opendata500UsCompaniesDictionarySourceUrl.value
      val downloadDirectory = opendata500CompanyDictionariesDownloadDirectory.value
      val downloadFile = downloadDirectory / sourceUrl.getFile

      val dictionaryIO: DictionaryIO = DictionaryIO.using(logger)

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadFile = downloadFile)

      dictionaryFile
    }

  private val downloadFemaleFirstNameDictionaryFileTask: Setting[Task[File]] =
    downloadOpendata500KoreanNameDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = opendata500KrCompaniesDictionaryFileSourceUrl.value
      val downloadDirectory = opendata500CompanyDictionariesDownloadDirectory.value
      val downloadFile = downloadDirectory / sourceUrl.getFile

      val dictionaryIO: DictionaryIO = DictionaryIO.using(logger)

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadFile = downloadFile)

      dictionaryFile
    }

  private val readDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    readOpendata500DictionaryEntries := {
      val logger = streams.value.log

      val femaleFirstNameFile: File = downloadOpendata500KoreanNameDictionaryFile.value
      val lastNameFile: File = downloadOpendata500UsCompaniesDictionaryFile.value

      val parseEntries: DictionaryEntriesReader =
        DictionaryEntriesReader.using()

      val dictionaryEntries: Seq[DictionaryEntry] =
        parseEntries(femaleFirstNameFile) ++
          parseEntries(lastNameFile)

      dictionaryEntries
    }

  private val writeDictionaryEntriesTask: Setting[Task[Seq[File]]] =
    writeOpendata500DictionaryEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = readOpendata500DictionaryEntries.value
      val outputDirectory: File = opendata500NameDictionaryOutputDirectory.value

      val writeEntries: DictionaryEntriesWriter =
        opendata500NameDictionariesOutputFormat.value match {
          case DictionaryOutputFormat.Csv =>
            DictionaryEntriesCsvWriter.usingDir(
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
      lastNameDictionarySourceUrlSetting,
      femaleFirstNameDictionarySourceUrlSetting,
      downloadDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      downloadLastNameDictionaryFileTask,
      downloadFemaleFirstNameDictionaryFileTask,
      readDictionaryEntriesTask,
      writeDictionaryEntriesTask,
      Compile / resourceGenerators += writeOpendata500DictionaryEntries
    )

}
