package ahlers.faker.scalacheck.implicits

import ahlers.faker._
import ahlers.faker.scalacheck.census2000._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object census2000 {
  implicit val arbPersonFamilyName: Arbitrary[PersonFamilyName] = Arbitrary(genPersonFamilyName)
}
