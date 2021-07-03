package ahlers.faker.scalacheck

import ahlers.faker.PersonFamilyName
import ahlers.faker.PersonGivenName
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf

package object name {
  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(census1990.genPersonGivenName, heise.genPersonGivenName)

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(census1990.genPersonFamilyName, census2000.genPersonFamilyName)
}
