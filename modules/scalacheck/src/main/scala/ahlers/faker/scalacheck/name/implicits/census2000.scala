package ahlers.faker.scalacheck.name.implicits

import ahlers.faker.PersonFamilyName
import ahlers.faker.scalacheck.name.census2000.genPersonFamilyName
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object census2000 {
  implicit val arbPersonFamilyName: Arbitrary[PersonFamilyName] = Arbitrary(genPersonFamilyName)
}
