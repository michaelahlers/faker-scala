package ahlers.faker.plugins.opendata500

import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object DictionaryEntriesCsvWriter {

  implicit val orderingName: Ordering[CompanyName] =
    Ordering.by(_.toText)

  def using(
    nameFile: File,
    websiteFile: File,
    logger: Logger
  ): DictionaryEntriesWriter = { dictionaryEntries =>
    nameFile.getParentFile.mkdirs()
    websiteFile.getParentFile.mkdirs()

    /** Group around unique [[CompanyName]] values. */
    val indexByName: Map[CompanyName, Int] =
      dictionaryEntries
        .map(_.name)
        .sorted
        .distinct
        .zipWithIndex
        .toMap

    IO.writeLines(
      file = nameFile,
      lines = indexByName
        .keys.toSeq
        .sorted
        .map { name =>
          """%s""".format(name.toText)
        },
      charset = StandardCharsets.UTF_8,
      append = false
    )

    IO.writeLines(
      file = websiteFile,
      lines = dictionaryEntries
        .sortBy(_.name)
        .map(entry => (entry.name, entry.website))
        .distinct
        .map { case (name, website) =>
          """%x,%s""".format(
            indexByName(name),
            website.toText)
        },
      charset = StandardCharsets.UTF_8,
      append = false
    )

    Seq(
      nameFile,
      websiteFile
    )
  }

  def usingDir(
    outputDirectory: File,
    logger: Logger
  ): DictionaryEntriesWriter = {
    val nameFile = outputDirectory / "name.csv"
    val websiteFile = outputDirectory / "index,website.csv"

    using(
      nameFile = nameFile,
      websiteFile = websiteFile,
      logger = logger
    )
  }

}
