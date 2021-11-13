package ahlers.faker.datasets.heise

import org.scalatest.Inside._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PersonGivenNamesSpec extends AnyWordSpec {

  "Only include literals" in {
    personGivenNames
      .all
      .foreach(inside(_) { case givenName =>
        givenName
          .toText.value
          .shouldNot(include("+"))
      })

  }

}
