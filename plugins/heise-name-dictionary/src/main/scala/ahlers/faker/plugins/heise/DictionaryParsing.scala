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

  def apply(regions: Set[Region]): DictionaryParsing =
    new DictionaryParsing {
      override def classifiedNames(dictionaryFile: File) = {
        val lines: Iterator[String] =
          File(dictionaryFile.toPath)
            .lineIterator(StandardCharsets.ISO_8859_1)

        val characterEncodings: Seq[CharacterEncoding] =
          lines
            .dropWhile(!_.contains("char set"))
            .drop(6)
            .take(67)
            .map(_.tail.init)
            .map(_.replaceAll("""\/\*\*.*""", ""))
            .map(_.trim().split('='))
            .flatMap {

              case Array(character, encodings) =>
                encodings
                  .split("or")
                  .map(_.trim())
                  .filter(_.nonEmpty)
                  .map(CharacterEncoding(_, character.trim().toInt.toChar.toString))

              /** @todo Handle errors properly. */
              case _ =>
                ???

            }
            .toIndexedSeq

        val decodeName = NameDecoder(characterEncodings)

        val regionByLabel: Map[String, Region] =
          regions
            .map(region => (region.label, region))
            .toMap

        val regionByIndex: Map[Int, Region] =
          lines
            .dropWhile(!_.contains("list of countries"))
            .drop(7)
            .take(164)
            .sliding(2, 3)
            .map {

              case Seq(label, index) =>
                (index.indexOf('|') - 30, regionByLabel(label.tail.init.trim()))

              /** @todo Handle errors properly. */
              case _ =>
                ???

            }
            .toMap

        /* Moves the iterator to the correct position for names. */
        lines
          .dropWhile(!_.contains("begin of name list"))
          .drop(2)

        lines
          .map { entry =>
            val genderO: Option[String] =
              Option(entry
                .take(2)
                .trim())
                .filter(_.nonEmpty)

            val probabilityByRegion: Map[Region, Int] =
              regionByIndex
                .flatMap {
                  case (index, name) =>
                    Option(entry.charAt(index.toInt + 30))
                      .map(_.toString.trim())
                      .filter(_.nonEmpty)
                      .map(Integer.parseInt(_, 16))
                      .map { probability =>
                        (name, probability)
                      }
                }

            genderO match {

              /** Export separately. */
              case None =>
                val Array(short, long) =
                  decodeName(entry.slice(3, 29).trim())
                    .split(' ')

                ClassifiedName.Equivalent(
                  short = short,
                  long = long,
                  probabilityByRegion)

              case Some(gender) =>
                val name =
                  entry
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
                  probabilityByLocale = probabilityByRegion)

            }
          }
      }
    }

}
