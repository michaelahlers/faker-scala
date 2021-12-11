package ahlers.faker.scalacheck

import ahlers.faker._
import org.scalacheck._
import org.scalacheck.Gen._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 19, 2020
 */
object all {

  val genCompanyName: Gen[CompanyName] =
    opendata500.companies.names.genCompanyName

  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(census1990.genPersonGivenName, jörgMichael.genPersonGivenName)

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(census1990.genPersonFamilyName, census2000.genPersonFamilyName)

}
