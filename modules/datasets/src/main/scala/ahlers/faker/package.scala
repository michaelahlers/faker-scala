package ahlers

import ahlers.faker.refined.string._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.string._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
package object faker {

  type CompanyIdType = String Refined (NonEmpty And Trimmed)

  type CompanyNameType = String Refined (NonEmpty And Trimmed)

  type CompanyWebsiteType = String Refined (NonEmpty And Trimmed And Uri)

  /** @see [[https://emailregex.com]] */
  type EmailLocalType = String Refined MatchEmailLocal

  /** @see [[https://emailregex.com]] */
  type EmailDomainType = String Refined
    (MatchEmailDomainHost
      Or MatchEmailDomainIPv4
      Or MatchEmailDomainIPv6)

  type PersonNamePrefixType = String Refined (NonEmpty And Trimmed)

  type PersonGivenNameType = String Refined (NonEmpty And Trimmed)

  type PersonMiddleNameType = String Refined (NonEmpty And Trimmed)

  type PersonFamilyNameType = String Refined (NonEmpty And Trimmed)

  type PersonNicknameType = String Refined (NonEmpty And Trimmed)

  type PersonNameSuffixType = String Refined (NonEmpty And Trimmed)

}
