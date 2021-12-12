package ahlers.faker.plugins.uscensus2000.persons

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

      } yield withFixture(test.toNoArgTest(Fixtures(
        nameFile = nameFile,
        usageFile = usageFile,
        writeEntries = writeEntries
      )))

    outcomeF.get()
  }

  "Consolidate usages to duplicate names" in { fixtures =>
    import fixtures.nameFile
    import fixtures.usageFile
    import fixtures.writeEntries

    val partialEntry = DictionaryEntry(_, _, Rank(0))

    val entries = IndexedSeq(
      partialEntry(Usage.Sur, Name("Alpha"))
    )

    writeEntries(
      dictionaryEntries = entries,
      nameFile = nameFile.toJava,
      usageFile = usageFile.toJava
    )

    nameFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Resource.my
        .getAsStream("expected_name.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq))

    usageFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Resource.my
        .getAsStream("expected_name-index,usage.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq))

  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    nameFile: File,
    usageFile: File,
    writeEntries: DictionaryEntriesCsvWriter)

}
