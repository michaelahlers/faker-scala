package ahlers.faker.plugins.uscensus2000

import sbt._

import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesCsvWriter {

  def apply(
    dictionaryEntries: IndexedSeq[DictionaryEntry],
    nameStream: OutputStream,
    usageStream: OutputStream
  ): Unit

  final def apply(
    dictionaryEntries: IndexedSeq[DictionaryEntry],
    nameFile: File,
    usageFile: File
  ): Unit = {
    val nameStream = new FileOutputStream(nameFile, false)
    val websiteStream = new FileOutputStream(usageFile, false)

    try apply(
      dictionaryEntries = dictionaryEntries,
      nameStream = nameStream,
      usageStream = websiteStream
    )
    finally {
      nameStream.flush()
      websiteStream.flush()

      nameStream.close()
      websiteStream.close()
    }
  }

}

object DictionaryEntriesCsvWriter {

  implicit val orderingName: Ordering[Name] =
    Ordering.by(_.toText)

  def using(
    logger: Logger
  ): DictionaryEntriesCsvWriter = { (dictionaryEntries, nameStream, usageStream) =>
    /** Group around unique [[Name]] values. */
    val indexByName: Map[Name, Int] =
      dictionaryEntries
        .map(_.name)
        .sorted
        .distinct
        .zipWithIndex
        .toMap

    IO.writeLines(
      writer = new PrintWriter(nameStream, true),
      lines = indexByName
        .keys.toSeq
        .sorted
        .map { name =>
          """%s""".format(name.toText)
        }
    )

    IO.writeLines(
      writer = new PrintWriter(usageStream, true),
      lines = dictionaryEntries
        .sortBy(_.name)
        .map(entry => (entry.name, entry.usage))
        .distinct
        .map { case (name, usage) =>
          """%x,%s""".format(
            indexByName(name),
            usage match {
              case Usage.Sur => "S"
            })
        }
    )
  }

}
