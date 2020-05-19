package ahlers.faker.scalacheck

import ahlers.faker._
import ahlers.faker.datasets.census2000._
import org.scalacheck.Gen._
import org.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object census2000 {

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(
      FamilyNamesIterator()
        .map(_.name)
        .toIndexedSeq)

}
