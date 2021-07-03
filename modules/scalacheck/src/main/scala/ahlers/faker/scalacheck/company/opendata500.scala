package ahlers.faker.scalacheck.company

import ahlers.faker.Company
import ahlers.faker.datasets.opendata500.CompanyKrIterator
import ahlers.faker.datasets.opendata500.CompanyUsIterator
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object opendata500 {

  val genCompanyKr: Gen[Company] = oneOf(CompanyKrIterator().toIndexedSeq)
  val genCompanyUs: Gen[Company] = oneOf(CompanyUsIterator().toIndexedSeq)

  val genCompany: Gen[Company] =
    oneOf(genCompanyKr, genCompanyUs)

}
