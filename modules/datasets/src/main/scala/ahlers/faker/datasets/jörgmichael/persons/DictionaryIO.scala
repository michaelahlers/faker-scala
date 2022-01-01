package ahlers.faker.datasets.jörgmichael.persons

import java.nio.charset.StandardCharsets
import scala.io.Codec
import scala.io.Source

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
        parseTemplateEntries(Source.fromResource("ahlers/faker/datasets/jörgmichael/persons/template.csv")(Codec.UTF8)
          .getLines()
          .zipWithIndex
          .map((TemplateLine(_, _)).tupled)
          .toIndexedSeq)

      override def loadUsageEntries() =
        parseUsageRegionWeightEntries(Source.fromResource("ahlers/faker/datasets/jörgmichael/persons/template-index,usage.csv")(Codec.UTF8)
          .getLines()
          .zipWithIndex
          .map((UsageLine(_, _)).tupled)
          .toIndexedSeq)

      override def loadCountryWeightEntries() =
        parseCountryWeightEntries(Source.fromResource("ahlers/faker/datasets/jörgmichael/persons/template-index,country-code,weight.csv")(Codec.UTF8)
          .getLines()
          .zipWithIndex
          .map((CountryWeightLine(_, _)).tupled)
          .toIndexedSeq)

    }

  val default: DictionaryIO =
    using(
      parseTemplateEntries = TemplateEntriesParser.default,
      parseUsageRegionWeightEntries = UsageEntriesParser.default,
      parseCountryWeightEntries = CountryWeightEntriesParser.default
    )

}
