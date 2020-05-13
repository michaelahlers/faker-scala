package com.opendata500

import _root_.kantan.csv._
import _root_.kantan.csv.refined._
import ahlers.faker.social._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
object kantan {

  implicit private[opendata500] val CellDecoderCompanyId: CellDecoder[CompanyId] = CompanyId.deriving
  implicit private[opendata500] val CellDecoderCompanyName: CellDecoder[CompanyName] = CompanyName.deriving
  implicit private[opendata500] val CellDecoderCompanyWebsite: CellDecoder[Seq[CompanyWebsite]] = CompanyWebsite.deriving[CellDecoder].map(Seq(_))

}
