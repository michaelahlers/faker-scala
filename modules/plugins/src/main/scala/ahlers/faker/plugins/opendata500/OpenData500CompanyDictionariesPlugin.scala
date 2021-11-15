package ahlers.faker.plugins.opendata500

import sbt.Keys._
import sbt._

import scala.util.Try

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object OpenData500CompanyDictionariesPlugin extends AutoPlugin {

  /** A quick-and-dirty [[URL]] factory, intended to streamline use of [[sun.net.www.protocol.classpath.Handler]] without manipulating the runtime. */
  private def url(location: String): URL =
    Try(new URL(location))
      .getOrElse(new URL(null, location, sun.net.www.protocol.classpath.Handler))

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.opendata500.com/us/ Open Data 500: United States Companies]]
     */
    val openData500UsCompanyDictionarySourceUrl = settingKey[URL]("""Location of the States companies dictionary.""")

    /**
     * @see [[https://www.opendata500.com/kr/ Open Data 500: Korean Companies]]
     */
    val openData500KrCompanyDictionarySourceUrl = settingKey[URL]("""Location of the Korean companies dictionary.""")

    val openData500CompanyDictionaryDownloadDirectory = settingKey[File]("Destination of downloaded company dictionaries, typically rooted in a task temporary directory, cleaned up after completion.")

    val openData500CompanyOutputFormat = settingKey[DictionaryOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val openData500CompanyOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val downloadOpenData500UsCompanyDictionaryFile = taskKey[File]("Fetches the United States companies dictionary file from the original source.")

    val downloadOpenData500KoreanCompanyDictionaryFile = taskKey[File]("Fetches the Korean companies dictionary file from the original source.")

    val readOpenData500CompanyEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses all company dictionary entries obtained from downloads.")

    val writeOpenData500CompanyEntries = taskKey[Seq[File]]("Writes all companies dictionary entries in the specified output format and directory, serves as a resource generator.")

  }

  import autoImport._

  /** @todo Consider restoring from authoritative source. */
  private val usCompanySourceUrlSetting: Setting[URL] =
    openData500UsCompanyDictionarySourceUrl :=
      //url("https://www.opendata500.com/us/download/us_companies.csv")
      url("classpath:www.opendata500.com/us/download/us_companies.csv")

  /** @todo Consider restoring from authoritative source. */
  private val krCompanySourceUrlSetting: Setting[URL] =
    openData500KrCompanyDictionarySourceUrl :=
      //url("https://www.opendata500.com/kr/download/kr_companies.csv")
      url("classpath:www.opendata500.com/kr/download/kr_companies.csv")

  private val downloadDirectorySetting: Setting[File] =
    openData500CompanyDictionaryDownloadDirectory :=
      taskTemporaryDirectory.value /
        "www.opendata500.com" /
        "download"

  private val outputFormatSetting: Setting[DictionaryOutputFormat] =
    openData500CompanyOutputFormat :=
      DictionaryOutputFormat.Csv

  private val outputDirectorySetting: Setting[File] =
    openData500CompanyOutputDirectory :=
      (Compile / resourceManaged).value /
        "www.opendata500.com"

  private val downloadLastNameDictionaryFileTask: Setting[Task[File]] =
    downloadOpenData500UsCompanyDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = openData500UsCompanyDictionarySourceUrl.value
      val downloadDirectory = openData500CompanyDictionaryDownloadDirectory.value
      val downloadFile = downloadDirectory / sourceUrl.getFile

      val dictionaryIO: DictionaryIO = DictionaryIO.using(logger)

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadFile = downloadFile)

      dictionaryFile
    }

  private val downloadCompanyDictionaryFileTask: Setting[Task[File]] =
    downloadOpenData500KoreanCompanyDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = openData500KrCompanyDictionarySourceUrl.value
      val downloadDirectory = openData500CompanyDictionaryDownloadDirectory.value
      val downloadFile = downloadDirectory / sourceUrl.getFile

      val dictionaryIO: DictionaryIO = DictionaryIO.using(logger)

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadFile = downloadFile)

      dictionaryFile
    }

  private val readCompanyEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    readOpenData500CompanyEntries := {
      val logger = streams.value.log

      val parseEntries: DictionaryEntriesReader =
        DictionaryEntriesReader.using()

      val dictionaryEntries: Seq[DictionaryEntry] = {
        import better.files._
        (for {
          krCompanyEntries <-
            openData500KrCompanyDictionarySourceUrl.value
              .openStream()
              .autoClosed
              .map(parseEntries(_))
          usCompanyEntries <-
            openData500UsCompanyDictionarySourceUrl.value
              .openStream()
              .autoClosed
              .map(parseEntries(_))
        } yield krCompanyEntries ++ usCompanyEntries)
          .get()
      }

      dictionaryEntries
    }

  private val writeCompanyEntriesTask: Setting[Task[Seq[File]]] =
    writeOpenData500CompanyEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = readOpenData500CompanyEntries.value
      val outputDirectory: File = openData500CompanyOutputDirectory.value

      val writeEntries: DictionaryEntriesWriter =
        openData500CompanyOutputFormat.value match {
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
      usCompanySourceUrlSetting,
      krCompanySourceUrlSetting,
      downloadDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      downloadLastNameDictionaryFileTask,
      downloadCompanyDictionaryFileTask,
      readCompanyEntriesTask,
      writeCompanyEntriesTask,
      Compile / resourceGenerators += writeOpenData500CompanyEntries
    )

}
