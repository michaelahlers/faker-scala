package ahlers.faker.scalacheck.name.implicits

import ahlers.faker.PersonGivenName
import ahlers.faker.scalacheck.name.heise.genPersonGivenName
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object heise {
  implicit val arbPersonGivenName: Arbitrary[PersonGivenName] = Arbitrary(genPersonGivenName)
}
