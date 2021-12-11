package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object countryWeights {

  private[jörgmichael] val entries: Seq[CountryWeightEntry] =
    DictionaryIO.default
      .loadCountryWeightEntries()

  val byUsageIndex: Map[UsageIndex, CountryWeight] =
    entries
      .map(entry =>
        (entry.usageIndex, entry.countryWeight))
      .toMap

  val all: Seq[CountryWeight] =
    entries
      .map(_.countryWeight)

}
