package ahlers.faker.plugins.heise.persons

import com.typesafe.config.ConfigFactory
import sbt.Keys._
import sbt._

import java.nio.charset.StandardCharsets
import scala.util.Try
import scala.collection.convert.ImplicitConversionsToScala._
import net.ceedubs.ficus.Ficus._

object HeiseNameDictionaryPlugin extends AutoPlugin {

  /** A quick-and-dirty [[URL]] factory, intended to streamline use of [[sun.net.www.protocol.classpath.Handler]] without manipulating the runtime. */
  private def url(location: String): URL =
    Try(new URL(location))
      .getOrElse(new URL(null, location, sun.net.www.protocol.classpath.Handler))

  /** Per [[noTrigger]], this plugin must be manually enabled, even if [[requires requirements]] are met. */
  override val trigger = noTrigger

  object autoImport {

    /**
     * @see [[https://www.heise.de/ct/ftp/07/17/182/ Magazin für Computertechnik: ''40 000 Namen'']]
     */
    val heiseNameDictionaryArchiveSourceUrl = settingKey[URL]("""Location of the original archive, described by "Magazin für Computertechnik's "40 000 Namen".""")

    val heiseNameDictionaryExtractDirectory = settingKey[File]("Destination of extracted name dictionary file, typically rooted in a task temporary directory, cleaned up after completion.")

    val heiseNameDictionaryOutputFormat = settingKey[TemplateEntriesOutputFormat]("Specifies whether to write—only CSV, for now, with additional formats planned.")

    val heiseNameDictionaryOutputDirectory = settingKey[File]("Where to write all output files, typically a managed resource directory on the class path.")

    val extractHeiseNameDictionaryFiles = taskKey[Set[File]]("Extracts the name dictionary source archive.")

    val loadHeiseNameDictionaryRegions = taskKey[Seq[Region]]("Loads region definitions from configuration.")

    val loadHeiseNameDictionaryEntries = taskKey[Seq[TemplateEntry]]("Loads and parses the dictionary to models suitable for encoding to standard formats.")

    val writeHeiseNameDictionaryEntries = taskKey[Seq[File]]("Writes entries in the specified output format.")

  }

  import autoImport._

  /** @todo Consider restoring from authoritative source. */
  private val archiveSourceUrlSetting: Setting[URL] =
    heiseNameDictionaryArchiveSourceUrl :=
      //url("ftp://ftp.heise.de/pub/ct/listings/0717-182.zip")
      url("classpath:ftp.heise.de/pub/ct/listings/0717-182.zip")

  private val dictionaryExtractDirectorySetting: Setting[File] =
    heiseNameDictionaryExtractDirectory :=
      taskTemporaryDirectory.value /
        "ftp.heise.de" /
        "pub" /
        "ct" /
        "listings"

  private val outputFormatSetting: Setting[TemplateEntriesOutputFormat] =
    heiseNameDictionaryOutputFormat :=
      TemplateEntriesOutputFormat.Csv

  private val outputDirectorySetting: Setting[File] =
    heiseNameDictionaryOutputDirectory :=
      (Compile / resourceManaged).value /
        "ftp.heise.de" /
        "pub" /
        "ct" /
        "listings"

  private val extractDictionaryFilesSetting: Setting[Task[Set[File]]] =
    extractHeiseNameDictionaryFiles := {
      val logger = streams.value.log

      val sourceUrl = heiseNameDictionaryArchiveSourceUrl.value
      val extractDirectory = heiseNameDictionaryExtractDirectory.value

      val extractFiles =
        IO.unzipURL(
          sourceUrl,
          extractDirectory)

      logger.info("""Extracted %d files from archive at "%s" to "%s": %s."""
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

  private val loadRegionsTask: Setting[Task[Seq[Region]]] =
    loadHeiseNameDictionaryRegions := {
      val logger = streams.value.log
      val regions =
        ConfigFactory
          .load(getClass.getClassLoader)
          .as[Seq[Region]]("regions")

      logger.info("Loaded %d regions, defining %d ISO country codes, from configuration."
        .format(
          regions.size,
          regions.flatMap(_.countryCodes).size))

      regions
    }

  private val loadEntriesTask: Setting[Task[Seq[TemplateEntry]]] =
    loadHeiseNameDictionaryEntries := {
      val extractFiles = extractHeiseNameDictionaryFiles.value
      val regions = loadHeiseNameDictionaryRegions.value

      val dictionaryFile =
        extractFiles
          .find(_.getName == "nam_dict.txt")
          .getOrElse(throw new MessageOnlyException(
            """File "%s" wasn't found among extracted files: %s."""
              .format(
                "nam_dict.txt",
                extractFiles.mkString("\"", "\", \"", "\"")
              )))

      val dictionaryLines: Seq[DictionaryLine] =
        IO.readLines(dictionaryFile, StandardCharsets.ISO_8859_1)
          .map(DictionaryLine(_))

      val readDictionaryEntries =
        TemplateEntriesReader.using(regions.toIndexedSeq)

      val dictionaryEntries: Seq[TemplateEntry] =
        readDictionaryEntries(dictionaryLines
          .toIndexedSeq)

      dictionaryEntries
    }

  private val writeEntriesTask: Setting[Task[Seq[File]]] =
    writeHeiseNameDictionaryEntries := {
      val logger = streams.value.log
      val outputFormat = heiseNameDictionaryOutputFormat.value
      val outputDirectory = heiseNameDictionaryOutputDirectory.value
      val classifiedNames = loadHeiseNameDictionaryEntries.value

      outputDirectory.mkdirs()

      val outputFiles =
        outputFormat match {

          case TemplateEntriesOutputFormat.Csv =>
            val templatesFile = outputDirectory / "template.csv"
            val usageFile = outputDirectory / "template-index,usage.csv"
            val countryCodeWeightFile = outputDirectory / "index,country-code,weight.csv"

            val writeEntries =
              TemplateEntriesCsvWriter.using(
                logger = logger
              )

            writeEntries(
              dictionaryEntries = classifiedNames.toIndexedSeq,
              templatesFile = templatesFile,
              usageFile = usageFile,
              countryCodeWeightFile = countryCodeWeightFile
            )

            Seq(
              templatesFile,
              usageFile,
              countryCodeWeightFile
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
      archiveSourceUrlSetting,
      dictionaryExtractDirectorySetting,
      outputFormatSetting,
      outputDirectorySetting,
      extractDictionaryFilesSetting,
      loadRegionsTask,
      loadEntriesTask,
      writeEntriesTask,
      Compile / resourceGenerators += writeHeiseNameDictionaryEntries
    )

}
