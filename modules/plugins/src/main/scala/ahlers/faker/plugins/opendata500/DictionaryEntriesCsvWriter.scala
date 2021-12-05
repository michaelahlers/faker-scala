package ahlers.faker.plugins.opendata500

import sbt._

import java.io.FileOutputStream
import java.io.InputStream
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
    websiteStream: OutputStream
  ): Unit

  final def apply(
    dictionaryEntries: IndexedSeq[DictionaryEntry],
    nameFile: File,
    websiteFile: File
  ): Unit = {
    val nameStream = new FileOutputStream(nameFile, false)
    val websiteStream = new FileOutputStream(websiteFile, false)

    try apply(
      dictionaryEntries = dictionaryEntries,
      nameStream = nameStream,
      websiteStream = websiteStream
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

  implicit val orderingName: Ordering[CompanyName] =
    Ordering.by(_.toText)

  def using(
    logger: Logger
  ): DictionaryEntriesCsvWriter = { (dictionaryEntries, nameStream, websiteStream) =>
    /** Group around unique [[CompanyName]] values. */
    val indexByName: Map[CompanyName, Int] =
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
      writer = new PrintWriter(websiteStream, true),
      lines = dictionaryEntries
        .sortBy(_.name)
        .flatMap(entry => entry.website.map((entry.name, _)))
        .distinct
        .map { case (name, website) =>
          """%x,%s""".format(
            indexByName(name),
            website.toText)
        }
    )

    ()
  }

}
