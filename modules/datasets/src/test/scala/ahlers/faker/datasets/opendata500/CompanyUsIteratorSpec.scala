package ahlers.faker.datasets.opendata500

import ahlers.faker
import ahlers.faker.Company
import ahlers.faker._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Outcome
import org.scalatest.fixture
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
class CompanyUsIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[Company]
  override protected def withFixture(test: OneArgTest) = {
    val loader = CompanyUsIterator()
    try withFixture(test.toNoArgTest(loader.toIndexedSeq))
    finally loader.close()
  }

  "Companies (United States)" in { companies =>
    companies.size.should(be(529))

    companies(0).should(
      matchTo(
        faker.Company(
          CompanyId("3-round-stones-inc"),
          CompanyName("3 Round Stones, Inc."),
          Seq(CompanyWebsite("http://3RoundStones.com"))
        )))

    companies(99).should(
      matchTo(
        faker.Company(
          CompanyId("cloudspyre"),
          CompanyName("Cloudspyre"),
          Seq(CompanyWebsite("http://www.cloudspyre.com"))
        )))

    companies(199).should(
      matchTo(
        faker.Company(
          CompanyId("govtribe"),
          CompanyName("GovTribe"),
          Seq(CompanyWebsite("govtribe.com"))
        )))

    companies(299).should(
      matchTo(
        faker.Company(
          CompanyId("mhealthcoach"),
          CompanyName("mHealthCoach"),
          Seq(CompanyWebsite("http://mhealthcoach.com"))
        )))

    companies(399).should(
      matchTo(
        faker.Company(
          CompanyId("reed-elsevier"),
          CompanyName("Reed Elsevier"),
          Seq(CompanyWebsite("http://www.reedelsevier.com/Pages/Home.aspx"))
        )))

    companies(499).should(
      matchTo(
        faker.Company(
          CompanyId("walk-score"),
          CompanyName("Walk Score"),
          Seq(CompanyWebsite("www.walkscore.com"))
        )))

  }

}
