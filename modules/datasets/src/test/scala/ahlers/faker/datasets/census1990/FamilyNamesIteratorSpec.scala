package ahlers.faker.datasets.census1990

import ahlers.faker._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class FamilyNamesIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[ClassifiedFamilyName]
  override protected def withFixture(test: OneArgTest) = {
    val loader = FamilyNamesIterator()
    try withFixture(test.toNoArgTest(loader.toIndexedSeq))
    finally loader.close()
  }

  "Family names" in { familyNames =>
    familyNames.size.should(be(88799))
    familyNames(0).should(matchTo(ClassifiedFamilyName(PersonFamilyName("SMITH"), NameRank(1))))
    familyNames(88798).should(matchTo(ClassifiedFamilyName(PersonFamilyName("AALDERINK"), NameRank(88799))))
  }

}
