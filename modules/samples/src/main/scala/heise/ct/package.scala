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

  type GivenNameProbabilityType = Int Refined (NonNegative And LessEqual[W.`13`.T])
  @newtype case class GivenNameProbability(toInt: GivenNameProbabilityType)

  type EquivalentNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class EquivalentName(toText: EquivalentNameType)

}
