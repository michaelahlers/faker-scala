package com.opendata500.us.download

import ahlers.faker.social.CompanyName
import ahlers.faker.social.CompanyHomepage
import eu.timepit.refined.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.wordspec._
import org.scalatest.matchers.should.Matchers._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
class CompanySpec extends AnyWordSpec {

  "Company values" must {
    "contain Open Data 500 US dataset" in {
      Company.values.size should be(529)

      Company.values(0) should {
        matchTo(
          Company(
            CompanyId("3-round-stones-inc"),
            CompanyName("3 Round Stones, Inc."),
            CompanyHomepage("http://3RoundStones.com")
          ))
      }

      Company.values(99) should {
        matchTo(
          Company(
            CompanyId("cloudspyre"),
            CompanyName("Cloudspyre"),
            CompanyHomepage("http://www.cloudspyre.com")
          ))
      }

      Company.values(199) should {
        matchTo(
          Company(
            CompanyId("govtribe"),
            CompanyName("GovTribe"),
            CompanyHomepage("govtribe.com")
          ))
      }

      Company.values(299) should {
        matchTo(
          Company(
            CompanyId("mhealthcoach"),
            CompanyName("mHealthCoach"),
            CompanyHomepage("http://mhealthcoach.com")
          ))
      }

      Company.values(399) should {
        matchTo(
          Company(
            CompanyId("reed-elsevier"),
            CompanyName("Reed Elsevier"),
            CompanyHomepage("http://www.reedelsevier.com/Pages/Home.aspx")
          ))
      }

      Company.values(499) should {
        matchTo(
          Company(
            CompanyId("walk-score"),
            CompanyName("Walk Score"),
            CompanyHomepage("www.walkscore.com")
          ))
      }
    }
  }

}
