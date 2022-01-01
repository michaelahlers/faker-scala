package ahlers.faker.scalacheck

import ahlers.faker.scalacheck.all._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 19, 2020
 */
class AllSpec extends AnyWordSpec {

  "Company names" in {
    import ScalaCheckPropertyChecks._
    forAll(genCompanyName) { (_) => }
  }

  "Person given names" in {
    import ScalaCheckPropertyChecks._
    forAll(genPersonGivenName) { (_) => }
  }

  "Person family names" in {
    import ScalaCheckPropertyChecks._
    forAll(genPersonFamilyName) { (_) => }
  }

}
