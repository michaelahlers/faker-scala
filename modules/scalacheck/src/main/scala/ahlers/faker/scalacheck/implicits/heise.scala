package ahlers.faker.scalacheck.implicits

import ahlers.faker._
import ahlers.faker.scalacheck.heise._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object heise {
  implicit val arbPersonGivenName: Arbitrary[PersonGivenName] = Arbitrary(genPersonGivenName)
}
