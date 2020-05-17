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

  "Given names (female)" in {
    import Gender._
    val familyNames = LoaderCensus1990.givenNamesFemale()
    familyNames.size.should(be(4275))
    familyNames(0).should(matchTo(ClassifiedGivenName(Female, PersonGivenName("MARY"), NameRank(1))))
    familyNames(4274).should(matchTo(ClassifiedGivenName(Female, PersonGivenName("ALLYN"), NameRank(4275))))
  }

  "Given names (male)" in {
    import Gender._
    val familyNames = LoaderCensus1990.givenNamesMale()
    familyNames.size.should(be(1219))
    familyNames(0).should(matchTo(ClassifiedGivenName(Male, PersonGivenName("JAMES"), NameRank(1))))
    familyNames(1218).should(matchTo(ClassifiedGivenName(Male, PersonGivenName("ALONSO"), NameRank(1219))))
  }

  "Family names" in {
    val familyNames = LoaderCensus1990.familyNames()
    familyNames.size.should(be(88799))
    familyNames(0).should(matchTo(ClassifiedFamilyName(PersonFamilyName("SMITH"), NameRank(1))))
    familyNames(88798).should(matchTo(ClassifiedFamilyName(PersonFamilyName("AALDERINK"), NameRank(88799))))
  }

}
