package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait CountryWeightEntryParser extends (CountryWeightLine => CountryWeightEntry)
object CountryWeightEntryParser {

  val default: CountryWeightEntryParser =
    line =>
      line
        .toText
        .split(',') match {

        case Array(usageIndex, countryCode, weight) =>
          CountryWeightEntry(
            usageIndex = UsageIndex(Integer.parseInt(usageIndex, 16)),
            countryWeight =
              CountryWeight(
                code = CountryCode(countryCode),
                weight = Weight(Integer.parseInt(weight, 16)))
          )

        /** @todo Proper error handling. */
        case _ =>
          ???

      }

}
