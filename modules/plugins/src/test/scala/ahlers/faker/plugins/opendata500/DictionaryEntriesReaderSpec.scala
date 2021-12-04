package ahlers.faker.plugins.opendata500

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger

import java.io.InputStream
import java.nio.charset.StandardCharsets
import scala.io.Codec
import scala.io.Source

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class DictionaryEntriesReaderSpec extends FixtureAnyWordSpec {
  import DictionaryEntriesReaderSpec._

  override type FixtureParam = Fixtures
  override protected def withFixture(test: OneArgTest) = {
    val fixturesF =
      for {

        entriesSource <-
          Resource.my
            .getAsStream("DictionaryEntriesReaderSpec.csv")
            .autoClosed

        reader =
          DictionaryEntriesReader
            .using(Logger.Null)

      } yield Fixtures(
        entriesSource = entriesSource,
        readEntries = reader
      )

    fixturesF
      .map(test.toNoArgTest(_))
      .apply(withFixture(_))
  }

  "Read and parse entries file" in { fixtures =>
    import fixtures.{ entriesSource, readEntries }

    readEntries(entriesSource)
      .should(matchTo(Seq(
        DictionaryEntry(CompanyId("alpha"), CompanyName("Alpha, Inc."), Some(CompanyWebsite("http://alpha.com"))),
        DictionaryEntry(CompanyId("bravo"), CompanyName("Bravo, LLC"), Some(CompanyWebsite("http://bravo.com")))
      )))
  }

}

object DictionaryEntriesReaderSpec {
  case class Fixtures(
    entriesSource: InputStream,
    readEntries: DictionaryEntriesReader)

}
