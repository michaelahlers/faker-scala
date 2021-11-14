package ahlers.faker.plugins.opendata500

import sbt._

import java.nio.charset.StandardCharsets
import kantan.csv.DecodeError.TypeError
import kantan.csv.CellDecoder
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesReader {
  def apply(sourceFile: File): Seq[DictionaryEntry]
}

object DictionaryEntriesReader {

  implicit private[opendata500] val CellDecoderCompanyId: CellDecoder[CompanyId] =
    CellDecoder[String].map(CompanyId(_))

  implicit private[opendata500] val CellDecoderCompanyName: CellDecoder[CompanyName] =
    CellDecoder[String].map(CompanyName(_))

  /** Excludes values known to be invalid. */
  implicit private[opendata500] val CellDecoderCompanyWebsites: CellDecoder[CompanyWebsite] =
    CellDecoder[String].map(CompanyWebsite(_))
  //.recover {
  //  case TypeError(message)
  //      if message.contains("Predicate isEmpty() did not fail") ||
  //        message.contains("http://H^8UDCC3>F8.6{.kr/") =>
  //    Seq()
  //}

  def using(
  ): DictionaryEntriesReader = { sourceFile =>
    sourceFile
      .asUnsafeCsvReader[DictionaryEntry](rfc.withHeader)
      .seq
  }

}
