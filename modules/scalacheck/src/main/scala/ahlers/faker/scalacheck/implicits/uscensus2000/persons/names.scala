package ahlers.faker.scalacheck.implicits.uscensus2000.persons

import ahlers.faker._
import ahlers.faker.scalacheck.uscensus2000.persons.names._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object names {
  implicit val arbPersonFamilyName: Arbitrary[PersonFamilyName] = Arbitrary(genPersonFamilyName)
}
