package ahlers.faker.plugins.opendata500

import better.files._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger
import org.scalatest.matchers.should.Matchers._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._

import java.nio.charset.StandardCharsets

/**
 * @since November 14, 2021
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

        websiteFile <-
          File.temporaryFile(
            prefix = "website",
            suffix = ".csv"
          )

        writer =
          DictionaryEntriesCsvWriter
            .using(
              nameFile = nameFile.toJava,
              websiteFile = websiteFile.toJava,
              logger = Logger.Null
            )

      } yield {
        info(s"""Name file: "$nameFile"; usage file: "$websiteFile".""")

        Fixtures(
          nameFile = nameFile,
          usageFile = websiteFile,
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

    val partialEntry = DictionaryEntry(CompanyId(""), _, _)

    val entries = IndexedSeq(
      partialEntry(CompanyName("Alpha"), CompanyWebsite("http://alpha.com")),
      partialEntry(CompanyName("Bravo"), CompanyWebsite("http://bravo0.com")),
      partialEntry(CompanyName("Bravo"), CompanyWebsite("http://bravo1.com"))
    )

    writeEntries(entries)
      .should(matchTo(Seq(
        nameFile.toJava,
        usageFile.toJava
      )))

    nameFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Seq(
        "Alpha",
        "Bravo"
      )))

    usageFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Seq(
        "0,http://alpha.com",
        "1,http://bravo0.com",
        "1,http://bravo1.com"
      )))

  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    nameFile: File,
    usageFile: File,
    writeEntries: DictionaryEntriesWriter)

}
