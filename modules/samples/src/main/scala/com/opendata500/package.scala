package com

import ahlers.faker.samples._
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

  implicit private val CellDecoderCompanyId: CellDecoder[CompanyId] = CompanyId.deriving

  implicit private val CellDecoderCompanyName: CellDecoder[CompanyName] =
    CompanyName.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())

  /** Excludes values known to be invalid. */
  implicit private val CellDecoderCompanyWebsites: CellDecoder[Seq[CompanyWebsite]] =
    CompanyWebsite.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())
      .map(Seq(_))
      .recover {
        case TypeError(message)
            if message.contains("Predicate isEmpty() did not fail") ||
              message.contains("http://H^8UDCC3>F8.6{.kr/") =>
          Seq()
      }

  val krCompanies: IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("com/opendata500/kr/download/kr_companies.csv")
      .unsafeReadCsv[IndexedSeq, Company](rfc.withHeader)

  val usCompanies: IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("com/opendata500/us/download/us_companies.csv")
      .unsafeReadCsv[IndexedSeq, Company](rfc.withHeader)

}
