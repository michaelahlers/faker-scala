package ahlers.faker.datasets.census1990

import ahlers.faker.models._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
class GivenNamesFemaleIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[ClassifiedGivenName]
  override protected def withFixture(test: OneArgTest) = {
    val loader = GivenNamesFemaleIterator()
    try withFixture(test.toNoArgTest(loader.toIndexedSeq))
    finally loader.close()
  }

  "Given names (female)" in { givenNames =>
    import Gender._
    givenNames.size.should(be(4275))
    givenNames(0).should(matchTo(ClassifiedGivenName(Female, PersonGivenName("MARY"), NameRank(1))))
    givenNames(4274).should(matchTo(ClassifiedGivenName(Female, PersonGivenName("ALLYN"), NameRank(4275))))
  }

}
