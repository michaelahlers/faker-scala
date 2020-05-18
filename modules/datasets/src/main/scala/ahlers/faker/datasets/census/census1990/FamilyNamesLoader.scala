package ahlers.faker.datasets.census.census1990

import java.io.Closeable

import ahlers.faker.models._
import eu.timepit.refined.api.Refined

import scala.io.Source

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class FamilyNamesLoader extends Iterator[ClassifiedFamilyName] with Closeable {

  private val source =
    Source.fromInputStream(
      Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream("www2.census.gov/topics/genealogy/1990surnames/dist.all.last"))

  final override def close(): Unit = source.close()

  private val lines = source.getLines()

  override def hasNext = lines.hasNext

  override def next() = {
    val row = lines.next()
    val name = row.slice(0, 15).trim()
    val rank = row.slice(29, 34).trim().toInt
    ClassifiedFamilyName(
      PersonFamilyName(Refined.unsafeApply(name)),
      NameRank(Refined.unsafeApply(rank))
    )
  }

}

object FamilyNamesLoader {
  def apply(): FamilyNamesLoader = new FamilyNamesLoader
}
