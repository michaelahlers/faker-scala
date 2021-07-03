package ahlers.faker.scalacheck.company

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class CompanySpec extends AnyWordSpecLike {
  "Companies" in {
    import ScalaCheckPropertyChecks._
    forAll(genCompany) { _ => }
  }
}
