package ahlers.faker.scalacheck.name

import ahlers.faker.datasets.census1990.FamilyNamesIterator
import ahlers.faker.datasets.census1990.GivenNamesFemaleIterator
import ahlers.faker.datasets.census1990.GivenNamesMaleIterator
import ahlers.faker.PersonFamilyName
import ahlers.faker.PersonGivenName
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf

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
