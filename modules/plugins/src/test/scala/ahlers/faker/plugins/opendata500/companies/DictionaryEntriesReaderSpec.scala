package ahlers.faker.plugins.opendata500.companies

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffShouldMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger

import java.io.InputStream

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class DictionaryEntriesReaderSpec extends FixtureAnyWordSpec {
  import DictionaryEntriesReaderSpec._

  override type FixtureParam = Fixtures
  override protected def withFixture(test: OneArgTest) = {
    val outcomeF =
      for {

        entriesStream <-
          Resource.my
            .getAsStream("given_companies.csv")
            .autoClosed

        readEntries =
          DictionaryEntriesReader
            .using(
              logger = Logger.Null
            )

      } yield withFixture(test.toNoArgTest(Fixtures(
        entriesStream = entriesStream,
        readEntries = readEntries
      )))

    outcomeF.get()
  }

  "Read and parse entries file" in { fixtures =>
    import fixtures.{ entriesStream, readEntries }

    readEntries(entriesStream)
      .shouldMatchTo(Seq(
        DictionaryEntry(CompanyId("alpha"), CompanyName("Alpha, Inc."), Some(CompanyWebsite("http://alpha.com"))),
        DictionaryEntry(CompanyId("bravo"), CompanyName("Bravo, LLC"), Some(CompanyWebsite("http://bravo.com")))
      ))
  }

}

object DictionaryEntriesReaderSpec {

  case class Fixtures(
    entriesStream: InputStream,
    readEntries: DictionaryEntriesReader)

}
