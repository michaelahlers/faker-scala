package ahlers.faker.datasets.census.census1990

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
class GivenNamesMaleLoaderSpec extends AnyWordSpec with BeforeAndAfterAll {

  val loader = GivenNamesMaleLoader()

  override def afterAll(): Unit = {
    super.afterAll()
    loader.close()
  }

  "Given names" in {
    import Gender._
    val givenNames = loader.givenNames().toIndexedSeq
    givenNames.size.should(be(1219))
    givenNames(0).should(matchTo(ClassifiedGivenName(Male, PersonGivenName("JAMES"), NameRank(1))))
    givenNames(1218).should(matchTo(ClassifiedGivenName(Male, PersonGivenName("ALONSO"), NameRank(1219))))
  }

}
