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
    val fixturesF =
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

        writer =
          DictionaryEntriesCsvWriter
            .using(
              templatesFile = templateFile.toJava,
              usageFile = usageFile.toJava,
              countryCodeWeightFile = countryCodeWeightFile.toJava,
              logger = Logger.Null
            )

      } yield {
        info(s"""Template file: "$templateFile"; usage file: "$usageFile"; country-code, weight file: "$countryCodeWeightFile".""")

        Fixtures(
          templatesFile = templateFile,
          usageFile = usageFile,
          countryCodeWeightFile = countryCodeWeightFile,
          writeEntries = writer
        )
      }

    fixturesF
      .map(test.toNoArgTest(_))
      .apply(withFixture(_))
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

    writeEntries(entries)
      .should(matchTo(Seq(
        templatesFile.toJava,
        usageFile.toJava,
        countryCodeWeightFile.toJava
      )))

    templatesFile
      .lines(StandardCharsets.ISO_8859_1)
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
      .lines(StandardCharsets.ISO_8859_1)
      .toSeq
      .should(matchTo(Seq(
        "0,F",
        "1,F",
        "1,1F",
        "2,F",
        "2,1F",
        "2,?F",
        "3,F",
        "3,1F",
        "3,?F",
        "4,M",
        "4,1M",
        "4,?M",
        "4,?",
        "5,="
      )))
  }

}

object DictionaryEntriesCsvWriterSpec {

  case class Fixtures(
    templatesFile: File,
    usageFile: File,
    countryCodeWeightFile: File,
    writeEntries: DictionaryEntriesWriter)

}
