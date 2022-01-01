package ahlers.faker.scalacheck.implicits.opendata500.companies

import ahlers.faker.CompanyWebsite
import ahlers.faker.scalacheck.opendata500.companies.websites._
import org.scalacheck.Arbitrary

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since January 1, 2022
 */
object websites {
  implicit val arbCompanyWebsite: Arbitrary[CompanyWebsite] = Arbitrary(genCompanyWebsite)
}
