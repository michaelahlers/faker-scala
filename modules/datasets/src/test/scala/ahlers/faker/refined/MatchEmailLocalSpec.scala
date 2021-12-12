package ahlers.faker.refined

import string._
import org.scalatestplus.scalacheck._
import com.softwaremill.diffx.scalatest.DiffShouldMatcher._
import eu.timepit.refined.api._
import org.scalatest._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import Inside._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
class MatchEmailLocalSpec extends AnyWordSpec {

  "isValid" in {
    val isValid = Validate[String, MatchEmailLocal].isValid(_)

    isValid("jane").should(be(true))
    isValid("jane+a").should(be(true))
    isValid("jane.smith").should(be(true))
    isValid("jane.smith+a").should(be(true))
    isValid("jane.smith+a+b").should(be(true))

    isValid("jane-doe").should(be(true))
    isValid("jane-doe+a").should(be(true))
    isValid("jane-doe+a+b").should(be(true))

    isValid("host!user").should(be(true))
    isValid("user%host").should(be(true))
  }

  "notValid" in {
    val notValid = Validate[String, MatchEmailLocal].notValid(_)

    notValid("").should(be(true))
    notValid("jane@doe").should(be(true))
    notValid("""a"b(c)d,e:f;g<h>i[j\k]l""").should(be(true))
    notValid("""jane"q"doe""").should(be(true))
  }

  "showExpr" in {
    val showExpr = Validate[String, MatchEmailLocal].showExpr(_)
    showExpr("jane.smith").shouldMatchTo("jane.smith is a valid email local part")
  }

}
