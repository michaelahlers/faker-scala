package ahlers.faker.plugins.heise

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import java.util.Locale
import scala.util.control.NonFatal
import scala.collection.convert.ImplicitConversionsToScala._
import net.ceedubs.ficus.readers.ValueReader
import net.ceedubs.ficus.Ficus._

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class Region(label: RegionLabel, countryCodes: Set[CountryCode])
object Region {

//val values: Set[Region] =
//  try ConfigFactory
//    .load()
//    .getConfigList("regions")
//    .map(region =>
//      Region(
//        label = region.getString("label"),
//        countryCodes = region.getStringList("country-codes").toSet
//      ))
//    .toSet
//  catch {
//    case NonFatal(reason) =>
//      reason.printStackTrace()
//      throw reason
//  }

//val byLabel: Map[String, Region] =
//  values
//    .map(region => (region.label, region))
//    .toMap

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
