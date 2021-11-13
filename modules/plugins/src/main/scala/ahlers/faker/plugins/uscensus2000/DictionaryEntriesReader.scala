package ahlers.faker.plugins.uscensus2000
import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesReader {
  def apply(usage: Usage, sourceFile: File): Seq[DictionaryEntry]
}

object DictionaryEntriesReader {

  def using(
    parseEntry: DictionaryEntryParser
  ): DictionaryEntriesReader = { (usage, sourceFile) =>
    IO.readLines(sourceFile, charset = StandardCharsets.US_ASCII)
      .drop(1)
      .map(DictionaryLine(_))
      .map(parseEntry(usage, _))
  }

}
