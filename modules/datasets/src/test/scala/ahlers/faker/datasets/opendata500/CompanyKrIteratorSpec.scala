package ahlers.faker.datasets.opendata500

import ahlers.faker
import ahlers.faker.Company
import ahlers.faker._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
class CompanyKrIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[Company]
  override protected def withFixture(test: OneArgTest) = {
    val loader = CompanyKrIterator()
    try withFixture(test.toNoArgTest(loader.toIndexedSeq))
    finally loader.close()
  }

  "Companies (Korean)" in { companies =>
    companies.size.should(be(301))

    companies(0).should(
      matchTo(
        faker.Company(
          CompanyId("-airblack-inc"),
          CompanyName("airblack Inc."),
          Seq(CompanyWebsite("www.airblack.com"))
        )))

    companies(99).should(
      matchTo(
        faker.Company(
          CompanyId("i-s-m-s-"),
          CompanyName("I S M S"),
          Seq(CompanyWebsite("http://www.isms.re.kr"))
        )))

    companies(199).should(
      matchTo(
        faker.Company(
          CompanyId("onycom-"),
          CompanyName("onycom"),
          Seq(CompanyWebsite("http://www.onycom.com"))
        )))

    companies(299).should(
      matchTo(
        faker.Company(
          CompanyId("ywmobile"),
          CompanyName("YWMobile"),
          Seq(CompanyWebsite("http://www.evercon.me/homepage/index.html"))
        )))
  }

}
