package ahlers.faker.models

import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 06, 2020
 */
class EmailLocalSpec extends AnyWordSpec {

  "constructor" in {

    """EmailLocal("jane.smith")""".should(compile)
    """EmailLocal("jane.smith+tag")""".should(compile)

    """EmailLocal("")""".shouldNot(compile)
    """EmailLocal("jane..smith")""".shouldNot(compile)

  }

}
