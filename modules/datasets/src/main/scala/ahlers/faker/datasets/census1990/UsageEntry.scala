package ahlers.faker.datasets.census1990

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[census1990] case class UsageEntry(
  index: UsageIndex,
  name: NameIndex,
  usage: Usage)
