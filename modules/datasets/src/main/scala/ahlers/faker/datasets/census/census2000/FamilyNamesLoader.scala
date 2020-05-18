package ahlers.faker.datasets.census.census2000

import java.io.InputStream
import java.util.zip.ZipInputStream

import ahlers.faker.models._
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
class FamilyNamesLoader {

  private val source: InputStream =
    new ZipInputStream(
      Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream("www2.census.gov/topics/genealogy/2000surnames/names.zip")) {
      while (!(getNextEntry().getName() == "app_c.csv")) {}
    }

  def familyNames(): Iterator[ClassifiedName] =
    source.unsafeReadCsv[Iterator, ClassifiedName](rfc.withHeader)

  def close(): Unit = source.close()

}

object FamilyNamesLoader {
  def apply(): FamilyNamesLoader = new FamilyNamesLoader
}
