package opendata500

import ahlers.faker.samples._
import kantan.csv.DecodeError.TypeError
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 17, 2020
 */
object OpenData500Loader {

  def krCompanies(): IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("opendata500.com/kr/download/kr_companies.csv")
      .unsafeReadCsv[IndexedSeq, Company](rfc.withHeader)

  def usCompanies(): IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("opendata500.com/us/download/us_companies.csv")
      .unsafeReadCsv[IndexedSeq, Company](rfc.withHeader)

}
