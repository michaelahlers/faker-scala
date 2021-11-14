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
    //usageFile: File,
    logger: Logger
  ): DictionaryEntriesWriter = { dictionaryEntries =>
    nameFile.getParentFile.mkdirs()
    //usageFile.getParentFile.mkdirs()

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
      charset = StandardCharsets.US_ASCII,
      append = false
    )

    //IO.writeLines(
    //  file = usageFile,
    //  lines = dictionaryEntries
    //    .sortBy(_.name)
    //    .map(entry => (entry.name, entry.usage))
    //    .distinct
    //    .map { case (name, usage) =>
    //      """%x,%s""".format(
    //        indexByName(name),
    //        usage match {
    //          case Usage.FemaleFirst => "F"
    //          case Usage.MaleFirst => "M"
    //          case Usage.Last => "L"
    //        })
    //    },
    //  charset = StandardCharsets.US_ASCII,
    //  append = false
    //)

    Seq(
      nameFile
      //usageFile
    )
  }

  def using(
    outputDirectory: File,
    logger: Logger
  ): DictionaryEntriesWriter = {
    val nameFile = outputDirectory / "name.csv"
    //val usageFile = outputDirectory / "index,usage.csv"

    using(
      nameFile = nameFile,
      //usageFile = usageFile,
      logger = logger
    )
  }

}
