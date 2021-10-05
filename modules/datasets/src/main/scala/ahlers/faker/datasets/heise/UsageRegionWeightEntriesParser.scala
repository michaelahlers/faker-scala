package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait UsageRegionWeightEntriesParser extends (Seq[UsageRegionWeightLine] => Seq[UsageRegionWeightEntry])
object UsageRegionWeightEntriesParser {

  def using(parseEntry: UsageRegionWeightEntryParser): UsageRegionWeightEntriesParser = _
    .map(parseEntry(_))

  val default: UsageRegionWeightEntriesParser =
    using(
      parseEntry = UsageRegionWeightEntryParser.default)

}
