package ahlers.faker.datasets.census2000

import ahlers.faker._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class FamilyNamesIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[ClassifiedName]
  override protected def withFixture(test: OneArgTest) = {
    val loader = FamilyNamesIterator()
    try withFixture(test.toNoArgTest(loader.toIndexedSeq))
    finally loader.close()
  }

  "Family names" in { familyNames =>
    familyNames.size.should(be(151671))
    familyNames(0).should(matchTo(ClassifiedName(PersonFamilyName("SMITH"), NameRank(1), NameCount(2376206))))
    familyNames(151670).should(matchTo(ClassifiedName(PersonFamilyName("ZUSI"), NameRank(150436), NameCount(100))))
  }
}
