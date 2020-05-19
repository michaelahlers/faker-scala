package ahlers.faker.scalacheck.implicits

import ahlers.faker._
import ahlers.faker.scalacheck.opendata500._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object opendata500 {
  implicit val arbCompany: Arbitrary[Company] = Arbitrary(genCompany)
}
