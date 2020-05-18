package ahlers.faker.datasets.opendata500

import ahlers.faker.models._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
class CompaniesKrLoaderSpec extends AnyWordSpec with BeforeAndAfterAll {

  val loader = CompaniesKrLoader()

  override def afterAll(): Unit = {
    super.afterAll()
    loader.close()
  }

  "Companies (Korean)" in {
    val companies = loader.companies().toIndexedSeq

    companies.size.should(be(301))

    companies(0) should {
      matchTo(
        Company(
          CompanyId("-airblack-inc"),
          CompanyName("airblack Inc."),
          Seq(CompanyWebsite("www.airblack.com"))
        ))
    }

    companies(99) should {
      matchTo(
        Company(
          CompanyId("i-s-m-s-"),
          CompanyName("I S M S"),
          Seq(CompanyWebsite("http://www.isms.re.kr"))
        ))
    }

    companies(199) should {
      matchTo(
        Company(
          CompanyId("onycom-"),
          CompanyName("onycom"),
          Seq(CompanyWebsite("http://www.onycom.com"))
        ))
    }

    companies(299) should {
      matchTo(
        Company(
          CompanyId("ywmobile"),
          CompanyName("YWMobile"),
          Seq(CompanyWebsite("http://www.evercon.me/homepage/index.html"))
        ))
    }

    //loader.companies().size.should(be(0))
  }

}
