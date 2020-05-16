package heise.ct

import cats.syntax.option._
import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import java.util.zip.ZipInputStream

import scala.io._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
object Dictionary {

  private val source: BufferedSource =
    Source.fromInputStream(
      new ZipInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("ftp.heise.de/pub/ct/listings/0717-182.zip")) {
        while (!(getNextEntry().getName() == "0717-182/nam_dict.txt")) {}
      })(Codec.ISO8859)

  val encodings: Seq[CharacterEncoding] =
    source
      .getLines()
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
      }
      .toIndexedSeq

  private def decode(x: String): String =
    encodings
      .sortBy(_.pattern.length())
      .foldLeft(x) {
        case (a, ce) =>
          a.replace(ce.pattern, ce.substitution)
      }

  val localeDefinitions: Seq[LocaleDefinition] =
    source
      .getLines()
      .dropWhile(!_.contains("list of countries"))
      .drop(7)
      .take(164)
      .sliding(2, 3)
      .map {
        case Seq(name, index) =>
          LocaleDefinition(
            LocaleIndex(Refined.unsafeApply(index.indexOf('|') - 30)),
            LocaleName(Refined.unsafeApply(name.tail.init.trim()))
          )
      }
      .toIndexedSeq
      .sortBy(_.index.toInt.value)

  val names: IndexedSeq[Name] =
    source
      .getLines()
      .dropWhile(!_.contains("begin of name list"))
      .drop(2)
      .map { entry =>
        val classifications =
          localeDefinitions
            .flatMap {
              case LocaleDefinition(index, name) =>
                Option(entry.charAt(index.toInt + 30))
                  .map(_.toString.trim())
                  .filter(_.nonEmpty)
                  .map(Integer.parseInt(_, 16))
                  .map { probability =>
                    (name, GivenNameProbability(Refined.unsafeApply(probability)))
                  }
            }
            .toMap

        entry.head match {
          case '=' =>
            decode(entry.slice(3, 29).trim())
              .split(' ') match {
              case Array(part) =>
                Name(Seq(NamePart(Refined.unsafeApply(part))), none, classifications)
              case Array(part, equivalentName) =>
                Name(Seq(NamePart(Refined.unsafeApply(part))), EquivalentName(Refined.unsafeApply(equivalentName)).some, classifications)
            }
          case _ =>
            Name(
              decode(entry.slice(3, 29).trim())
                .split('+').toSeq
                .map(part => NamePart(Refined.unsafeApply(part))),
              none,
              classifications)
        }
      }
      .toIndexedSeq

  source.close()

}
