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
class GivenNamesMaleIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[ClassifiedGivenName]
  override protected def withFixture(test: OneArgTest) = {
    val loader = GivenNamesMaleIterator()
    try withFixture(test.toNoArgTest(loader.toIndexedSeq))
    finally loader.close()
  }

  "Given names" in { givenNames =>
    import Gender._
    givenNames.size.should(be(1219))
    givenNames(0).should(matchTo(ClassifiedGivenName(Male, PersonGivenName("JAMES"), NameRank(1))))
    givenNames(1218).should(matchTo(ClassifiedGivenName(Male, PersonGivenName("ALONSO"), NameRank(1219))))
  }

}
