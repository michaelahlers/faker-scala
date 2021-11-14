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

  /** @todo */
  val genCompanyKr: Gen[Company] = oneOf(Nil) // companies.all.toIndexedSeq)

  /** @todo */
  val genCompanyUs: Gen[Company] = oneOf(Nil) // companies.all.toIndexedSeq)

  val genCompany: Gen[Company] =
    oneOf(genCompanyKr, genCompanyUs)

}
