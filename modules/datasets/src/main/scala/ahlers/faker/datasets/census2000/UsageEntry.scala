package ahlers.faker.datasets.census2000

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[census2000] case class UsageEntry(
  index: UsageIndex,
  name: NameIndex,
  usage: Usage)
