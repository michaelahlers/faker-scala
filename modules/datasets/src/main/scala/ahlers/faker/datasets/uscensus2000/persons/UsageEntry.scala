package ahlers.faker.datasets.uscensus2000.persons

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[uscensus2000] case class UsageEntry(
  index: UsageIndex,
  name: NameIndex,
  usage: Usage)
