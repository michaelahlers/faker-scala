package ahlers.faker.plugins.uscensus1990.persons

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffShouldMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger

import java.io.InputStream
import java.nio.charset.StandardCharsets

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
      usage = Usage.FemaleFirst,
      entriesStream = entriesStream)
      .shouldMatchTo(Seq(
        DictionaryEntry(Usage.FemaleFirst, Name("Alpha"), Frequency(0.1f), CumulativeFrequency(0.1f), Rank(1)),
        DictionaryEntry(Usage.FemaleFirst, Name("Bravo"), Frequency(0.2f), CumulativeFrequency(0.3f), Rank(2)),
        DictionaryEntry(Usage.FemaleFirst, Name("Charlie"), Frequency(0.3f), CumulativeFrequency(0.6f), Rank(3))
      ))

  }

}

object DictionaryEntriesReaderSpec {

  case class Fixtures(
    entriesStream: InputStream,
    readEntries: DictionaryEntriesReader)

}
