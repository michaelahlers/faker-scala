package ahlers.faker.datasets.census1990

import java.io.Closeable

import ahlers.faker._
import eu.timepit.refined.api.Refined

import scala.io.Source

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class GivenNamesMaleIterator extends Iterator[ClassifiedGivenName] with Closeable {

  private val source =
    Source.fromInputStream(
      Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream("www2.census.gov/topics/genealogy/1990surnames/dist.male.first"))

  final override def close(): Unit = source.close()

  private val lines = source.getLines()

  override def hasNext = lines.hasNext

  def next() = {
    import Gender._

    val row = lines.next()
    val name = row.slice(0, 15).trim()
    val rank = row.slice(29, 34).trim().toInt
    ClassifiedGivenName(
      Male,
      PersonGivenName(Refined.unsafeApply(name)),
      NameRank(Refined.unsafeApply(rank))
    )
  }

}

object GivenNamesMaleIterator {
  def apply(): GivenNamesMaleIterator = new GivenNamesMaleIterator
}
