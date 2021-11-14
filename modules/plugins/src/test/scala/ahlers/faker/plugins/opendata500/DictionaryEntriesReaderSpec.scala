package ahlers.faker.plugins.opendata500

import better.files._
import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.FixtureAnyWordSpec
import sbt.Logger

import java.nio.charset.StandardCharsets

/**
 * @since November 14, 2021
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
            .using()

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
      """company_name_id,company_name,url,year_founded,city,state,country,zip_code,full_time_employees,company_type,company_category,revenue_source,business_model,social_impact,description,description_short,source_count,data_types,example_uses,data_impacts,financial_info,last_updated""",
      """alpha,"Alpha, Inc.",http://alpha.com,0,City,State,County,00000,0,Type,Category,Revenue Source,Business Model,Social Impact,Description (long),Description (short),Count,Types,Usages,[],Financial Info.,1970-01-01 00:00:00.000000""",
      """bravo,"Bravo, LLC",http://bravo.com,1,City,State,County,00001,1,Type,Category,Revenue Source,Business Model,Social Impact,Description (long),Description (short),Count,Types,Usages,[u'Bravo'],Financial Info.,1970-01-01 00:00:01.000000"""
    )

    readEntries(
      sourceFile = sourceFile.toJava)
      .should(matchTo(Seq(
        DictionaryEntry(CompanyId("alpha"), CompanyName("Alpha, Inc."), CompanyWebsite("http://alpha.com")),
        DictionaryEntry(CompanyId("bravo"), CompanyName("Bravo, LLC"), CompanyWebsite("http://bravo.com"))
      )))
  }

}

object DictionaryEntriesReaderSpec {
  case class Fixtures(
    sourceFile: File,
    readEntries: DictionaryEntriesReader)

}
