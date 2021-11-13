package ahlers.faker.scalacheck

import ahlers.faker._
import ahlers.faker.datasets.census1990._
import org.scalacheck.Gen._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object census1990 {

  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(personGivenNames.all)

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(personFamilyNames.all)

}
