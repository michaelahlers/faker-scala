package ahlers.faker.scalacheck

import ahlers.faker._
import ahlers.faker.datasets.jörgmichael.persons._
import org.scalacheck.Gen._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object jörgMichael {

  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(personGivenNames.all)

}
