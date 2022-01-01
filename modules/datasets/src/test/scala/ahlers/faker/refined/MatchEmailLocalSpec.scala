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

    assert(isValid("jane"))
    assert(isValid("jane+a"))
    assert(isValid("jane.smith"))
    assert(isValid("jane.smith+a"))
    assert(isValid("jane.smith+a+b"))

    assert(isValid("jane-doe"))
    assert(isValid("jane-doe+a"))
    assert(isValid("jane-doe+a+b"))

    assert(isValid("host!user"))
    assert(isValid("user%host"))
  }

  "notValid" in {
    val notValid = Validate[String, MatchEmailLocal].notValid(_)

    assert(notValid(""))
    assert(notValid("jane@doe"))
    assert(notValid("""a"b(c)d,e:f;g<h>i[j\k]l"""))
    assert(notValid("""jane"q"doe"""))
  }

  "showExpr" in {
    val showExpr = Validate[String, MatchEmailLocal].showExpr(_)
    showExpr("jane.smith").shouldMatchTo("jane.smith is a valid email local part")
  }

}
