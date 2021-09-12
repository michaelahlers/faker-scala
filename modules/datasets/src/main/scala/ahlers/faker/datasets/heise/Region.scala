package ahlers.faker.datasets.heise

import cats.syntax.option._
import java.util.Locale

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
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

    case class CountryCodeNotFoundException(country: String)
      extends Exception(s"""No locale with country code "$country".""")

    private val byCountry: Map[String, Seq[Locale]] =
      Locale.getAvailableLocales
        .groupBy(_.getCountry)
        .mapValues(_.toSeq)
        .withDefault(country =>
          throw CountryCodeNotFoundException(country))

    /**
     * Caveats:
     *
     * - Resolves [[`Great Britain`]] to [[Locale.UK]], which is technically incorrect; see also [[https://stackoverflow.com/questions/8334904/locale-uk-and-country-code ''Locale.UK and country code'']].
     */
    def unapply(region: Region): Option[Seq[Locale]] =
      region match {
        case `Albania` => byCountry("AL").some
        case `Arabia/Persia` => none
        case `Armenia` => byCountry("AM").some
        case `Austria` => byCountry("AT").some
        case `Azerbaijan` => byCountry("AZ").some
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
        case `India/Sri Lanka` => (byCountry("IN") ++ byCountry("LK")).some
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
