package ahlers.faker.plugins.heise

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

        templateFile <-
          File.temporaryFile(
            prefix = "template",
            suffix = ".csv"
          )

        usageFile <-
          File.temporaryFile(
            prefix = "index,usage",
            suffix = ".csv"
          )

        countryCodeWeightFile <-
          File.temporaryFile(
            prefix = "index,country-code,weight,file",
            suffix = ".csv"
          )

        writeEntries =
          DictionaryEntriesCsvWriter
            .using(
              logger = Logger.Null
            )

      } yield withFixture(test.toNoArgTest(Fixtures(
        templatesFile = templateFile,
        usageFile = usageFile,
        countryCodeWeightFile = countryCodeWeightFile,
        writeEntries = writeEntries
      )))

    outcomeF.get()
  }

  "Consolidate usages to duplicate templates" in { fixtures =>
    import fixtures.templatesFile
    import fixtures.usageFile
    import fixtures.countryCodeWeightFile
    import fixtures.writeEntries

    val partialEntry = DictionaryEntry(_, _, Nil)

    val entries = IndexedSeq(
      partialEntry(Usage.Female, Template("Alpha")),
      partialEntry(Usage.Female, Template("Bravo")),
      partialEntry(Usage.FirstFemale, Template("Bravo")),
      partialEntry(Usage.Female, Template("Charlie")),
      partialEntry(Usage.FirstFemale, Template("Charlie")),
      partialEntry(Usage.MostlyFemale, Template("Charlie")),
      partialEntry(Usage.Female, Template("Delta")),
      partialEntry(Usage.FirstFemale, Template("Delta")),
      partialEntry(Usage.MostlyFemale, Template("Delta")),
      partialEntry(Usage.Female, Template("Delta")),
      partialEntry(Usage.FirstFemale, Template("Delta")),
      partialEntry(Usage.MostlyFemale, Template("Delta")),
      partialEntry(Usage.Male, Template("Echo")),
      partialEntry(Usage.FirstMale, Template("Echo")),
      partialEntry(Usage.MostlyMale, Template("Echo")),
      partialEntry(Usage.Unisex, Template("Echo")),
      partialEntry(Usage.Equivalent, Template("Foxtrot"))
    )

    writeEntries(
      dictionaryEntries = entries,
      templatesFile = templatesFile.toJava,
      usageFile = usageFile.toJava,
      countryCodeWeightFile = countryCodeWeightFile.toJava
    )

    templatesFile
      .lines(StandardCharsets.UTF_8)
      .toSeq
      .should(matchTo(Resource.my
        .getAsStream("expected_name.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq))

    usageFile
      .lines(StandardCharsets.ISO_8859_1)
      .toSeq
      .should(matchTo(Resource.my
        .getAsStream("expected_index,usage.csv")
        .lines(StandardCharsets.UTF_8)
        .toSeq))
  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    templatesFile: File,
    usageFile: File,
    countryCodeWeightFile: File,
    writeEntries: DictionaryEntriesCsvWriter)

}
