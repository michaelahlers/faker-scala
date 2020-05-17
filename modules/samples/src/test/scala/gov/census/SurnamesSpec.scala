package gov.census

import ahlers.faker.samples._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class SurnamesSpec extends AnyWordSpec {

  "Frequently Occurring Surnames from the Census 2000" in {
    classifiedNames.size.should(be(151671))
    classifiedNames(0).should(matchTo(ClassifiedName(PersonFamilyName("SMITH"), NameRank(1), NameCount(2376206))))
    classifiedNames(151670).should(matchTo(ClassifiedName(PersonFamilyName("ZUSI"), NameRank(150436), NameCount(100))))
  }
}
