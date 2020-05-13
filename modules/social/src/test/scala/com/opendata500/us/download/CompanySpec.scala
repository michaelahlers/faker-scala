package com.opendata500.us.download

import ahlers.faker.social.CompanyName
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
            CompanyName("3 Round Stones, Inc.")
          ))
      }

      Company.values(99) should {
        matchTo(
          Company(
            CompanyId("cloudspyre"),
            CompanyName("Cloudspyre")
          ))
      }

      Company.values(199) should {
        matchTo(
          Company(
            CompanyId("govtribe"),
            CompanyName("GovTribe")
          ))
      }

      Company.values(299) should {
        matchTo(
          Company(
            CompanyId("mhealthcoach"),
            CompanyName("mHealthCoach")
          ))
      }

      Company.values(399) should {
        matchTo(
          Company(
            CompanyId("reed-elsevier"),
            CompanyName("Reed Elsevier")
          ))
      }

      Company.values(499) should {
        matchTo(
          Company(
            CompanyId("walk-score"),
            CompanyName("Walk Score")
          ))
      }
    }
  }

}
