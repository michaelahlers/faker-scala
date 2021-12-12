package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[jörgmichael] trait CountryWeightEntriesParser extends (IndexedSeq[CountryWeightLine] => Seq[CountryWeightEntry])
private[jörgmichael] object CountryWeightEntriesParser {

  def using(parseCountryWeightEntry: CountryWeightEntryParser): CountryWeightEntriesParser = _
    .map(parseCountryWeightEntry(_))

  val default: CountryWeightEntriesParser =
    using(CountryWeightEntryParser.default)

}
