package ahlers.faker.plugins.uscensus2000.persons

import sbt.Keys._
import sbt._

import scala.util.Try
import scala.collection.convert.ImplicitConversionsToScala._

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object UsCensus2000NameDictionariesPlugin extends AutoPlugin {

  /** A quick-and-dirty [[URL]] factory, intended to streamline use of [[sun.net.www.protocol.classpath.Handler]] without manipulating the runtime. */
  private def url(location: String): URL =
    Try(new URL(location)).getOrElse(new URL(null, location, sun.net.www.protocol.classpath.Handler))

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.census.gov/topics/population/genealogy/data/2000_surnames.html United States Census Bureau: Frequently Occurring Surnames from the Census 2000]]
     */
    val usCensus2000SurnameDictionarySourceUrl = settingKey[URL]("""Location of the surnames names dictionary.""")

    val usCensus2000NameDictionariesExtractDirectory = settingKey[File]("Destination of extracted name dictionary files, typically rooted in a task temporary directory, cleaned up after completion.")

    val usCensus2000NameDictionariesOutputFormat = settingKey[DictionaryOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val usCensus2000NameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val extractUsCensus2000SurnameDictionaryFiles = taskKey[Set[File]]("Extracts the surname dictionary file from the original source archive.")

    val readUsCensus2000DictionaryEntries = taskKey[Seq[DictionaryEntry]]("Loads and parses all name dictionary entries obtained from downloads.")

    val writeUsCensus2000DictionaryEntries = taskKey[Seq[File]]("Writes all name dictionary entries in the specified output format and directory, serves as a resource generator.")

  }

  import autoImport._

  /** @todo Consider restoring from authoritative source. */
  private val nameDictionarySourceUrlSetting: Setting[URL] =
    usCensus2000SurnameDictionarySourceUrl :=
      //url("https://www2.census.gov/topics/genealogy/2000surnames/names.zip")
      url("classpath:www2.census.gov/topics/genealogy/2000surnames/names.zip")

  private val downloadDirectorySetting: Setting[File] =
    usCensus2000NameDictionariesExtractDirectory :=
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

  private val extractSurnameDictionaryFilesTaskSetting: Setting[Task[Set[File]]] =
    extractUsCensus2000SurnameDictionaryFiles := {
      val logger = streams.value.log

      val sourceUrl = usCensus2000SurnameDictionarySourceUrl.value
      val extractDirectory = usCensus2000NameDictionariesExtractDirectory.value

      val extractFiles =
        IO.unzipURL(
          sourceUrl,
          extractDirectory)

      logger.info("""Extracted %d files from archive "%s" to "%s": %s."""
        .format(
          extractFiles.size,
          sourceUrl,
          extractDirectory,
          extractFiles
            .map(_
              .toPath
              .drop(extractDirectory
                .toPath
                .size)
              .reduce(_.resolve(_)))
            .mkString("\"", "\", \"", "\"")
        ))

      extractFiles
    }

  private val readDictionaryEntriesTask: Setting[Task[Seq[DictionaryEntry]]] =
    readUsCensus2000DictionaryEntries := {
      val logger = streams.value.log

      val extractFiles: Set[File] = extractUsCensus2000SurnameDictionaryFiles.value

      val surnameFile =
        extractFiles
          .find(_.getName == "app_c.txt")
          .getOrElse(throw new MessageOnlyException(
            """File "%s" wasn't found among extracted files: %s."""
              .format(
                "app_c.txt",
                extractFiles.mkString("\"", "\", \"", "\"")
              )))

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

      outputDirectory.mkdirs()

      val outputFiles =
        usCensus2000NameDictionariesOutputFormat.value match {

          case DictionaryOutputFormat.Csv =>
            val nameFile = outputDirectory / "name.csv"
            val usageFile = outputDirectory / "index,usage.csv"

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
      nameDictionarySourceUrlSetting,
      downloadDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      extractSurnameDictionaryFilesTaskSetting,
      readDictionaryEntriesTask,
      writeDictionaryEntriesTask,
      Compile / resourceGenerators += writeUsCensus2000DictionaryEntries
    )

}
