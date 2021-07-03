package ahlers.faker.scalacheck.name.implicits

import ahlers.faker.scalacheck.name.census1990.genPersonFamilyName
import ahlers.faker.scalacheck.name.census1990.genPersonGivenName
import ahlers.faker.PersonFamilyName
import ahlers.faker.PersonGivenName
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object census1990 {
  implicit val arbPersonGivenName: Arbitrary[PersonGivenName] = Arbitrary(genPersonGivenName)
  implicit val arbPersonFamilyName: Arbitrary[PersonFamilyName] = Arbitrary(genPersonFamilyName)
}
