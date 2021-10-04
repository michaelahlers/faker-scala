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

  val genPersonGivenName: Gen[PersonGivenName] =
    /** @todo Restore consideration for variations and equivalents. */
    oneOf(
      givenNames
        .map(_.name)
        .map(name =>
          PersonGivenName(Refined.unsafeApply(name.toText)))
        .toIndexedSeq)

}
