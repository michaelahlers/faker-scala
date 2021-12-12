package ahlers.faker.datasets.uscensus1990.persons

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[uscensus1990] case class UsageEntry(
  index: UsageIndex,
  name: NameIndex,
  usage: Usage)
