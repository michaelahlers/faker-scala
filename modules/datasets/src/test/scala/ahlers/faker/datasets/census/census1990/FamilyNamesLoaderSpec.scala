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
class FamilyNamesLoaderSpec extends AnyWordSpec with BeforeAndAfterAll {

  val loader = FamilyNamesLoader()

  override def afterAll(): Unit = {
    super.afterAll()
    loader.close()
  }

  "Family names" in {
    val familyNames = loader.toIndexedSeq
    familyNames.size.should(be(88799))
    familyNames(0).should(matchTo(ClassifiedFamilyName(PersonFamilyName("SMITH"), NameRank(1))))
    familyNames(88798).should(matchTo(ClassifiedFamilyName(PersonFamilyName("AALDERINK"), NameRank(88799))))
  }

}
