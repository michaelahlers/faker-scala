package ahlers.faker.datasets.heise

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait CountryWeightEntriesParser extends (IndexedSeq[CountryWeightLine] => Seq[CountryWeightEntry])
private[heise] object CountryWeightEntriesParser {

  def using(parseCountryWeightEntry: CountryWeightEntryParser): CountryWeightEntriesParser = _
    .map(parseCountryWeightEntry(_))

  val default: CountryWeightEntriesParser =
    using(CountryWeightEntryParser.default)

}
