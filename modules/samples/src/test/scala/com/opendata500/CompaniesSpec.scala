package com.opendata500

import ahlers.faker.samples._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
class CompaniesSpec extends AnyWordSpec {

  "Open Data 500 Korean companies dataset" in {
    krCompanies.size should be(301)

    krCompanies(0) should {
      matchTo(
        Company(
          CompanyId("-airblack-inc"),
          CompanyName("airblack Inc."),
          Seq(CompanyWebsite("www.airblack.com"))
        ))
    }

    krCompanies(99) should {
      matchTo(
        Company(
          CompanyId("i-s-m-s-"),
          CompanyName("I S M S"),
          Seq(CompanyWebsite("http://www.isms.re.kr"))
        ))
    }

    krCompanies(199) should {
      matchTo(
        Company(
          CompanyId("onycom-"),
          CompanyName("onycom"),
          Seq(CompanyWebsite("http://www.onycom.com"))
        ))
    }

    krCompanies(299) should {
      matchTo(
        Company(
          CompanyId("ywmobile"),
          CompanyName("YWMobile"),
          Seq(CompanyWebsite("http://www.evercon.me/homepage/index.html"))
        ))
    }
  }

  "Open Data 500 United States companies dataset" in {
    usCompanies.size should be(529)

    usCompanies(0) should {
      matchTo(
        Company(
          CompanyId("3-round-stones-inc"),
          CompanyName("3 Round Stones, Inc."),
          Seq(CompanyWebsite("http://3RoundStones.com"))
        ))
    }

    usCompanies(99) should {
      matchTo(
        Company(
          CompanyId("cloudspyre"),
          CompanyName("Cloudspyre"),
          Seq(CompanyWebsite("http://www.cloudspyre.com"))
        ))
    }

    usCompanies(199) should {
      matchTo(
        Company(
          CompanyId("govtribe"),
          CompanyName("GovTribe"),
          Seq(CompanyWebsite("govtribe.com"))
        ))
    }

    usCompanies(299) should {
      matchTo(
        Company(
          CompanyId("mhealthcoach"),
          CompanyName("mHealthCoach"),
          Seq(CompanyWebsite("http://mhealthcoach.com"))
        ))
    }

    usCompanies(399) should {
      matchTo(
        Company(
          CompanyId("reed-elsevier"),
          CompanyName("Reed Elsevier"),
          Seq(CompanyWebsite("http://www.reedelsevier.com/Pages/Home.aspx"))
        ))
    }

    usCompanies(499) should {
      matchTo(
        Company(
          CompanyId("walk-score"),
          CompanyName("Walk Score"),
          Seq(CompanyWebsite("www.walkscore.com"))
        ))
    }
  }

}
