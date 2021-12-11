package ahlers.faker.plugins.jörgmichael.persons

import java.util.Locale
import net.ceedubs.ficus.readers.ValueReader
import net.ceedubs.ficus.Ficus._

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class Region(label: RegionLabel, countryCodes: Set[CountryCode])
object Region {

  /** Allows for factory overloading without breaking [[ValueReader]] derivation. */
  implicit class CompanionOps(private val self: Region.type) extends AnyVal {
    def apply(label: RegionLabel): Region =
      Region(
        label = label,
        countryCodes = Set.empty)
  }

  object HasLocales {

    val byCountryCode: Map[CountryCode, Seq[Locale]] =
      Locale.getAvailableLocales
        .toSeq
        .groupBy(locale =>
          CountryCode(locale.getCountry))

    def unapply(region: Region): Option[Set[Locale]] =
      Option(region
        .countryCodes
        .flatMap(byCountryCode.get(_))
        .flatten)
        .filter(_.nonEmpty)

  }

  implicit val valueReader: ValueReader[Region] = {
    import net.ceedubs.ficus.readers.ArbitraryTypeReader
    import net.ceedubs.ficus.readers.namemappers.implicits.hyphenCase
    implicit val regionLabelValueReader: ValueReader[RegionLabel] = ValueReader[String].map(RegionLabel(_))
    implicit val countryCodeValueReader: ValueReader[CountryCode] = ValueReader[String].map(CountryCode(_))
    ArbitraryTypeReader.arbitraryTypeValueReader[Region].value
  }

}
