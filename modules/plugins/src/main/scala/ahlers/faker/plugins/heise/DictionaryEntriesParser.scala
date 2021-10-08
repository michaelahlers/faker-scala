package ahlers.faker.plugins.heise

import better.files._
import sbt.File

import java.nio.charset.StandardCharsets

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesParser extends (IndexedSeq[DictionaryLine] => Seq[DictionaryEntry])
object DictionaryEntriesParser {

  def using(
    regions: IndexedSeq[Region],
    characterEncodings: IndexedSeq[CharacterEncoding],
    regionIndexes: IndexedSeq[RegionIndex]
  ): DictionaryEntriesParser = {
    val decodeUsage = UsageDecoder()

    lines =>
      val decodeName = TemplateDecoder(characterEncodings)
      val parseRegionWeights = RegionWeightsParser.using(regionIndexes)

      val parseDictionaryEntry =
        DictionaryEntryParser.using(
          decodeUsage = decodeUsage,
          decodeName = decodeName,
          parseRegionWeights = parseRegionWeights)

      lines
        .dropWhile(!_.toText.contains("begin of name list"))
        .drop(2)
        .map(parseDictionaryEntry(_))
  }

  def using(regions: IndexedSeq[Region]): DictionaryEntriesParser = {
    val parseCharacterEncodings = CharacterEncodingsParser.default
    val parseRegionIndexes = RegionIndexesParser.using(regions)

    lines =>
      val characterEncodings: IndexedSeq[CharacterEncoding] =
        parseCharacterEncodings(lines)
          .toIndexedSeq

      val regionIndexes: IndexedSeq[RegionIndex] =
        parseRegionIndexes(lines)
          .toIndexedSeq

      val parseDictionaryEntries: DictionaryEntriesParser =
        DictionaryEntriesParser.using(
          regions = regions,
          characterEncodings = characterEncodings,
          regionIndexes = regionIndexes)

      parseDictionaryEntries(lines)

  }

}
