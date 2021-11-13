package ahlers.faker.plugins.uscensus1990

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class DictionaryEntriesReaderSpec extends FixtureAnyWordSpec {
  import DictionaryEntriesReaderSpec._

  override type FixtureParam = Fixtures
  override protected def withFixture(test: OneArgTest) = {
    val fixturesF =
      for {

        nameFile <-
          File.temporaryFile(
            prefix = "entries",
            suffix = ".txt"
          )

        reader =
          DictionaryEntriesReader
            .using(
              parseEntry = DictionaryEntryParser.default
            )

      } yield {
        info(s"""Source file: "$nameFile".""")

        Fixtures(
          sourceFile = nameFile,
          readEntries = reader
        )
      }

    fixturesF
      .map(test.toNoArgTest(_))
      .apply(withFixture(_))
  }

  "Consolidate usages to duplicate names" in { fixtures =>
    import fixtures.{ sourceFile, readEntries }

    sourceFile.appendLines(
      "Alpha 0.1 0.1 1",
      "Bravo  0.2  0.3  2",
      "Charlie   0.3   0.6   3"
    )

    readEntries(
      usage = Usage.FemaleFirst,
      sourceFile = sourceFile.toJava)
      .should(matchTo(Seq(
        DictionaryEntry(Usage.FemaleFirst, Name("Alpha"), Frequency(0.1f), CumulativeFrequency(0.1f), Rank(1)),
        DictionaryEntry(Usage.FemaleFirst, Name("Bravo"), Frequency(0.2f), CumulativeFrequency(0.3f), Rank(2)),
        DictionaryEntry(Usage.FemaleFirst, Name("Charlie"), Frequency(0.3f), CumulativeFrequency(0.6f), Rank(3))
      )))

  }

}

object DictionaryEntriesReaderSpec {

  case class Fixtures(
    sourceFile: File,
    readEntries: DictionaryEntriesReader)

}
