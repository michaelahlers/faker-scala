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
class ClassifiedFamilyNamesSpec extends AnyWordSpec {

  "Read" in {
    val classifiedFamilyNames = ClassifiedFamilyNames.read()
    classifiedFamilyNames.size.should(be(88799))
    classifiedFamilyNames(0).should(matchTo(ClassifiedFamilyName(PersonFamilyName("SMITH"), NameRank(1))))
    classifiedFamilyNames(88798).should(matchTo(ClassifiedFamilyName(PersonFamilyName("AALDERINK"), NameRank(88799))))
  }

}
