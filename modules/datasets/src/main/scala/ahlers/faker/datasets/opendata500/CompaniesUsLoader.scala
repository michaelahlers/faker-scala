package ahlers.faker.datasets.opendata500

import ahlers.faker.models._
import kantan.csv.DecodeError.TypeError
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 17, 2020
 */
class CompaniesUsLoader {

  private val source =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("opendata500.com/us/download/us_companies.csv")

  def companies(): Iterator[Company] =
    source.unsafeReadCsv[Iterator, Company](rfc.withHeader)

  def close(): Unit = source.close()

}

object CompaniesUsLoader {
  def apply(): CompaniesUsLoader = new CompaniesUsLoader
}
