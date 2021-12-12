package ahlers.faker.scalacheck.name.implicits

import ahlers.faker.PersonFamilyName
import ahlers.faker.PersonGivenName
import ahlers.faker.scalacheck.name.genPersonFamilyName
import ahlers.faker.scalacheck.name.genPersonGivenName
import org.scalacheck.Arbitrary

trait NameArbitraryInstances {
  implicit val arbPersonGivenName: Arbitrary[PersonGivenName] = Arbitrary(genPersonGivenName)
  implicit val arbPersonFamilyName: Arbitrary[PersonFamilyName] = Arbitrary(genPersonFamilyName)
}
