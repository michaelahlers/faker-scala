package ahlers.faker.scalacheck.opendata500.companies

import ahlers.faker.Company
import ahlers.faker.CompanyName
import eu.timepit.refined.api.Refined
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf

/**
 * @since December 06, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object names {

  /** @todo */
  // val genCompanyKr: Gen[CompanyName] = oneOf(Nil) // companies.all.toIndexedSeq)

  /** @todo */
  // val genCompanyUs: Gen[CompanyName] = oneOf(Nil) // companies.all.toIndexedSeq)

  val genCompanyName: Gen[CompanyName] =
    oneOf(ahlers.faker.datasets.opendata500
      .companies
      .names
      .all
      .map(name => CompanyName(Refined.unsafeApply(name.toText))))
  // oneOf(genCompanyKr, genCompanyUs)

}
