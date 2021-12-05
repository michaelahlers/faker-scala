package ahlers.faker.plugins.uscensus2000

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec

import java.io.InputStream

/**
 * @since November 13, 2021
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
            .getAsStream("given_entries.csv")
            .autoClosed

        readEntries =
          DictionaryEntriesReader
            .using(
              parseEntry = DictionaryEntryParser.default
            )

      } yield withFixture(test.toNoArgTest(Fixtures(
        entriesStream = entriesStream,
        readEntries = readEntries
      )))

    outcomeF.get()
  }

  "Read and parse entries file" in { fixtures =>
    import fixtures.{ entriesStream, readEntries }

    readEntries(
      usage = Usage.Sur,
      entriesStream = entriesStream)
      .should(matchTo(Seq(
        DictionaryEntry(Usage.Sur, Name("Alpha"), Rank(1)),
        DictionaryEntry(Usage.Sur, Name("Bravo"), Rank(2)),
        DictionaryEntry(Usage.Sur, Name("Charlie"), Rank(3))
      )))

  }

}

object DictionaryEntriesReaderSpec {

  case class Fixtures(
    entriesStream: InputStream,
    readEntries: DictionaryEntriesReader)

}
