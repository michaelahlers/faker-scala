package ahlers.faker.plugins.uscensus2000.persons

import sbt._

import java.io.FileInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import scala.io.Codec
import scala.io.Source

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesReader {

  def apply(
    usage: Usage,
    entriesStream: InputStream
  ): Seq[DictionaryEntry]

  final def apply(
    usage: Usage,
    entriesFile: File
  ): Seq[DictionaryEntry] = {
    val entriesStream = new FileInputStream(entriesFile)

    try apply(
        usage = usage,
        entriesStream = entriesStream
      )
    finally entriesStream.close()
  }

}

object DictionaryEntriesReader {

  def using(
    parseEntry: DictionaryEntryParser
  ): DictionaryEntriesReader = { (usage, entriesStream) =>
    Source
      .fromInputStream(entriesStream)(Codec.ISO8859)
      .getLines()
      .drop(1)
      .map(DictionaryLine(_))
      .map(parseEntry(usage, _))
      .toIndexedSeq
  }

}
