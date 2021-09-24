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

  def apply(regions: Seq[Region]): DictionaryParsing = {
    val parseCharacterEncodings = CharacterEncodingParser()
    val parseRegionIndexes = RegionIndexParser(regions)
    val decodeGender = GenderDecoder()

    new DictionaryParsing {
      override def classifiedNames(dictionaryFile: File) = {
        val lines: Iterator[DictionaryLine] =
          File(dictionaryFile.toPath)
            .lineIterator(StandardCharsets.ISO_8859_1)
            .map(DictionaryLine(_))

        val characterEncodings: Seq[CharacterEncoding] =
          parseCharacterEncodings(
            lines
              .dropWhile(!_.toString.contains("char set"))
              .drop(6)
              .take(67))

        val regionIndexes: Seq[RegionIndex] =
          parseRegionIndexes(
            lines
              .dropWhile(!_.toString.contains("list of countries"))
              .drop(7)
              .take(164))

        val parseRegionProbabilities = RegionProbabilityParser(regionIndexes)
        val decodeName = NameDecoder(characterEncodings)

        /* Moves the iterator to the correct position for names. */
        lines
          .dropWhile(!_.toString.contains("begin of name list"))
          .drop(2)

        lines
          .map { line =>
            val genderO: Option[Gender] =
              Option(line
                .toString
                .take(2)
                .trim())
                .filter(_.nonEmpty)
                .map(decodeGender(_))

            val regionProbabilities: Seq[RegionProbability] =
              parseRegionProbabilities(line)

            genderO match {

              case None =>
                val Array(short, long) =
                  decodeName(line
                    .toString
                    .slice(3, 29).trim())
                    .split(' ')

                ClassifiedName.Equivalent(
                  short = short,
                  long = long,
                  regionProbabilities = regionProbabilities)

              case Some(gender) =>
                val name =
                  line
                    .toString
                    .slice(3, 29)
                    .trim()

                val parts =
                  decodeName(name)
                    .split('+')

                val variations: Seq[String] =
                  parts match {
                    case parts @ Array(_) => parts
                    case Array(first, second) =>
                      Array(
                        s"$first $second",
                        s"$first-$second",
                        s"$first${second.toLowerCase}")
                  }

                ClassifiedName.Gendered(
                  gender = gender,
                  variations = variations,
                  regionProbabilities = regionProbabilities)

            }
          }
      }
    }
  }

}
