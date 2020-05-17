package gov

import java.io.InputStream
import java.util.zip.ZipInputStream

import ahlers.faker.samples._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.numeric._
import eu.timepit.refined.string._
import io.estatico.newtype.macros.newtype
import kantan.csv.DecodeError.TypeError
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
package object census {

  type NameCountType = Int Refined Positive
  @newtype case class NameCount(toIht: NameCountType)

  type NameRankType = Int Refined Positive
  @newtype case class NameRank(toIht: NameRankType)

  implicit private val CellDecoderPersonFamilyName: CellDecoder[PersonFamilyName] =
    PersonFamilyName.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())

  implicit private val CellDecoderNameCount: CellDecoder[NameCount] =
    NameCount.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())

  implicit private val CellDecoderNameRank: CellDecoder[NameRank] =
    NameRank.deriving[CellDecoder]
      .contramapEncoded[String](_.trim())

  val classifiedNames: IndexedSeq[ClassifiedName] = {
    val source: InputStream =
      new ZipInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("www2.census.gov/topics/genealogy/2000surnames/names.zip")) {
        while (!(getNextEntry().getName() == "app_c.csv")) {}
      }

    try source.unsafeReadCsv[IndexedSeq, ClassifiedName](rfc.withHeader)
    finally source.close()
  }
}
