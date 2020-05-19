package ahlers

import ahlers.faker.refined.string._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.string._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
package object faker {

  type CompanyIdType = String Refined (NonEmpty And Trimmed)
  @newtype case class CompanyId(toText: CompanyIdType)

  type CompanyNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class CompanyName(toText: CompanyNameType)

  type CompanyWebsiteType = String Refined (NonEmpty And Trimmed And Uri)
  @newtype case class CompanyWebsite(toText: CompanyWebsiteType)

  /** @see [[https://emailregex.com]] */
  type EmailLocalType = String Refined MatchEmailLocal

  /** @see [[https://en.wikipedia.org/wiki/Email_address#Local-part]] */
  @newtype case class EmailLocal(toText: EmailLocalType)

  /** @see [[https://emailregex.com]] */
  type EmailDomainType = String Refined
    (MatchEmailDomainHost
      Or MatchEmailDomainIPv4
      Or MatchEmailDomainIPv6)

  /** @see [[https://en.wikipedia.org/wiki/Email_address#Domain]] */
  @newtype case class EmailDomain(toText: EmailDomainType)

  type PersonNamePrefixType = String Refined (NonEmpty And Trimmed)
  @newtype case class PersonNamePrefix(toText: PersonNamePrefixType)

  type PersonGivenNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class PersonGivenName(toText: PersonGivenNameType)

  type PersonMiddleNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class PersonMiddleName(toText: PersonMiddleNameType)

  type PersonFamilyNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class PersonFamilyName(toText: PersonFamilyNameType)

  type PersonNicknameType = String Refined (NonEmpty And Trimmed)
  @newtype case class PersonNickname(toText: PersonNicknameType)

  type PersonNameSuffixType = String Refined (NonEmpty And Trimmed)
  @newtype case class PersonNameSuffix(toText: PersonNameSuffixType)

}
