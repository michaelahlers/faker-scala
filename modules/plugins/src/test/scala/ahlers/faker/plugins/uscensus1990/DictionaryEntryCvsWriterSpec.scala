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
class DictionaryEntryCvsWriterSpec extends FixtureAnyWordSpec {
  import DictionaryEntryCvsWriterSpec._

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

      } yield {
        info(s"""Name file: "$nameFile"; usage file: "$usageFile".""")

        Fixtures(
          nameFile = nameFile,
          usageFile = usageFile,
          writeEntries = writer
        )
      }

    fixturesF
      .map(test.toNoArgTest(_))
      .apply(withFixture(_))
  }

  "Consolidate usages to duplicate names" in { fixtures =>
    import fixtures.nameFile
    import fixtures.usageFile
    import fixtures.writeEntries

    val partialEntry = DictionaryEntry(_, _, Frequency(0), CumulativeFrequency(0), Rank(0))

    val entries = IndexedSeq(
      partialEntry(Usage.FemaleFirst, Name("Alpha")),
      partialEntry(Usage.MaleFirst, Name("Bravo")),
      partialEntry(Usage.Last, Name("Charlie")),
      partialEntry(Usage.FemaleFirst, Name("Delta")),
      partialEntry(Usage.MaleFirst, Name("Delta")),
      partialEntry(Usage.FemaleFirst, Name("Echo")),
      partialEntry(Usage.MaleFirst, Name("Echo")),
      partialEntry(Usage.Last, Name("Echo")),
      partialEntry(Usage.FemaleFirst, Name("Foxtrot")),
      partialEntry(Usage.FemaleFirst, Name("Foxtrot")),
      partialEntry(Usage.MaleFirst, Name("Foxtrot")),
      partialEntry(Usage.MaleFirst, Name("Foxtrot")),
      partialEntry(Usage.Last, Name("Foxtrot")),
      partialEntry(Usage.Last, Name("Foxtrot"))
    )

    writeEntries(entries)
      .should(matchTo(Seq(
        nameFile.toJava,
        usageFile.toJava
      )))

    nameFile
      .lines(StandardCharsets.US_ASCII)
      .toSeq
      .should(matchTo(Seq(
        "Alpha",
        "Bravo",
        "Charlie",
        "Delta",
        "Echo",
        "Foxtrot"
      )))

    usageFile
      .lines(StandardCharsets.US_ASCII)
      .toSeq
      .should(matchTo(Seq(
        "0,F",
        "1,M",
        "2,L",
        "3,F",
        "3,M",
        "4,F",
        "4,M",
        "4,L",
        "5,F",
        "5,M",
        "5,L"
      )))

  }

}

object DictionaryEntryCvsWriterSpec {

  case class Fixtures(
    nameFile: File,
    usageFile: File,
    writeEntries: DictionaryEntriesWriter)

}
