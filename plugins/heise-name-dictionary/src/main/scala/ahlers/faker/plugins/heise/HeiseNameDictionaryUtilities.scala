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

  type Region = Region.Value
  object Region extends Enumeration {

    val `Albania`, `Arabia/Persia`, `Armenia`, `Austria`, `Azerbaijan`, `Belarus`, `Belgium`, `Bosnia/Herzegovina`, `Bulgaria`, `China`, `Croatia`, `Czech Republic`, `Denmark`, `East Frisia`, `Estonia`, `Finland`, `France`, `Georgia`,
      `Germany`, `Great Britain`, `Greece`, `Hungary`, `Iceland`, `India/Sri Lanka`, `Ireland`, `Israel`, `Italy`, `Japan`, `Kazakhstan/Uzbekistan`, `Korea`, `Kosovo`, `Latvia`, `Lithuania`, `Luxembourg`, `Macedonia`, `Malta`, `Moldova`,
      `Montenegro`, `Netherlands`, `Norway`, `Poland`, `Portugal`, `Romania`, `Russia`, `Serbia`, `Slovakia`, `Slovenia`, `Spain`, `Sweden`, `Switzerland`, `Turkey`, `Ukraine`, `United States`, `Vietnam`, `Other` = Value

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
            (index.indexOf('|') - 30, Region.withName(label.tail.init.trim()))

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
