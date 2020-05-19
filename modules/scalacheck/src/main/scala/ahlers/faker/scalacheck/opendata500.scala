package ahlers.faker.scalacheck

import ahlers.faker._
import ahlers.faker.datasets.opendata500._
import org.scalacheck.Gen._
import org.scalacheck._

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
