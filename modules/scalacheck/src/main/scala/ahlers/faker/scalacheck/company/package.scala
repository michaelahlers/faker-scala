package ahlers.faker.scalacheck

import ahlers.faker.Company
import org.scalacheck.Gen

package object company {
  val genCompany: Gen[Company] = opendata500.genCompany
}
