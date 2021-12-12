package ahlers.faker.scalacheck.implicits.opendata500.companies

import ahlers.faker.scalacheck.opendata500.companies.names._
import ahlers.faker.CompanyName
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object names {
  implicit val arbCompanyName: Arbitrary[CompanyName] = Arbitrary(genCompanyName)
}
