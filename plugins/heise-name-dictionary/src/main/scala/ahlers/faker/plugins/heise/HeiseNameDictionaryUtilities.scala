package ahlers.faker.plugins.heise

import better.files._
import sbt.File
import sbt.IO
import sbt.MessageOnlyException
import sbt.util.Logger

import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Locale
import scala.collection.convert.ImplicitConversionsToScala._
import scala.util.control.NonFatal

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

  /**
   * Caveats:
   * - Resolves to [[Locale.UK]], which is technically incorrect; see also [[https://stackoverflow.com/questions/8334904/locale-uk-and-country-code ''Locale.UK and country code'']].
   */
  object Region extends Enumeration {

    private case class RegionLabelNotFoundException(regionLabel: String, cause: Throwable)
      extends Exception(s"""Couldn't resolve "$regionLabel" to a region.""", cause)

    private case class CountryCodeNotFoundException(countryCode: String)
      extends Exception(s"""No locales with country code "$countryCode".""")

    private val byCountryCode: Map[String, Seq[Locale]] =
      Locale.getAvailableLocales
        .toSeq
        .groupBy(_.getCountry)
        .withDefault(countryCode =>
          throw CountryCodeNotFoundException(countryCode))

    private[Region] case class WithProperties(locales: Seq[Locale]) extends Val
    private object WithProperties {
      def apply(): WithProperties = WithProperties(Nil)
      def apply(locale: Locale, locales: Locale*): WithProperties = WithProperties(locale +: locales)
    }

    implicit private def implyRegion(value: Value): WithProperties = value.asInstanceOf[WithProperties]

    val `Albania` = WithProperties(byCountryCode("AL"))
    val `Arabia/Persia` = WithProperties()
    val `Armenia` = WithProperties(byCountryCode("AM"))
    val `Austria` = WithProperties(byCountryCode("AT"))
    val `Azerbaijan` = WithProperties(byCountryCode("AZ"))
    val `Belarus` = WithProperties()
    val `Belgium` = WithProperties()
    val `Bosnia and Herzegovina` = WithProperties()
    val `Bulgaria` = WithProperties()
    val `China` = WithProperties()
    val `Croatia` = WithProperties()
    val `Czech Republic` = WithProperties()
    val `Denmark` = WithProperties()
    val `East Frisia` = WithProperties()
    val `Estonia` = WithProperties()
    val `Finland` = WithProperties()
    val `France` = WithProperties()
    val `Georgia` = WithProperties()
    val `Germany` = WithProperties()
    val `Great Britain` = WithProperties(Locale.UK)
    val `Greece` = WithProperties()
    val `Hungary` = WithProperties()
    val `Iceland` = WithProperties()
    val `India/Sri Lanka` = WithProperties(byCountryCode("IN") ++ byCountryCode("LK"))
    val `Ireland` = WithProperties(Locale.ITALY)
    val `Israel` = WithProperties()
    val `Italy` = WithProperties()
    val `Japan` = WithProperties(Locale.JAPAN)
    val `Kazakhstan/Uzbekistan,etc.` = WithProperties()
    val `Korea` = WithProperties(Locale.KOREA)
    val `Kosovo` = WithProperties()
    val `Latvia` = WithProperties()
    val `Lithuania` = WithProperties()
    val `Luxembourg` = WithProperties()
    val `Macedonia` = WithProperties()
    val `Malta` = WithProperties()
    val `Moldova` = WithProperties()
    val `Montenegro` = WithProperties()
    val `the Netherlands` = WithProperties()
    val `Norway` = WithProperties()
    val `Poland` = WithProperties()
    val `Portugal` = WithProperties()
    val `Romania` = WithProperties()
    val `Russia` = WithProperties()
    val `Serbia` = WithProperties()
    val `Slovakia` = WithProperties()
    val `Slovenia` = WithProperties()
    val `Spain` = WithProperties()
    val `Sweden` = WithProperties()
    val `Swiss` = WithProperties()
    val `Turkey` = WithProperties()
    val `Ukraine` = WithProperties()
    val `U.S.A.` = WithProperties()
    val `Vietnam` = WithProperties()
    val `other countries` = WithProperties()

    object HasLocale {
      def unapply(region: Region): Option[Seq[Locale]] =
        Option(region.locales)
          .filter(_.nonEmpty)
    }

    /** A simple work-around of [[withName]] with consideration for encodings. */
    def withLabel(label: String): Region =
      try withName(label
        .replaceAllLiterally(" ", "$u0020")
        .replaceAllLiterally(",", "$u002C")
        .replaceAllLiterally(".", "$u002E")
        .replaceAllLiterally("/", "$div"))
      catch {
        case NonFatal(reason) =>
          throw RegionLabelNotFoundException(label, reason)
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
            (index.indexOf('|') - 30, Region.withLabel(label.tail.init.trim()))

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
              decodeName(characterEncodings, entry.slice(3, 29).trim())
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
              decodeName(characterEncodings, name)
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
