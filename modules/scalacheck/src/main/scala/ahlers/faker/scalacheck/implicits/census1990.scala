package ahlers.faker.scalacheck.implicits

import ahlers.faker._
import ahlers.faker.scalacheck.census1990._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object census1990 {
  implicit val arbPersonGivenName: Arbitrary[PersonGivenName] = Arbitrary(genPersonGivenName)
  implicit val arbPersonFamilyName: Arbitrary[PersonFamilyName] = Arbitrary(genPersonFamilyName)
}
