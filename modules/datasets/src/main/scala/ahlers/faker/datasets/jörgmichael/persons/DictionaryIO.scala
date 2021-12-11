package ahlers.faker.datasets.jörgmichael.persons

import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since October 03, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[jörgmichael] trait DictionaryIO {

  def loadTemplateEntries(): Seq[TemplateEntry]

  def loadUsageEntries(): Seq[UsageEntry]

  def loadCountryWeightEntries(): Seq[CountryWeightEntry]

}

private[jörgmichael] object DictionaryIO {

  def using(
    parseTemplateEntries: TemplateEntriesParser,
    parseUsageRegionWeightEntries: UsageEntriesParser,
    parseCountryWeightEntries: CountryWeightEntriesParser
  ): DictionaryIO =
    new DictionaryIO {

      override def loadTemplateEntries() =
        parseTemplateEntries(Resource.my.getAsStream("template.csv")
          .lines(StandardCharsets.UTF_8)
          .zipWithIndex
          .map(TemplateLine.tupled)
          .toIndexedSeq)

      override def loadUsageEntries() =
        parseUsageRegionWeightEntries(Resource.my.getAsStream("template-index,usage.csv")
          .lines()
          .zipWithIndex
          .map(UsageLine.tupled)
          .toIndexedSeq)

      override def loadCountryWeightEntries() =
        parseCountryWeightEntries(Resource.my.getAsStream("index,country-code,weight.csv")
          .lines()
          .zipWithIndex
          .map(CountryWeightLine.tupled)
          .toIndexedSeq)

    }

  val default: DictionaryIO =
    using(
      parseTemplateEntries = TemplateEntriesParser.default,
      parseUsageRegionWeightEntries = UsageEntriesParser.default,
      parseCountryWeightEntries = CountryWeightEntriesParser.default
    )

}
