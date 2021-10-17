package ahlers.faker.datasets.heise

import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.LessEqual
import eu.timepit.refined.numeric.NonNegative

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class Weight(toInt: Int) extends AnyVal
object Weight {

  /** @todo Revisit. */
  //type WeightType = Int Refined (NonNegative And LessEqual[W.`13`.T])

}
