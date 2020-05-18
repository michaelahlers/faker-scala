package ahlers.faker.datasets

import ahlers.faker.models._
import kantan.csv.DecodeError.TypeError
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
package object opendata500 {

  implicit private[opendata500] val CellDecoderCompanyId: CellDecoder[CompanyId] = CompanyId.deriving

  implicit private[opendata500] val CellDecoderCompanyName: CellDecoder[CompanyName] =
    CompanyName.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())

  /** Excludes values known to be invalid. */
  implicit private[opendata500] val CellDecoderCompanyWebsites: CellDecoder[Seq[CompanyWebsite]] =
    CompanyWebsite.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())
      .map(Seq(_))
      .recover {
        case TypeError(message)
            if message.contains("Predicate isEmpty() did not fail") ||
              message.contains("http://H^8UDCC3>F8.6{.kr/") =>
          Seq()
      }

}
