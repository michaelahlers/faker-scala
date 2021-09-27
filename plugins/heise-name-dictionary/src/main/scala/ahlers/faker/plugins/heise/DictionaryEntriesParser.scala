package ahlers.faker.plugins.heise

import better.files._
import sbt.File

import java.nio.charset.StandardCharsets

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesParser extends (File => Iterator[DictionaryEntry])
object DictionaryEntriesParser {

  def apply(regions: IndexedSeq[Region]): DictionaryEntriesParser = {
    val parseCharacterEncodings = CharacterEncodingsParser()
    val parseRegionIndexes = RegionIndexesParser(regions)
    val decodeUsage = UsageDecoder()

    dictionaryFile: File =>
      val lines: Iterator[DictionaryLine] =
        File(dictionaryFile.toPath)
          .lineIterator(StandardCharsets.ISO_8859_1)
          .map(DictionaryLine(_))

      val characterEncodings: Seq[CharacterEncoding] =
        parseCharacterEncodings(
          lines
            .dropWhile(!_.toText.contains("char set"))
            .drop(6)
            .take(67))

      val regionIndexes: Seq[RegionIndex] =
        parseRegionIndexes(
          lines
            .dropWhile(!_.toText.contains("list of countries"))
            .drop(7)
            .take(164))

      val decodeName = NameDecoder(characterEncodings.toIndexedSeq)
      val parseRegionWeights = RegionWeightsParser(regionIndexes)

      val parseDictionaryEntry =
        DictionaryEntryParser(
          decodeUsage = decodeUsage,
          decodeName = decodeName,
          parseRegionWeights = parseRegionWeights)

      /* Moves the iterator to the correct position for names. */
      lines
        .dropWhile(!_.toText.contains("begin of name list"))
        .drop(2)

      lines
        .map(parseDictionaryEntry(_))
  }

}
