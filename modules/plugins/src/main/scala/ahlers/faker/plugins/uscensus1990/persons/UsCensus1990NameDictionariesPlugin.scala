package ahlers.faker.plugins.uscensus1990.persons

import sbt.Keys._
import sbt._

import scala.util.Try

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object UsCensus1990NameDictionariesPlugin extends AutoPlugin {

  /** A quick-and-dirty [[URL]] factory, intended to streamline use of [[sun.net.www.protocol.classpath.Handler]] without manipulating the runtime. */
  private def url(location: String): URL =
    Try(new URL(location))
      .getOrElse(new URL(null, location, sun.net.www.protocol.classpath.Handler))

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
    val usCensus1990FirstNameFemaleDictionaryFileSourceUrl = settingKey[URL]("""Location of the female first names dictionary.""")

    /**
     * @see [[https://www.census.gov/topics/population/genealogy/data/1990_census/1990_census_namefiles.html United States Census Bureau: Frequently Occurring Surnames from Census 1990]]
     */
    val usCensus1990FirstNameMaleDictionaryFileSourceUrl = settingKey[URL]("""Location of the male first names dictionary.""")

    val usCensus1990NameDictionariesDownloadDirectory = settingKey[File]("Destination of downloaded name dictionaries, typically rooted in a task temporary directory, cleaned up after completion.")

    val usCensus1990NameDictionariesOutputFormat = settingKey[DictionaryOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val usCensus1990NameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val readUsCensus1990DictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses all name dictionary entries obtained from downloads.")

    val writeUsCensus1990DictionaryEntries = taskKey[Seq[File]]("Writes all name dictionary entries in the specified output format and directory, serves as a resource generator.")

  }

  import autoImport._

  /** @todo Consider restoring from authoritative source. */
  private val lastNameDictionarySourceUrlSetting: Setting[URL] =
    usCensus1990LastNameDictionarySourceUrl :=
      //url("https://www2.census.gov/topics/genealogy/1990surnames/dist.all.last")
      url("classpath:www2.census.gov/topics/genealogy/1990surnames/dist.all.last")

  /** @todo Consider restoring from authoritative source. */
  private val femaleFirstNameDictionarySourceUrlSetting: Setting[URL] =
    usCensus1990FirstNameFemaleDictionaryFileSourceUrl :=
      //url("https://www2.census.gov/topics/genealogy/1990surnames/dist.female.first")
      url("classpath:www2.census.gov/topics/genealogy/1990surnames/dist.female.first")

  /** @todo Consider restoring from authoritative source. */
  private val maleFirstNameDictionarySourceUrlSetting: Setting[URL] =
    usCensus1990FirstNameMaleDictionaryFileSourceUrl :=
      //url("https://www2.census.gov/topics/genealogy/1990surnames/dist.male.first")
      url("classpath:www2.census.gov/topics/genealogy/1990surnames/dist.male.first")

  private val downloadDirectorySetting: Setting[File] =
    usCensus1990NameDictionariesDownloadDirectory :=
      taskTemporaryDirectory.value /
        "www2.census.gov" /
        "topics" /
        "genealogy" /
        "1990surnames"

  private val outputFormatSetting: Setting[DictionaryOutputFormat] =
    usCensus1990NameDictionariesOutputFormat :=
      DictionaryOutputFormat.Csv

  private val outputDirectorySetting: Setting[File] =
    usCensus1990NameDictionaryOutputDirectory :=
      (Compile / resourceManaged).value /
        "www2.census.gov" /
        "topics" /
        "genealogy" /
        "1990surnames"

  private val readDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    readUsCensus1990DictionaryEntries := {
      val logger = streams.value.log

      val parseEntries: DictionaryEntriesReader =
        DictionaryEntriesReader.using(DictionaryEntryParser.default)

      val dictionaryEntries: Seq[DictionaryEntry] = {
        import better.files._
        (for {
          lastNameEntries <-
            usCensus1990LastNameDictionarySourceUrl.value
              .openStream()
              .autoClosed
              .map(parseEntries(Usage.Last, _))
          femaleFirstNameEntries <-
            usCensus1990FirstNameFemaleDictionaryFileSourceUrl.value
              .openStream()
              .autoClosed
              .map(parseEntries(Usage.FemaleFirst, _))
          maleFirstNameEntries <-
            usCensus1990FirstNameMaleDictionaryFileSourceUrl.value
              .openStream()
              .autoClosed
              .map(parseEntries(Usage.MaleFirst, _))
        } yield lastNameEntries ++ femaleFirstNameEntries ++ maleFirstNameEntries)
          .get()
      }

      dictionaryEntries
    }

  private val writeDictionaryEntriesTask: Setting[Task[Seq[File]]] =
    writeUsCensus1990DictionaryEntries := {
      val logger = streams.value.log

      val dictionaryEntries: Seq[DictionaryEntry] = readUsCensus1990DictionaryEntries.value
      val outputDirectory: File = usCensus1990NameDictionaryOutputDirectory.value

      outputDirectory.mkdirs()

      val outputFiles =
        usCensus1990NameDictionariesOutputFormat.value match {

          case DictionaryOutputFormat.Csv =>
            val nameFile = outputDirectory / "name.csv"
            val usageFile = outputDirectory / "name-index,usage.csv"

            val writeEntries =
              DictionaryEntriesCsvWriter.using(
                logger = logger
              )

            writeEntries(
              dictionaryEntries = dictionaryEntries.toIndexedSeq,
              nameFile = nameFile,
              usageFile = usageFile
            )

            Seq(
              nameFile,
              usageFile
            )

          //case DictionaryOutputFormat.Yaml =>
          //  ???

        }

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
      lastNameDictionarySourceUrlSetting,
      femaleFirstNameDictionarySourceUrlSetting,
      maleFirstNameDictionarySourceUrlSetting,
      downloadDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      readDictionaryEntriesTask,
      writeDictionaryEntriesTask,
      Compile / resourceGenerators += writeUsCensus1990DictionaryEntries
    )

}
