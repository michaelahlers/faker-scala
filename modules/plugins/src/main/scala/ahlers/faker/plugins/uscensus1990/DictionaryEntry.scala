package ahlers.faker.plugins.uscensus1990

/**
 * @since October 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class DictionaryEntry(
  usage: Usage,
  name: Name,
  frequency: Frequency,
  cumulativeFrequency: CumulativeFrequency,
  rank: Rank)
