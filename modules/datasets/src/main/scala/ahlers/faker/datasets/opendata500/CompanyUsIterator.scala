package ahlers.faker.datasets.opendata500

import java.io.Closeable
import ahlers.faker.Company
import ahlers.faker._
import kantan.csv.DecodeError.TypeError
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 17, 2020
 */
class CompanyUsIterator extends Iterator[Company] with Closeable {

  private val reader =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("www.opendata500.com/us/download/us_companies.csv")
      .asUnsafeCsvReader[Company](rfc.withHeader)

  override def close(): Unit = reader.close()

  override def hasNext = reader.hasNext
  override def next() = reader.next()

}

object CompanyUsIterator {
  def apply(): CompanyUsIterator = new CompanyUsIterator
}
