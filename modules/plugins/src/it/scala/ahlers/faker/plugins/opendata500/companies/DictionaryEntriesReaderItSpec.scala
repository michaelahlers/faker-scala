package ahlers.faker.plugins.opendata500.companies

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger

import java.io.InputStream

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class DictionaryEntriesReaderItSpec extends FixtureAnyWordSpec {
  import DictionaryEntriesReaderItSpec._

  override type FixtureParam = Fixtures
  override protected def withFixture(test: OneArgTest) = {
    val fixturesF =
      for {

        krCompanySource <-
          Resource
            .getAsStream("www.opendata500.com/kr/download/kr_companies.csv")
            .autoClosed

        usCompanySource <-
          Resource
            .getAsStream("www.opendata500.com/us/download/us_companies.csv")
            .autoClosed

        reader =
          DictionaryEntriesReader
            .using(Logger.Null)

      } yield Fixtures(
        krCompanySource = krCompanySource,
        usCompanySource = usCompanySource,
        readEntries = reader
      )

    fixturesF
      .map(test.toNoArgTest(_))
      .apply(withFixture(_))
  }

  "Companies (Korean)" in { fixtures =>
    import fixtures.{ krCompanySource, readEntries }

    val companies: Seq[DictionaryEntry] =
      readEntries(krCompanySource)

    companies.size.should(be(301))

    companies(0).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("-airblack-inc"),
          name = CompanyName("airblack Inc."),
          website = Some(CompanyWebsite("www.airblack.com"))
        )))

    companies(99).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("i-s-m-s-"),
          name = CompanyName("I S M S"),
          website = Some(CompanyWebsite("http://www.isms.re.kr"))
        )))

    companies(199).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("onycom-"),
          name = CompanyName("onycom"),
          website = Some(CompanyWebsite("http://www.onycom.com"))
        )))

    companies(299).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("ywmobile"),
          name = CompanyName("YWMobile"),
          website = Some(CompanyWebsite("http://www.evercon.me/homepage/index.html"))
        )))
  }

  "Companies (United States)" in { fixtures =>
    import fixtures.{ readEntries, usCompanySource }

    val companies: Seq[DictionaryEntry] =
      readEntries(usCompanySource)

    companies.size.should(be(529))

    companies(0).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("3-round-stones-inc"),
          name = CompanyName("3 Round Stones, Inc."),
          website = Some(CompanyWebsite("http://3RoundStones.com"))
        )))

    companies(99).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("cloudspyre"),
          name = CompanyName("Cloudspyre"),
          website = Some(CompanyWebsite("http://www.cloudspyre.com"))
        )))

    companies(199).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("govtribe"),
          name = CompanyName("GovTribe"),
          website = Some(CompanyWebsite("govtribe.com"))
        )))

    companies(299).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("mhealthcoach"),
          name = CompanyName("mHealthCoach"),
          website = Some(CompanyWebsite("http://mhealthcoach.com"))
        )))

    companies(399).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("reed-elsevier"),
          name = CompanyName("Reed Elsevier"),
          website = Some(CompanyWebsite("http://www.reedelsevier.com/Pages/Home.aspx"))
        )))

    companies(499).should(
      matchTo(
        DictionaryEntry(
          id = CompanyId("walk-score"),
          name = CompanyName("Walk Score"),
          website = Some(CompanyWebsite("www.walkscore.com"))
        )))

  }

}

object DictionaryEntriesReaderItSpec {

  case class Fixtures(
    krCompanySource: InputStream,
    usCompanySource: InputStream,
    readEntries: DictionaryEntriesReader)

}
