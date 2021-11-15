package ahlers.faker.plugins.opendata500

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class DictionaryEntry(
  id: CompanyId,
  name: CompanyName,
  website: Option[CompanyWebsite])
