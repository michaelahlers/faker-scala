package ahlers.faker.models

/**
 * Person name with components structured after [[https://developer.apple.com/documentation/foundation/personnamecomponents `PersonNameComponents` from Apple's Foundation Framework]].
 *
 * @see [[https://developer.apple.com/documentation/foundation/personnamecomponents]]
 *
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
case class PersonName(
  namePrefix: Option[PersonNamePrefix],
  givenName: Option[PersonGivenName],
  middleName: Option[PersonMiddleName],
  familyName: Option[PersonFamilyName],
  nameSuffix: Option[PersonNameSuffix],
  nickname: Option[PersonNickname])
