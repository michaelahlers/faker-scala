package ahlers.faker.scalacheck.opendata500.companies

import ahlers.faker.CompanyWebsite
import eu.timepit.refined.api.Refined
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf

/**
 * @since December 06, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object websites {

  val genCompanyWebsite: Gen[CompanyWebsite] =
    oneOf(ahlers.faker.datasets.opendata500
      .companies
      .websites
      .all
      .map(website => CompanyWebsite(Refined.unsafeApply(website.toText))))

}
