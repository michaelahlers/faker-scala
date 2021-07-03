package ahlers.faker.scalacheck.company
package implicits

import ahlers.faker.Company
import org.scalacheck.Arbitrary

trait CompanyArbitraryInstances {
  implicit val arbCompany: Arbitrary[Company] = Arbitrary(genCompany)
}
