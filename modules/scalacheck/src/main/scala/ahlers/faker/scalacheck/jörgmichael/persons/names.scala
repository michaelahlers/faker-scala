package ahlers.faker.scalacheck.jörgmichael.persons

import ahlers.faker._
import ahlers.faker.datasets.jörgmichael.persons._
import org.scalacheck.Gen._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since December 11, 2021
 */
object names {
  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(personGivenNames.all)
}
