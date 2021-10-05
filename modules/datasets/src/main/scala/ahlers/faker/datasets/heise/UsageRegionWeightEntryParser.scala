package ahlers.faker.datasets.heise

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait UsageRegionWeightEntryParser extends (UsageRegionWeightLine => UsageRegionWeightEntry)
object UsageRegionWeightEntryParser {

  def using(
    parseUsage: UsageParser
  ): UsageRegionWeightEntryParser = _
    .toText
    .split(',') match {

    case Array(index, usage, countryCode, weight) =>
      UsageRegionWeightEntry(
        index = Index(Integer.parseInt(index, 16)),
        usage = parseUsage(usage),
        countryCode = countryCode,
        weight = Weight(Integer.parseInt(weight, 16)))

    /** @todo Proper error-handling. */
    case _ =>
      ???

  }

  val default =
    using(
      parseUsage = UsageParser.default)

}
