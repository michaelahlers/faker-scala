package ahlers.faker.datasets.heise

import java.io.Closeable

import cats.syntax.option._
import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import java.util.zip.ZipInputStream

import ahlers.faker.PersonGivenName

import scala.io._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
class ClassifiedNameIterator extends Iterator[ClassifiedName] with Closeable {

  private val source: BufferedSource =
    Source.fromInputStream(
      new ZipInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("ftp.heise.de/pub/ct/listings/0717-182.zip")) {
        while (!(getNextEntry().getName() == "0717-182/nam_dict.txt")) {}
      })(Codec.ISO8859)

  private val lines: Iterator[String] = source.getLines()

  override def close(): Unit = source.close()

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

  private def decodeName(x: String): String =
    characterEncodings
      .sortBy(_.pattern.length())
      .foldLeft(x) {
        case (a, ce) =>
          a.replace(ce.pattern, ce.substitution)
      }

  val localeByLabel: Map[String, Locale] = {
    import Locales._
    Map(
      "Great Britain" -> `Great Britain`,
      "Ireland" -> Ireland,
      "U.S.A." -> `United States`,
      "Italy" -> Italy,
      "Malta" -> Malta,
      "Portugal" -> Portugal,
      "Spain" -> Spain,
      "France" -> France,
      "Belgium" -> Belgium,
      "Luxembourg" -> Luxembourg,
      "the Netherlands" -> Netherlands,
      "East Frisia" -> `East Frisia`,
      "Germany" -> Germany,
      "Austria" -> Austria,
      "Swiss" -> Swiss,
      "Iceland" -> Iceland,
      "Denmark" -> Denmark,
      "Norway" -> Norway,
      "Sweden" -> Sweden,
      "Finland" -> Finland,
      "Estonia" -> Estonia,
      "Latvia" -> Latvia,
      "Lithuania" -> Lithuania,
      "Poland" -> Poland,
      "Czech Republic" -> `Czech Republic`,
      "Slovakia" -> Slovakia,
      "Hungary" -> Hungary,
      "Romania" -> Romania,
      "Bulgaria" -> Bulgaria,
      "Bosnia and Herzegovina" -> `Bosnia/Herzegovina`,
      "Croatia" -> Croatia,
      "Kosovo" -> Kosovo,
      "Macedonia" -> Macedonia,
      "Montenegro" -> Montenegro,
      "Serbia" -> Serbia,
      "Slovenia" -> Slovenia,
      "Albania" -> Albania,
      "Greece" -> Greece,
      "Russia" -> Russia,
      "Belarus" -> Belarus,
      "Moldova" -> Moldova,
      "Ukraine" -> Ukraine,
      "Armenia" -> Armenia,
      "Azerbaijan" -> Azerbaijan,
      "Georgia" -> Georgia,
      "Kazakhstan/Uzbekistan,etc." -> `Kazakhstan/Uzbekistan`,
      "Turkey" -> Turkey,
      "Arabia/Persia" -> `Arabia/Persia`,
      "Israel" -> Israel,
      "China" -> China,
      "India/Sri Lanka" -> `India/Sri Lanka`,
      "Japan" -> Japan,
      "Korea" -> Korea,
      "Vietnam" -> Vietnam,
      "other countries" -> Other
    )
  }

  val localeByIndex: Map[Int, Locale] =
    lines
      .dropWhile(!_.contains("list of countries"))
      .drop(7)
      .take(164)
      .sliding(2, 3)
      .map {

        case Seq(label, index) =>
          (index.indexOf('|') - 30, localeByLabel(label.tail.init.trim()))

        /** @todo Handle errors properly. */
        case _ =>
          ???

      }
      .toMap

  val genderByLabel: Map[String, Gender] = {
    import Genders._
    Map(
      "M" -> Male,
      "1M" -> FirstMale,
      "?M" -> MostlyMale,
      "F" -> Female,
      "1F" -> FirstFemale,
      "?F" -> MostlyFemale,
      "?" -> Unisex
    )
  }

  /* Moves the iterator to the correct position for names. */
  lines
    .dropWhile(!_.contains("begin of name list"))
    .drop(2)

  override def hasNext = lines.hasNext

  override def next() = {
    val entry = lines.next()

    val genderO: Option[Gender] = genderByLabel.get(entry.take(2).trim())

    val probabilityByLocale: Map[Locale, LocaleProbability] =
      localeByIndex
        .flatMap {
          case (index, name) =>
            Option(entry.charAt(index.toInt + 30))
              .map(_.toString.trim())
              .filter(_.nonEmpty)
              .map(Integer.parseInt(_, 16))
              .map { probability =>
                (name, LocaleProbability(Refined.unsafeApply(probability)))
              }
        }

    genderO match {
      case None =>
        EquivalentNames(
          decodeName(entry.slice(3, 29).trim())
            .split(' ').toSet[String]
            .map(givenName => PersonGivenName(Refined.unsafeApply(givenName))),
          probabilityByLocale)
      case Some(gender) =>
        GenderedName(
          gender,
          decodeName(entry.slice(3, 29).trim())
            .split('+').toSeq
            .map(givenName => PersonGivenName(Refined.unsafeApply(givenName))),
          probabilityByLocale)
    }
  }

}

object ClassifiedNameIterator {
  def apply(): ClassifiedNameIterator = new ClassifiedNameIterator
}
