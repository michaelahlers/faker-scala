package gov.census.genealogoy.census2000

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
 * @since May 17, 2020
 */
object LoaderCensus2000 {

  def familyNames(): IndexedSeq[ClassifiedName] = {
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
