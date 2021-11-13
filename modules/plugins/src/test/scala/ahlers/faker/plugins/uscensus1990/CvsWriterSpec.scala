package ahlers.faker.plugins.uscensus1990

import better.files._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger
import org.scalatest.matchers.should.Matchers._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class CvsWriterSpec extends FixtureAnyWordSpec {
  import CvsWriterSpec._

  better.files.File.temporaryFile()

  override type FixtureParam = Fixtures
  override protected def withFixture(test: OneArgTest) = {
    val fixturesF =
      for {

        nameFile <-
          File.temporaryFile(
            prefix = "name",
            suffix = ".csv"
          )

        usageFile <-
          File.temporaryFile(
            prefix = "usage",
            suffix = ".csv"
          )

        writer =
          DictionaryEntriesCsvWriter
            .using(
              nameFile = nameFile.toJava,
              usageFile = usageFile.toJava,
              logger = Logger.Null
            )

      } yield Fixtures(
        nameFile = nameFile,
        usageFile = usageFile,
        writeEntries = writer
      )

    fixturesF
      .map(test.toNoArgTest(_))
      .apply(withFixture(_))
  }

  "Consolidate duplicate names" in { fixtures =>
    import fixtures.nameFile
    import fixtures.usageFile
    import fixtures.writeEntries

    val partialEntry = DictionaryEntry(_, _, Frequency(0), CumulativeFrequency(0), Rank(0))

    val entries = IndexedSeq(
      partialEntry(Usage.FemaleFirst, Name("Alpha")),
      partialEntry(Usage.FemaleFirst, Name("Bravo")),
      partialEntry(Usage.MaleFirst, Name("Bravo")),
      partialEntry(Usage.FemaleFirst, Name("Charlie")),
      partialEntry(Usage.MaleFirst, Name("Charlie")),
      partialEntry(Usage.FemaleFirst, Name("Charlie")),
      partialEntry(Usage.Last, Name("Charlie"))
    )

    writeEntries(entries)

    nameFile
      .lines(StandardCharsets.US_ASCII)
      .toSeq
      .should(matchTo(Seq(
        "Alpha",
        "Bravo",
        "Charlie"
      )))

    usageFile
      .lines(StandardCharsets.US_ASCII)
      .toSeq
      .should(matchTo(Seq(
        "0,F",
        "1,F",
        "1,M",
        "2,F",
        "2,M",
        "2,L"
      )))

  }

}

object CvsWriterSpec {

  case class Fixtures(
    nameFile: File,
    usageFile: File,
    writeEntries: DictionaryEntriesWriter)

}
