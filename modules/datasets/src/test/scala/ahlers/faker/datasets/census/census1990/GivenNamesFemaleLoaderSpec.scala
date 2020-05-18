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
class GivenNamesFemaleLoaderSpec extends AnyWordSpec with BeforeAndAfterAll {

  val loader = GivenNamesFemaleLoader()

  override def afterAll(): Unit = {
    super.afterAll()
    loader.close()
  }

  "Given names (female)" in {
    import Gender._
    val givenNames = loader.toIndexedSeq
    givenNames.size.should(be(4275))
    givenNames(0).should(matchTo(ClassifiedGivenName(Female, PersonGivenName("MARY"), NameRank(1))))
    givenNames(4274).should(matchTo(ClassifiedGivenName(Female, PersonGivenName("ALLYN"), NameRank(4275))))
  }

}
