package ahlers.faker.datasets.census.census2000

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
    familyNames.size.should(be(151671))
    familyNames(0).should(matchTo(ClassifiedName(PersonFamilyName("SMITH"), NameRank(1), NameCount(2376206))))
    familyNames(151670).should(matchTo(ClassifiedName(PersonFamilyName("ZUSI"), NameRank(150436), NameCount(100))))
  }
}
