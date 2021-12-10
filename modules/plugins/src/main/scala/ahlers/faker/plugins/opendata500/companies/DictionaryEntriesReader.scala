package ahlers.faker.plugins.opendata500.companies

import kantan.csv._
import kantan.csv.ops._
import sbt._

import java.io.FileInputStream
import java.io.InputStream

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesReader {

  def apply(entriesStream: InputStream): Seq[DictionaryEntry]

  final def apply(entriesFile: File): Seq[DictionaryEntry] = {
    val entriesStream = new FileInputStream(entriesFile)

    try apply(
      entriesStream = entriesStream
    )
    finally entriesStream.close()
  }

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
  implicit private[opendata500] val CellDecoderCompanyWebsites: CellDecoder[Option[CompanyWebsite]] =
    CellDecoder[String]
      .map(_.trim)
      .map(Option(_)
        .filter(value =>
          value.nonEmpty ||
            value.equalsIgnoreCase("http://H^8UDCC3>F8.6{.kr/"))
        .map(CompanyWebsite(_)))

  implicit private val HeaderDecoderDictionaryEntry: HeaderDecoder[DictionaryEntry] =
    HeaderDecoder.decoder("company_name_id", "company_name", "url")(DictionaryEntry.apply)

  def using(
    logger: Logger
  ): DictionaryEntriesReader = _
    .asUnsafeCsvReader[DictionaryEntry](rfc.withHeader)
    .toIndexedSeq

}
