package ahlers.faker

import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @todo Known to fail with Scala 3; restore for all versions if [[https://github.com/fthomas/refined/issues/932]] is resolved.
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
