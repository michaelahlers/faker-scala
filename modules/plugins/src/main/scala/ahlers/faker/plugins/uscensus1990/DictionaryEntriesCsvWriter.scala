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

    val names: Seq[Name] =
      dictionaryEntries
        .sortBy(_.name)
        .map(_.name)

    val usageNames: Seq[(Usage, Name)] =
      dictionaryEntries
        .sortBy(_.name)
        .map(entry => (entry.usage, entry.name))

    IO.writeLines(
      file = nameFile,
      lines = names
        .map { name =>
          """%s""".format(name.toText)
        },
      charset = StandardCharsets.US_ASCII,
      append = false
    )

    IO.writeLines(
      file = nameFile,
      lines = usageNames
        .map { case (usage, name) =>
          """%d,%s""".format(names.indexOf(name), usage)
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
