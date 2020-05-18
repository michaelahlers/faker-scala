package ahlers.faker.datasets.census.census1990

import java.io.Closeable

import ahlers.faker.models._
import eu.timepit.refined.api.Refined

import scala.io.Source

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class GivenNamesMaleLoader extends Closeable {

  private val source =
    Source.fromInputStream(
      Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream("www2.census.gov/topics/genealogy/1990surnames/dist.male.first"))

  def givenNames(): Iterator[ClassifiedGivenName] = {
    import Gender._

    source
      .getLines()
      .map { row =>
        val name = row.slice(0, 15).trim()
        val rank = row.slice(29, 34).trim().toInt
        ClassifiedGivenName(
          Male,
          PersonGivenName(Refined.unsafeApply(name)),
          NameRank(Refined.unsafeApply(rank))
        )
      }
  }

  override def close(): Unit = source.close()

}

object GivenNamesMaleLoader {
  def apply(): GivenNamesMaleLoader = new GivenNamesMaleLoader
}
