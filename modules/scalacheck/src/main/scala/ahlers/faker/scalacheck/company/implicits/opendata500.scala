package ahlers.faker.scalacheck.company.implicits

import ahlers.faker.Company
import ahlers.faker.scalacheck.company.opendata500.genCompany
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object opendata500 {
  implicit val arbCompany: Arbitrary[Company] = Arbitrary(genCompany)
}
