package ahlers.faker.plugins.heise

import better.files._
import sbt.File

import java.nio.charset.StandardCharsets

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryParsing {
  def classifiedNames(dictionaryFile: File): Iterator[ClassifiedName]
}

object DictionaryParsing {

  def apply(regions: IndexedSeq[Region]): DictionaryParsing = {
    val parseCharacterEncodings = CharacterEncodingsParser()
    val parseRegionIndexes = RegionIndexesParser(regions)
    val decodeUsage = UsageDecoder()

    new DictionaryParsing {
      override def classifiedNames(dictionaryFile: File) = {
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
        val parseNames = NamesParser()
        val parseRegionWeights = RegionWeightsParser(regionIndexes)

        val parseClassifiedName =
          ClassifiedNameParser(
            decodeUsage = decodeUsage,
            decodeName = decodeName,
            parseNames = parseNames,
            parseRegionWeights = parseRegionWeights)

        /* Moves the iterator to the correct position for names. */
        lines
          .dropWhile(!_.toText.contains("begin of name list"))
          .drop(2)

        lines
          .map(parseClassifiedName(_))
      }
    }
  }

}
