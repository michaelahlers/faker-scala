package heise

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.numeric._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
package object ct {

  type LocaleProbabilityType = Int Refined (NonNegative And LessEqual[W.`13`.T])
  @newtype case class LocaleProbability(toInt: LocaleProbabilityType)

}
