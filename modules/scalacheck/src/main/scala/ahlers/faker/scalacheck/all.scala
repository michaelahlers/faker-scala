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

  val genCompanyWebsite: Gen[CompanyWebsite] =
    opendata500.companies.websites.genCompanyWebsite

  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(
      uscensus1990.persons.names.genPersonGivenName,
      jörgmichael.persons.names.genPersonGivenName)

  val genPersonFamilyName: Gen[PersonFamilyName] =
    oneOf(
      uscensus1990.persons.names.genPersonFamilyName,
      uscensus2000.persons.names.genPersonFamilyName)

}
