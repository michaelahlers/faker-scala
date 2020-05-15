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
            .map(CharacterEncoding(_, character.trim().toInt.toChar))
      }
      .toSeq
      .sortBy(_.pattern.length)

  val localeDefinitions: Seq[LocaleDefinition] =
    source
      .getLines()
      .dropWhile(!_.contains("list of countries"))
      .drop(7)
      .take(164)
      .sliding(2, 3)
      .map {
        case Seq(name, index) =>
          LocaleDefinition(LocaleIndex(Refined.unsafeApply(index.indexOf('|'))), LocaleName(Refined.unsafeApply(name.tail.init.trim())))
      }
      .toSeq
      .sortBy(_.index)

  val names: IndexedSeq[Name] =
    source
      .getLines()
      .dropWhile(!_.contains("begin of name list"))
      .drop(2)
      .map { entry =>
        entry.head match {
          case '=' =>
            entry
              .slice(3, 29)
              .trim()
              .split(' ')
              .map(encodings.foldRight(_)(_.replace(_))) match {
              case Array(part) =>
                Name(NamePart(Refined.unsafeApply(part)))
              case Array(part, equivalentName) =>
                Name(NamePart(Refined.unsafeApply(part)), EquivalentName(Refined.unsafeApply(equivalentName)))
            }
          case _ =>
            Name(
              entry
                .slice(3, 29)
                .trim()
                .split('+')
                .map(part => NamePart(Refined.unsafeApply(part)))
                .toSeq)
        }
      }
      .toIndexedSeq

  source.close()

}
