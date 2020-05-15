package heise.ct

import java.util.zip.ZipInputStream

import scala.io._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
object NameDictionary {

  private val source: BufferedSource =
    Source.fromInputStream(
      new ZipInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("ftp.heise.de/pub/ct/listings/0717-182.zip")) {
        while (!(getNextEntry().getName() == "0717-182/nam_dict.txt")) {}
      })(Codec.ISO8859)

  case class Encoding(pattern: String, substitution: Char)
  val encodings: Seq[Encoding] =
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
            .map(Encoding(_, character.trim().toInt.toChar))
      }
      .toSeq
      .sortBy(_.pattern.length)

  case class Locale(position: Int, label: String)
  val locales: Seq[Locale] =
    source
      .getLines()
      .dropWhile(!_.contains("list of countries"))
      .drop(7)
      .take(164)
      .sliding(2, 3)
      .map {
        case Seq(locale, position) =>
          Locale(position.indexOf('|'), locale.tail.init.trim())
      }
      .toSeq
      .sortBy(_.position)

  assert(locales.size == 55)

  //source
  //  .getLines()
  //  .dropWhile(!_.contains("begin of name list"))
  //  .drop(2)

  source.close()

}
