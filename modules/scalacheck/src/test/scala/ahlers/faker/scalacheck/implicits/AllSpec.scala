package ahlers.faker.scalacheck.implicits

import ahlers.faker._
import ahlers.faker.scalacheck.implicits.all._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 19, 2020
 */
class AllSpec extends AnyWordSpec {

  "Company names" in {
    import ScalaCheckPropertyChecks._
    forAll { (_: CompanyName) => }
  }

  "Company websites" in {
    import ScalaCheckPropertyChecks._
    forAll { (_: CompanyWebsite) => }
  }

  "Person given names" in {
    import ScalaCheckPropertyChecks._
    forAll { (_: PersonGivenName) => }
  }

  "Person family names" in {
    import ScalaCheckPropertyChecks._
    forAll { (_: PersonFamilyName) => }
  }

}
