package ahlers.faker.datasets.heise

import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since October 03, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def loadTemplateEntries(): Seq[TemplateEntry]

  def loadUsageRegionWeightEntries(): Seq[UsageRegionWeightEntry]

}

object DictionaryIO {

  def using(
    parseTemplateEntries: TemplateEntriesParser,
    parseUsageRegionWeightEntries: UsageRegionWeightEntriesParser
  ): DictionaryIO =
    new DictionaryIO {

      override def loadTemplateEntries() =
        parseTemplateEntries(Resource.my.getAsStream("index,template.csv")
          .lines(StandardCharsets.UTF_8)
          .toSeq
          .map(TemplateLine(_)))

      override def loadUsageRegionWeightEntries() =
        parseUsageRegionWeightEntries(Resource.my.getAsStream("index,usage,country-code,weight.csv")
          .lines(StandardCharsets.UTF_8)
          .toSeq
          .map(UsageRegionWeightLine(_)))

    }

  val default: DictionaryIO =
    using(
      parseTemplateEntries = TemplateEntriesParser.default,
      parseUsageRegionWeightEntries = UsageRegionWeightEntriesParser.default)

}
