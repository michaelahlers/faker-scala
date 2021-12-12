package ahlers.faker.plugins.opendata500.companies

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
    val outcomeF =
      for {

        nameFile <-
          File.temporaryFile(
            prefix = "name",
            suffix = ".csv"
          )

        websiteFile <-
          File.temporaryFile(
            prefix = "name-index,website",
            suffix = ".csv"
          )

        writeEntries =
          DictionaryEntriesCsvWriter
            .using(
              logger = Logger.Null
            )

      } yield withFixture(test.toNoArgTest(Fixtures(
        nameFile = nameFile,
        websiteFile = websiteFile,
        writeEntries = writeEntries
      )))

    outcomeF.get()
  }

  "Consolidate duplicate company names" in { fixtures =>
    import fixtures.nameFile
    import fixtures.websiteFile
    import fixtures.writeEntries

    val partialEntry = DictionaryEntry(CompanyId(""), _, _)

    val entries = IndexedSeq(
      partialEntry(CompanyName("Alpha"), Some(CompanyWebsite("http://alpha.com"))),
      partialEntry(CompanyName("Bravo"), Some(CompanyWebsite("http://bravo0.com"))),
      partialEntry(CompanyName("Bravo"), Some(CompanyWebsite("http://bravo1.com")))
    )

    writeEntries(
      dictionaryEntries = entries,
      nameFile = nameFile.toJava,
      websiteFile = websiteFile.toJava
    )

    nameFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Resource.my
        .getAsStream("expected_name.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq))

    websiteFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Resource.my
        .getAsStream("expected_index,website.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq))

  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    nameFile: File,
    websiteFile: File,
    writeEntries: DictionaryEntriesCsvWriter)

}
