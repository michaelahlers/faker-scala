package ahlers.faker.scalacheck.implicits.jörgmichael.persons

import ahlers.faker.PersonGivenName
import ahlers.faker.scalacheck.jörgmichael.persons.names.genPersonGivenName
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object names {
  implicit val arbPersonGivenName: Arbitrary[PersonGivenName] = Arbitrary(genPersonGivenName)
}
