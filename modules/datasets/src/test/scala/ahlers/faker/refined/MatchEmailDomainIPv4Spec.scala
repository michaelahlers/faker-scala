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
class MatchEmailDomainIPv4Spec extends AnyWordSpec {

  "isValid" in {
    val isValid = Validate[String, MatchEmailDomainIPv4].isValid(_)

    isValid("[192.168.1.1]").should(be(true))
  }

  "notValid" in {
    val notValid = Validate[String, MatchEmailDomainIPv4].notValid(_)

    notValid("192.168.1.1").should(be(true))
    notValid("[192.168.1.1").should(be(true))
    notValid("192.168.1.1]").should(be(true))

    notValid("[192.168.1]").should(be(true))
    notValid("[192.168.1.]").should(be(true))
  }

  "showExpr" in {
    val showExpr = Validate[String, MatchEmailDomainIPv4].showExpr(_)
    showExpr("[192.168.1.1]").shouldMatchTo("[192.168.1.1] is a valid email domain IPv4 address")
  }

}
