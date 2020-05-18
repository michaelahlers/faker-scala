package ahlers.faker.models

import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 06, 2020
 */
class EmailDomainSpec extends AnyWordSpec {

  "constructor" in {

    """EmailDomain("example.com")""".should(compile)
    """EmailDomain("[192.168.1.1]")""".should(compile)
    """EmailDomain("[::1:1]")""".should(compile)

    """EmailDomain("")""".shouldNot(compile)
    """EmailDomain("[192.168.1.1")""".shouldNot(compile)
    """EmailDomain("[::1:1")""".shouldNot(compile)

  }

}
