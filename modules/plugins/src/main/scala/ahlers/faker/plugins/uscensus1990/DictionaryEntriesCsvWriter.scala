package ahlers.faker.plugins.uscensus1990

import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object DictionaryEntriesCsvWriter {

  implicit val orderingName: Ordering[Name] =
    Ordering.by(_.toText)

  def using(
    outputDirectory: File,
    logger: Logger
  ): DictionaryEntriesWriter = { dictionaryEntries =>
    outputDirectory.mkdirs()

    val nameFile = outputDirectory / "name.csv"
    val usageFile = outputDirectory / "index,usage.csv"

    val entriesIndex: Seq[(DictionaryEntry, Int)] =
      dictionaryEntries
        .sortBy(_.name)
        .zipWithIndex

    IO.writeLines(
      file = nameFile,
      lines = entriesIndex
        .map { case (entry, _) =>
          """%s""".format(entry.name.toText)
        },
      charset = StandardCharsets.US_ASCII,
      append = false
    )

    IO.writeLines(
      file = usageFile,
      lines = entriesIndex
        .map { case (entry, index) =>
          """%x,%s""".format(index, entry.usage)
        },
      charset = StandardCharsets.US_ASCII,
      append = false
    )

    Seq(
      nameFile,
      usageFile
    )
  }

}
