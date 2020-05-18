package ahlers.faker.refined

import string._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.api._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
class MatchEmailDomainIPv6Spec extends AnyWordSpec {

  "isValid" in {
    val isValid = Validate[String, MatchEmailDomainIPv6].isValid(_)

    isValid("[::1]").should(be(true))
  }

  "notValid" in {
    val notValid = Validate[String, MatchEmailDomainIPv6].notValid(_)

    notValid("::1").should(be(true))
    notValid("[::1").should(be(true))
    notValid("::1]").should(be(true))

    notValid("[1:]").should(be(true))
    notValid("[1:2]").should(be(true))
  }

  "showExpr" in {
    val showExpr = Validate[String, MatchEmailDomainIPv6].showExpr(_)
    showExpr("[::1]").should(matchTo("[::1] is a valid email domain IPv6 address"))
  }

}
