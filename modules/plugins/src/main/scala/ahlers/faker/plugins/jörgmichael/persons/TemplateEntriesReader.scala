package ahlers.faker.plugins.jörgmichael.persons

import better.files._
import sbt.File

import java.nio.charset.StandardCharsets

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait TemplateEntriesReader {
  def apply(lines: IndexedSeq[DictionaryLine]): Seq[TemplateEntry]
}

object TemplateEntriesReader {

  def using(
    regions: IndexedSeq[Region],
    characterEncodings: IndexedSeq[CharacterEncoding],
    regionIndexes: IndexedSeq[RegionIndex]
  ): TemplateEntriesReader = {
    val decodeUsage = UsageDecoder()

    lines =>
      val decodeName = TemplateDecoder(characterEncodings)
      val parseRegionWeights = RegionWeightsParser.using(regionIndexes)

      val parseDictionaryEntry =
        TemplateEntryParser.using(
          decodeUsage = decodeUsage,
          decodeName = decodeName,
          parseRegionWeights = parseRegionWeights)

      lines
        .dropWhile(!_.toText.contains("begin of name list"))
        .drop(2)
        .map(parseDictionaryEntry(_))
  }

  def using(regions: IndexedSeq[Region]): TemplateEntriesReader = {
    val parseCharacterEncodings = CharacterEncodingsParser.default
    val parseRegionIndexes = RegionIndexesParser.using(regions)

    lines =>
      val characterEncodings: IndexedSeq[CharacterEncoding] =
        parseCharacterEncodings(lines)
          .toIndexedSeq

      val regionIndexes: IndexedSeq[RegionIndex] =
        parseRegionIndexes(lines)
          .toIndexedSeq

      val parseDictionaryEntries: TemplateEntriesReader =
        TemplateEntriesReader.using(
          regions = regions,
          characterEncodings = characterEncodings,
          regionIndexes = regionIndexes)

      parseDictionaryEntries(lines)

  }

}
