package ahlers.faker.plugins.uscensus1990

import sbt.Keys._
import sbt._

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object UsCensus1990NameDictionariesPlugin extends AutoPlugin {

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.census.gov/topics/population/genealogy/data/1990_census/1990_census_namefiles.html United States Census Bureau: Frequently Occurring Surnames from Census 1990]]
     */
    val usCensus1990LastNameDictionarySourceUrl = settingKey[URL]("""Location of the last names dictionary.""")

    /**
     * @see [[https://www.census.gov/topics/population/genealogy/data/1990_census/1990_census_namefiles.html United States Census Bureau: Frequently Occurring Surnames from Census 1990]]
     */
    val census1990FirstNameFemaleDictionaryFileSourceUrl = settingKey[URL]("""Location of the female first names dictionary.""")

    /**
     * @see [[https://www.census.gov/topics/population/genealogy/data/1990_census/1990_census_namefiles.html United States Census Bureau: Frequently Occurring Surnames from Census 1990]]
     */
    val census1990FirstNameMaleDictionaryFileSourceUrl = settingKey[URL]("""Location of the male first names dictionary.""")

    val census1990NameDictionariesDownloadDirectory = settingKey[File]("Destination of downloaded name dictionaries, typically rooted in a task temporary directory, cleaned up after completion.")

    val census1990NameDictionariesOutputFormat = settingKey[DictionaryOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val usCensus1990NameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val downloadUsCensus1990LastNameDictionaryFile = taskKey[File]("Fetches the last name dictionary file from the original source.")

    val downloadUsCensus1990FemaleFirstNameDictionaryFile = taskKey[File]("Fetches the female first name dictionary file from the original source.")

    val downloadUsCensus1990MaleFirstNameDictionaryFile = taskKey[File]("Fetches the male first name dictionary file from the original source.")

    val loadUsCensus1990LastNameDictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses the last names dictionary to models suitable for encoding to standard formats.")

    val loadUsCensus1990FirstNameFemaleDictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses the female first names dictionary to models suitable for encoding to standard formats.")

    val loadUsCensus1990FirstNameMaleDictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses the male first names dictionary to models suitable for encoding to standard formats.")

    val writeUsCensus1990LastNameDictionaryEntries = taskKey[File]("Writes last name dictionary entries in the specified output format and directory.")

    val writeUsCensus1990FemaleFirstNameDictionaryEntries = taskKey[File]("Writes female first name dictionary entries in the specified output format and directory.")

    val writeUsCensus1990MaleFirstNameDictionaryEntries = taskKey[File]("Writes male first name dictionary entries in the specified output format and directory.")

    val writeUsCensus1990DictionaryEntries = taskKey[Seq[File]]("Writes all name dictionary entries in the specified output format and directory, serves as a resource generator.")

  }

  import autoImport._

  private val lastNameDictionarySourceUrlSetting: Setting[URL] =
    usCensus1990LastNameDictionarySourceUrl :=
      url("http://www2.census.gov/topics/genealogy/1990surnames/dist.all.last")

  private val femaleFirstNameDictionarySourceUrlSetting: Setting[URL] =
    census1990FirstNameFemaleDictionaryFileSourceUrl :=
      url("http://www2.census.gov/topics/genealogy/1990surnames/dist.female.first")

  private val maleFirstNameDictionarySourceUrlSetting: Setting[URL] =
    census1990FirstNameMaleDictionaryFileSourceUrl :=
      url("http://www2.census.gov/topics/genealogy/1990surnames/dist.male.first")

  private val downloadDirectorySetting: Setting[File] =
    census1990NameDictionariesDownloadDirectory :=
      taskTemporaryDirectory.value /
        "www2.census.gov" /
        "topics" /
        "genealogy" /
        "1990surnames"

  private val outputFormatSetting: Setting[DictionaryOutputFormat] =
    census1990NameDictionariesOutputFormat :=
      DictionaryOutputFormat.Csv

  private val outputDirectorySetting: Setting[File] =
    usCensus1990NameDictionaryOutputDirectory :=
      (Compile / resourceManaged).value /
        "www2.census.gov" /
        "topics" /
        "genealogy" /
        "1990surnames"

  private val downloadLastNameDictionaryFileTask: Setting[Task[File]] =
    downloadUsCensus1990LastNameDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = usCensus1990LastNameDictionarySourceUrl.value
      val downloadDirectory = census1990NameDictionariesDownloadDirectory.value

      def dictionaryIO: DictionaryIO = ???

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory)

      dictionaryFile
    }

  private val downloadFemaleFirstNameDictionaryFileTask: Setting[Task[File]] =
    downloadUsCensus1990FemaleFirstNameDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = census1990FirstNameFemaleDictionaryFileSourceUrl.value
      val downloadDirectory = census1990NameDictionariesDownloadDirectory.value

      def dictionaryIO: DictionaryIO = ???

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory)

      dictionaryFile
    }

  private val downloadMaleFirstNameDictionaryFileTask: Setting[Task[File]] =
    downloadUsCensus1990MaleFirstNameDictionaryFile := {
      val logger = streams.value.log

      val sourceUrl = census1990FirstNameMaleDictionaryFileSourceUrl.value
      val downloadDirectory = census1990NameDictionariesDownloadDirectory.value

      def dictionaryIO: DictionaryIO = ???

      val dictionaryFile: File =
        dictionaryIO
          .downloadDictionary(
            sourceUrl = sourceUrl,
            downloadDirectory = downloadDirectory)

      dictionaryFile
    }

  private val loadLastNameDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    loadUsCensus1990LastNameDictionaryEntries := {
      val logger = streams.value.log

      val dictionaryFile: File = downloadUsCensus1990LastNameDictionaryFile.value

      def dictionaryIO: DictionaryIO = ???

      val dictionaryEntries: Seq[DictionaryEntry] =
        dictionaryIO.loadDictionaryEntries(dictionaryFile)

      dictionaryEntries
    }

  private val loadFemaleFirstNameDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    loadUsCensus1990FirstNameFemaleDictionaryEntries := {
      val logger = streams.value.log

      val dictionaryFile: File = downloadUsCensus1990FemaleFirstNameDictionaryFile.value

      def dictionaryIO: DictionaryIO = ???

      val dictionaryEntries: Seq[DictionaryEntry] =
        dictionaryIO.loadDictionaryEntries(dictionaryFile)

      dictionaryEntries
    }

  private val loadMaleFirstNameDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    loadUsCensus1990FirstNameMaleDictionaryEntries := {
      val logger = streams.value.log

      val dictionaryFile: File = downloadUsCensus1990MaleFirstNameDictionaryFile.value

      def dictionaryIO: DictionaryIO = ???

      val dictionaryEntries: Seq[DictionaryEntry] =
        dictionaryIO.loadDictionaryEntries(dictionaryFile)

      dictionaryEntries
    }

  private val writeLastNameDictionaryEntriesTask: Setting[Task[File]] =
    writeUsCensus1990LastNameDictionaryEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = loadUsCensus1990LastNameDictionaryEntries.value
      val outputDirectory: File = usCensus1990NameDictionaryOutputDirectory.value

      def dictionaryIO: DictionaryIO = ???

      val outputFile: File =
        dictionaryIO
          .writeDictionary(
            entries = dictionaryEntries,
            outputDirectory = outputDirectory)

      outputFile
    }

  private val writeFemaleFirstNameDictionaryEntriesTask: Setting[Task[File]] =
    writeUsCensus1990FemaleFirstNameDictionaryEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = loadUsCensus1990FirstNameFemaleDictionaryEntries.value
      val outputDirectory: File = usCensus1990NameDictionaryOutputDirectory.value

      def dictionaryIO: DictionaryIO = ???

      val outputFile: File =
        dictionaryIO
          .writeDictionary(
            entries = dictionaryEntries,
            outputDirectory = outputDirectory)

      outputFile
    }

  private val writeMaleFirstNameDictionaryEntriesTask: Setting[Task[File]] =
    writeUsCensus1990MaleFirstNameDictionaryEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = loadUsCensus1990FirstNameMaleDictionaryEntries.value
      val outputDirectory: File = usCensus1990NameDictionaryOutputDirectory.value

      def dictionaryIO: DictionaryIO = ???

      val outputFile: File =
        dictionaryIO
          .writeDictionary(
            entries = dictionaryEntries,
            outputDirectory = outputDirectory)

      outputFile
    }

  private val writeDictionaryEntriesTask: Setting[Task[Seq[File]]] =
    writeUsCensus1990DictionaryEntries := {
      Seq(
        writeUsCensus1990LastNameDictionaryEntries.value,
        writeUsCensus1990FemaleFirstNameDictionaryEntries.value,
        writeUsCensus1990MaleFirstNameDictionaryEntries.value
      )
    }

  override val projectSettings =
    Seq(
      lastNameDictionarySourceUrlSetting,
      femaleFirstNameDictionarySourceUrlSetting,
      maleFirstNameDictionarySourceUrlSetting,
      downloadDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      downloadLastNameDictionaryFileTask,
      downloadFemaleFirstNameDictionaryFileTask,
      downloadMaleFirstNameDictionaryFileTask,
      loadLastNameDictionaryEntriesTask,
      loadFemaleFirstNameDictionaryEntriesTask,
      loadMaleFirstNameDictionaryEntriesTask,
      writeLastNameDictionaryEntriesTask,
      writeFemaleFirstNameDictionaryEntriesTask,
      writeMaleFirstNameDictionaryEntriesTask,
      Compile / resourceGenerators += writeUsCensus1990DictionaryEntries
    )

}
