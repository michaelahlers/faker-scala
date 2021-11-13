package ahlers.faker.plugins.uscensus2000

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

    val partialEntry = DictionaryEntry(_, _, Rank(0))

    val entries = IndexedSeq(
      partialEntry(Usage.Sur, Name("Alpha"))
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
        "Alpha"
      )))

    usageFile
      .lines(StandardCharsets.US_ASCII)
      .toSeq
      .should(matchTo(Seq(
        "0,S"
      )))

  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    nameFile: File,
    usageFile: File,
    writeEntries: DictionaryEntriesWriter)

}
