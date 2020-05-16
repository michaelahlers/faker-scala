package gov

import java.io.InputStream
import java.util.zip.ZipInputStream

import ahlers.faker.samples._
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

  implicit private val CellDecoderPersonFamilyName: CellDecoder[PersonFamilyName] =
    PersonFamilyName.deriving[CellDecoder]
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
