package ahlers.faker.scalacheck.name

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class NameSpec extends AnyWordSpecLike {
  "Person given names" in {
    import ScalaCheckPropertyChecks._
    forAll(genPersonGivenName) { _ => }
  }

  "Person family names" in {
    import ScalaCheckPropertyChecks._
    forAll(genPersonFamilyName) { _ => }
  }
}
