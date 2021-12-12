package ahlers.faker.scalacheck.name.implicits

import ahlers.faker.PersonFamilyName
import ahlers.faker.PersonGivenName
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class NameSpec extends AnyWordSpec {

  "Person given names" in {
    import ScalaCheckPropertyChecks._
    forAll { _: PersonGivenName => }
  }

  "Person family names" in {
    import ScalaCheckPropertyChecks._
    forAll { _: PersonFamilyName => }
  }
}
