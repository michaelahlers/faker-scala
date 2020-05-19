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

  val genPersonGivenNameFemale: Gen[PersonGivenName] =
    oneOf(
      GivenNamesFemaleIterator()
        .map(_.name)
        .toIndexedSeq)

  val genPersonGivenNameMale: Gen[PersonGivenName] =
    oneOf(
      GivenNamesMaleIterator()
        .map(_.name)
        .toIndexedSeq)

  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(genPersonGivenNameFemale, genPersonGivenNameMale)

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(
      FamilyNamesIterator()
        .map(_.name)
        .toIndexedSeq)

}
