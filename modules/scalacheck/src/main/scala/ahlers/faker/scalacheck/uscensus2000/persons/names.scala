package ahlers.faker.scalacheck.uscensus2000.persons

import ahlers.faker._
import ahlers.faker.datasets.uscensus2000.persons._
import org.scalacheck.Gen._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object names {

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(personFamilyNames.all)

}
