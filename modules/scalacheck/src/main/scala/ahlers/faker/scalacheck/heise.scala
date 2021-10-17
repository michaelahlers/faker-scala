package ahlers.faker.scalacheck

import ahlers.faker.datasets.heise._
import ahlers.faker._
import org.scalacheck.Gen._
import org.scalacheck._
import eu.timepit.refined.api.Refined

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object heise {

  /** @todo Restore consideration for variations and equivalents. */
  def genPersonGivenName: Gen[PersonGivenName] =
    ???
  /*oneOf(
      givenNames
        .map(_.template)
        .map(name =>
          PersonGivenName(Refined.unsafeApply(name.toText)))
        .toIndexedSeq)*/

}
