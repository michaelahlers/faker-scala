package com.opendata500.us

import ahlers.faker.social.CompanyName
import ahlers.faker.social.CompanyHomepage
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.string._
import io.estatico.newtype.macros.newtype
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
package object download {

  type CompanyIdType = String Refined (NonEmpty And Trimmed)
  @newtype case class CompanyId(toText: CompanyIdType)

  implicit val CellDecoderCompanyId: CellDecoder[CompanyId] = CompanyId.deriving
  implicit val CellDecoderCompanyName: CellDecoder[CompanyName] = CompanyName.deriving
  implicit val CellDecoderCompanyHomepage: CellDecoder[CompanyHomepage] = CompanyHomepage.deriving

}
