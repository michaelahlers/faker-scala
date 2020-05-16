package heise

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.string._
import eu.timepit.refined.numeric._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
package object ct {

  type LocaleIndexType = Int Refined (NonNegative And LessEqual[W.`55`.T])
  @newtype case class LocaleIndex(toInt: LocaleIndexType)

  type LocaleNameType = String Refined (NonEmpty And Trimmed)
  type LocaleName = LocaleName.Value
  object LocaleName extends Enumeration {

    val GreatBritain, Ireland, UnitedStates, Italy, Malta, Portugal, Spain, France, Belgium, Luxembourg, Netherlands, EastFrisia, Germany, Austria, Swiss, Iceland, Denmark, Norway, Sweden, Finland, Estonia, Latvia, Lithuania, Poland,
      CzechRepublic, Slovakia, Hungary, Romania, Bulgaria, `Bosnia/Herzegovina`, Croatia, Kosovo, Macedonia, Montenegro, Serbia, Slovenia, Albania, Greece, Russia, Belarus, Moldova, Ukraine, Armenia, Azerbaijan, Georgia,
      `Kazakhstan/Uzbekistan`, Turkey, `Arabia/Persia`, Israel, China, `India/Sri Lanka`, Japan, Korea, Vietnam, Other = Value

    val byLabel: Map[String, LocaleName] = Map(
      "Great Britain" -> GreatBritain,
      "Ireland" -> Ireland,
      "U.S.A." -> UnitedStates,
      "Italy" -> Italy,
      "Malta" -> Malta,
      "Portugal" -> Portugal,
      "Spain" -> Spain,
      "France" -> France,
      "Belgium" -> Belgium,
      "Luxembourg" -> Luxembourg,
      "the Netherlands" -> Netherlands,
      "East Frisia" -> EastFrisia,
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
      "Czech Republic" -> CzechRepublic,
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

  type GivenNameProbabilityType = Int Refined (NonNegative And LessEqual[W.`13`.T])
  @newtype case class GivenNameProbability(toInt: GivenNameProbabilityType)

  type EquivalentNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class EquivalentName(toText: EquivalentNameType)

}
