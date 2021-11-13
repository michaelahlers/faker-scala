package ahlers.faker.plugins.uscensus2000

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

  "Read and parse entries file" in { fixtures =>
    import fixtures.{ sourceFile, readEntries }

    sourceFile.appendLines(
      "name,rank,count,prop100k,cum_prop100k,pctwhite,pctblack,pctapi,pctaian,pct2prace,pcthispanic",
      "Alpha,1,2376206,880.85,880.85,73.35,22.22,0.40,0.85,1.63,1.56",
      "Bravo,2,1857160,688.44,1569.30,61.55,33.80,0.42,0.91,1.82,1.50",
      "Charlie,3,1534042,568.66,2137.96,48.52,46.72,0.37,0.78,2.01,1.60"
    )

    readEntries(
      usage = Usage.Sur,
      sourceFile = sourceFile.toJava)
      .should(matchTo(Seq(
        DictionaryEntry(Usage.Sur, Name("Alpha"), Rank(1)),
        DictionaryEntry(Usage.Sur, Name("Bravo"), Rank(2)),
        DictionaryEntry(Usage.Sur, Name("Charlie"), Rank(3))
      )))

  }

}

object DictionaryEntriesReaderSpec {

  case class Fixtures(
    sourceFile: File,
    readEntries: DictionaryEntriesReader)

}
