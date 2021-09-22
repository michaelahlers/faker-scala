package ahlers.faker.plugins.heise

import sbt.File
import sbt.IO
import sbt.MessageOnlyException
import sbt.util.Logger
import better.files._
import cats.syntax.option._
import java.util.Locale

import scala.collection.convert.ImplicitConversionsToScala._
import java.net.URL
import java.nio.charset.StandardCharsets

/**
 * @since September 18, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object HeiseNameDictionaryUtilities {

  def downloadDictionary(sourceUrl: URL, downloadDirectory: File, dictionaryFileName: String, logger: Logger): File = {
    val downloadFiles =
      IO.unzipURL(
        sourceUrl,
        downloadDirectory)

    logger.info("""Extracted %d files from archive "%s" to "%s": %s."""
      .format(
        downloadFiles.size,
        sourceUrl,
        downloadDirectory,
        downloadFiles
          .map(_
            .toPath
            .drop(downloadDirectory
              .toPath
              .size)
            .reduce(_.resolve(_)))
          .mkString("\"", "\", \"", "\"")
      ))

    val dictionaryFile =
      downloadFiles
        .find(_.getName() == dictionaryFileName)
        .getOrElse(throw new MessageOnlyException(s"""File "$dictionaryFileName" wasn't found in archive "$sourceUrl"."""))

    logger.info(s"""Heise name dictionary saved to "$dictionaryFile".""")

    dictionaryFile
  }

  def decodeName(characterEncodings: Seq[CharacterEncoding], encodedName: String): String =
    characterEncodings
      .sortBy(_.pattern.length())
      .foldLeft(encodedName) {
        case (decodedName, characterEncoding) =>
          decodedName
            .replace(
              characterEncoding.pattern,
              characterEncoding.substitution)
      }

  sealed trait Region
  object Regions {

    case object `Albania` extends Region
    case object `Arabia/Persia` extends Region
    case object `Armenia` extends Region
    case object `Austria` extends Region
    case object `Azerbaijan` extends Region
    case object `Belarus` extends Region
    case object `Belgium` extends Region
    case object `Bosnia/Herzegovina` extends Region
    case object `Bulgaria` extends Region
    case object `China` extends Region
    case object `Croatia` extends Region
    case object `Czech Republic` extends Region
    case object `Denmark` extends Region
    case object `East Frisia` extends Region
    case object `Estonia` extends Region
    case object `Finland` extends Region
    case object `France` extends Region
    case object `Georgia` extends Region
    case object `Germany` extends Region
    case object `Great Britain` extends Region
    case object `Greece` extends Region
    case object `Hungary` extends Region
    case object `Iceland` extends Region
    case object `India/Sri Lanka` extends Region
    case object `Ireland` extends Region
    case object `Israel` extends Region
    case object `Italy` extends Region
    case object `Japan` extends Region
    case object `Kazakhstan/Uzbekistan` extends Region
    case object `Korea` extends Region
    case object `Kosovo` extends Region
    case object `Latvia` extends Region
    case object `Lithuania` extends Region
    case object `Luxembourg` extends Region
    case object `Macedonia` extends Region
    case object `Malta` extends Region
    case object `Moldova` extends Region
    case object `Montenegro` extends Region
    case object `Netherlands` extends Region
    case object `Norway` extends Region
    case object `Poland` extends Region
    case object `Portugal` extends Region
    case object `Romania` extends Region
    case object `Russia` extends Region
    case object `Serbia` extends Region
    case object `Slovakia` extends Region
    case object `Slovenia` extends Region
    case object `Spain` extends Region
    case object `Sweden` extends Region
    case object `Switzerland` extends Region
    case object `Turkey` extends Region
    case object `Ukraine` extends Region
    case object `United States` extends Region
    case object `Vietnam` extends Region

    case object `Other` extends Region

    object HasLocale {

      case class CountryCodeNotFoundException(countryCode: String)
        extends Exception(s"""No locales with country code "$countryCode".""")

      val byCountryCode: Map[String, Seq[Locale]] =
        Locale.getAvailableLocales
          .toSeq
          .groupBy(_.getCountry)
          .withDefault(countryCode =>
            throw CountryCodeNotFoundException(countryCode))

      /**
       * Caveats:
       *
       * - Resolves [[`Great Britain`]] to [[Locale.UK]], which is technically incorrect; see also [[https://stackoverflow.com/questions/8334904/locale-uk-and-country-code ''Locale.UK and country code'']].
       */
      def unapply(region: Region): Option[Seq[Locale]] =
        region match {
          case `Albania` => byCountryCode("AL").some
          case `Arabia/Persia` => none
          case `Armenia` => byCountryCode("AM").some
          case `Austria` => byCountryCode("AT").some
          case `Azerbaijan` => byCountryCode("AZ").some
          case `Belarus` => none
          case `Belgium` => none
          case `Bosnia/Herzegovina` => none
          case `Bulgaria` => none
          case `China` => none
          case `Croatia` => none
          case `Czech Republic` => none
          case `Denmark` => none
          case `East Frisia` => none
          case `Estonia` => none
          case `Finland` => none
          case `France` => Seq(Locale.FRANCE).some
          case `Georgia` => none
          case `Germany` => Seq(Locale.GERMANY).some
          case `Great Britain` => Seq(Locale.UK).some
          case `Greece` => none
          case `Hungary` => none
          case `Iceland` => none
          case `India/Sri Lanka` => (byCountryCode("IN") ++ byCountryCode("LK")).some
          case `Ireland` => none
          case `Israel` => none
          case `Italy` => Seq(Locale.ITALY).some
          case `Japan` => Seq(Locale.JAPAN).some
          case `Kazakhstan/Uzbekistan` => none
          case `Korea` => Seq(Locale.KOREA).some
          case `Kosovo` => none
          case `Latvia` => none
          case `Lithuania` => none
          case `Luxembourg` => none
          case `Macedonia` => none
          case `Malta` => none
          case `Moldova` => none
          case `Montenegro` => none
          case `Netherlands` => none
          case `Norway` => none
          case `Poland` => none
          case `Portugal` => none
          case `Romania` => none
          case `Russia` => none
          case `Serbia` => none
          case `Slovakia` => none
          case `Slovenia` => none
          case `Spain` => none
          case `Sweden` => none
          case `Switzerland` => none
          case `Turkey` => none
          case `Ukraine` => none
          case `United States` => Seq(Locale.US).some
          case `Vietnam` => none

          case `Other` => none
        }

    }

    val byLabel: Map[String, Region] =
      Map(
        "Great Britain" -> `Great Britain`,
        "Ireland" -> `Ireland`,
        "U.S.A." -> `United States`,
        "Italy" -> `Italy`,
        "Malta" -> `Malta`,
        "Portugal" -> `Portugal`,
        "Spain" -> `Spain`,
        "France" -> `France`,
        "Belgium" -> `Belgium`,
        "Luxembourg" -> `Luxembourg`,
        "the Netherlands" -> `Netherlands`,
        "East Frisia" -> `East Frisia`,
        "Germany" -> `Germany`,
        "Austria" -> `Austria`,
        "Swiss" -> `Switzerland`,
        "Iceland" -> `Iceland`,
        "Denmark" -> `Denmark`,
        "Norway" -> `Norway`,
        "Sweden" -> `Sweden`,
        "Finland" -> `Finland`,
        "Estonia" -> `Estonia`,
        "Latvia" -> `Latvia`,
        "Lithuania" -> `Lithuania`,
        "Poland" -> `Poland`,
        "Czech Republic" -> `Czech Republic`,
        "Slovakia" -> `Slovakia`,
        "Hungary" -> `Hungary`,
        "Romania" -> `Romania`,
        "Bulgaria" -> `Bulgaria`,
        "Bosnia and Herzegovina" -> `Bosnia/Herzegovina`,
        "Croatia" -> `Croatia`,
        "Kosovo" -> `Kosovo`,
        "Macedonia" -> `Macedonia`,
        "Montenegro" -> `Montenegro`,
        "Serbia" -> `Serbia`,
        "Slovenia" -> `Slovenia`,
        "Albania" -> `Albania`,
        "Greece" -> `Greece`,
        "Russia" -> `Russia`,
        "Belarus" -> `Belarus`,
        "Moldova" -> `Moldova`,
        "Ukraine" -> `Ukraine`,
        "Armenia" -> `Armenia`,
        "Azerbaijan" -> `Azerbaijan`,
        "Georgia" -> `Georgia`,
        "Kazakhstan/Uzbekistan,etc." -> `Kazakhstan/Uzbekistan`,
        "Turkey" -> `Turkey`,
        "Arabia/Persia" -> `Arabia/Persia`,
        "Israel" -> `Israel`,
        "China" -> `China`,
        "India/Sri Lanka" -> `India/Sri Lanka`,
        "Japan" -> `Japan`,
        "Korea" -> `Korea`,
        "Vietnam" -> `Vietnam`,
        "other countries" -> `Other`
      )

  }

  def classifiedNames(dictionaryFile: File): Iterator[ClassifiedName] = {
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

    val regionByIndex: Map[Int, Region] =
      lines
        .dropWhile(!_.contains("list of countries"))
        .drop(7)
        .take(164)
        .sliding(2, 3)
        .map {

          case Seq(label, index) =>
            (index.indexOf('|') - 30, Regions.byLabel(label.tail.init.trim()))

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
      .flatMap { entry =>
        val genderO: Option[String] =
          Option(entry
            .take(2)
            .trim())
            .filter(_.nonEmpty)

        genderO match {

          /** Export separately. */
          case None =>
            None

          case Some(gender) =>
            val name =
              entry
                .slice(3, 29)
                .trim()

            val parts =
              decodeName(characterEncodings, name)
                .split('+')
                .toSeq

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

            Some(ClassifiedName(
              gender = gender,
              parts = parts,
              probabilityByLocale = probabilityByRegion))

        }
      }
  }

}
