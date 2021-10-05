package ahlers.faker.datasets.heise

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait UsageRegionWeightEntryParser extends (UsageRegionWeightLine => UsageRegionWeightEntry)
object UsageRegionWeightEntryParser {

  val default: UsageRegionWeightEntryParser = _
    .toText
    .split(',') match {

    case Array(index, _, countryCode, weight) =>
      UsageRegionWeightEntry(
        index = Index(Integer.parseInt(index, 16)),
        usage = Usage.Female,
        countryCode = countryCode,
        weight = Weight(Integer.parseInt(weight, 16)))

    /** @todo Proper error-handling. */
    case _ =>
      ???

  }

}
