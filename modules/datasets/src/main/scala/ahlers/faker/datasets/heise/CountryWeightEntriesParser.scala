package ahlers.faker.datasets.heise

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait CountryWeightEntriesParser extends (IndexedSeq[CountryWeightLine] => Seq[CountryWeightEntry])
object CountryWeightEntriesParser {

  def using(parseCountryWeightEntry: CountryWeightEntryParser): CountryWeightEntriesParser = _
    .map(parseCountryWeightEntry(_))

  val default: CountryWeightEntriesParser =
    using(CountryWeightEntryParser.default)

}
