package ahlers.faker.scalacheck.company.implicits

import ahlers.faker.Company
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class CompanySpec extends AnyWordSpecLike {
  "Companies" in {
    import ScalaCheckPropertyChecks._
    forAll { _: Company => }
  }
}
