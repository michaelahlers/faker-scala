package ahlers.faker.plugins.uscensus1990.persons

import better.files._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger
import org.scalatest.matchers.should.Matchers._
import com.softwaremill.diffx.scalatest.DiffShouldMatcher._
import com.softwaremill.diffx.generic.auto._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class DictionaryEntriesCsvWriterSpec extends FixtureAnyWordSpec {
  import DictionaryEntriesCsvWriterSpec._

  override type FixtureParam = Fixtures
  override protected def withFixture(test: OneArgTest) = {
    val outcomeF =
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

        writeEntries =
          DictionaryEntriesCsvWriter
            .using(
              logger = Logger.Null
            )

      } yield {
        info(s"""Name file: "$nameFile"; usage file: "$usageFile".""")

        withFixture(test.toNoArgTest(Fixtures(
          nameFile = nameFile,
          usageFile = usageFile,
          writeEntries = writeEntries
        )))
      }

    outcomeF.get()
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

    writeEntries(
      dictionaryEntries = entries,
      nameFile = nameFile.toJava,
      usageFile = usageFile.toJava
    )

    nameFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .shouldMatchTo(Resource.my
        .getAsStream("expected_name.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq)

    usageFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .shouldMatchTo(Resource.my
        .getAsStream("expected_name-index,usage.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq)

  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    nameFile: File,
    usageFile: File,
    writeEntries: DictionaryEntriesCsvWriter)

}
