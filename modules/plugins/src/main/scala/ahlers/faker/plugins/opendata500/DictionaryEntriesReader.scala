package ahlers.faker.plugins.opendata500

import kantan.csv._
import sbt._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

import java.io.InputStream

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesReader {
  def apply(entriesSource: InputStream): Seq[DictionaryEntry]
}

object DictionaryEntriesReader {

  implicit private[opendata500] val CellDecoderCompanyId: CellDecoder[CompanyId] =
    CellDecoder[String]
      .map(_.trim)
      .map(CompanyId(_))

  implicit private[opendata500] val CellDecoderCompanyName: CellDecoder[CompanyName] =
    CellDecoder[String]
      .map(_.trim)
      .map(CompanyName(_))

  /**
   * Excludes values known to be invalid.
   * @todo Restore exclusions.
   */
  implicit private[opendata500] val CellDecoderCompanyWebsites: CellDecoder[CompanyWebsite] =
    CellDecoder[String].map(CompanyWebsite(_))
  //.recover {
  //  case TypeError(message)
  //      if message.contains("Predicate isEmpty() did not fail") ||
  //        message.contains("http://H^8UDCC3>F8.6{.kr/") =>
  //    Seq()
  //}

  def using(
    logger: Logger
  ): DictionaryEntriesReader = _
    .asUnsafeCsvReader[DictionaryEntry](rfc.withHeader)
    .seq

}
