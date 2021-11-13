package ahlers.faker.plugins.uscensus1990
import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesParser extends ((Usage, File) => Seq[DictionaryEntry])
object DictionaryEntriesParser {

  def using(
    parseEntry: DictionaryEntryParser
  ): DictionaryEntriesParser = { (usage, sourceFile) =>
    IO.readLines(sourceFile, charset = StandardCharsets.US_ASCII)
      .map(DictionaryLine(_))
      .map(parseEntry(usage, _))
  }

}
