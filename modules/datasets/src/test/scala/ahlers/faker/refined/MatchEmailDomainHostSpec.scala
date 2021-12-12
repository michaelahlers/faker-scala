package ahlers.faker.refined

import string._
import com.softwaremill.diffx.scalatest.DiffShouldMatcher._
import eu.timepit.refined.api._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
class MatchEmailDomainHostSpec extends AnyWordSpec {

  "isValid" in {
    val isValid = Validate[String, MatchEmailDomainHost].isValid(_)

    isValid("example.com").should(be(true))
    // isValid("(comment)example.com").should(be(true))
    // isValid("example.com(comment)").should(be(true))
  }

  "notValid" in {
    val notValid = Validate[String, MatchEmailDomainHost].notValid(_)

    notValid("").should(be(true))
  }

  "showExpr" in {
    val showExpr = Validate[String, MatchEmailDomainHost].showExpr(_)
    showExpr("example.com").shouldMatchTo("example.com is a valid email domain host")
  }

}
