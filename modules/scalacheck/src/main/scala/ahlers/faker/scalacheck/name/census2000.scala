package ahlers.faker.scalacheck.name

import ahlers.faker.PersonFamilyName
import ahlers.faker.datasets.census2000.FamilyNamesIterator
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf

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
