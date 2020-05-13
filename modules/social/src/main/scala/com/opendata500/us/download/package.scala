package com.opendata500.us

import ahlers.faker.social._
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
package object download {

  implicit private val CellDecoderCompanyId: CellDecoder[CompanyId] = CompanyId.deriving
  implicit private val CellDecoderCompanyName: CellDecoder[CompanyName] = CompanyName.deriving
  implicit private val CellDecoderCompanyWebsite: CellDecoder[Seq[CompanyWebsite]] = CompanyWebsite.deriving[CellDecoder].map(Seq(_))

  val usCompanies: IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("com/opendata500/us/download/us_companies.csv")
      .asCsvReader[Company](rfc.withHeader)
      .toIndexedSeq
      .flatMap(_.toOption)

}
