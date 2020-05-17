package gov.census.genealogoy.census1990

import ahlers.faker.samples._
import eu.timepit.refined.api.Refined

import scala.io.Source

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
object LoaderCensus1990 {

  def givenNamesFemale(): IndexedSeq[ClassifiedGivenName] = {
    import Gender._

    val source =
      Source.fromInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("www2.census.gov/topics/genealogy/1990surnames/dist.female.first"))

    try source
      .getLines()
      .map { row =>
        val name = row.slice(0, 15).trim()
        val rank = row.slice(29, 34).trim().toInt
        ClassifiedGivenName(
          Female,
          PersonGivenName(Refined.unsafeApply(name)),
          NameRank(Refined.unsafeApply(rank))
        )
      }
      .toIndexedSeq
    finally source.close()
  }

  def givenNamesMale(): IndexedSeq[ClassifiedGivenName] = {
    import Gender._

    val source =
      Source.fromInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("www2.census.gov/topics/genealogy/1990surnames/dist.male.first"))

    try source
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
      .toIndexedSeq
    finally source.close()
  }

  def familyNames(): IndexedSeq[ClassifiedFamilyName] = {
    val source =
      Source.fromInputStream(
        Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("www2.census.gov/topics/genealogy/1990surnames/dist.all.last"))

    try source
      .getLines()
      .map { row =>
        val name = row.slice(0, 15).trim()
        val rank = row.slice(29, 34).trim().toInt
        ClassifiedFamilyName(
          PersonFamilyName(Refined.unsafeApply(name)),
          NameRank(Refined.unsafeApply(rank))
        )
      }
      .toIndexedSeq
    finally source.close()
  }

}
