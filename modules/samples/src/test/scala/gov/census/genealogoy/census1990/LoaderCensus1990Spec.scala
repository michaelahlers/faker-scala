package gov.census.genealogoy.census1990

import ahlers.faker.samples._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class LoaderCensus1990Spec extends AnyWordSpec {

  "Family names" in {
    val familyNames = LoaderCensus1990.familyNames()
    familyNames.size.should(be(88799))
    familyNames(0).should(matchTo(ClassifiedFamilyName(PersonFamilyName("SMITH"), NameRank(1))))
    familyNames(88798).should(matchTo(ClassifiedFamilyName(PersonFamilyName("AALDERINK"), NameRank(88799))))
  }

}
