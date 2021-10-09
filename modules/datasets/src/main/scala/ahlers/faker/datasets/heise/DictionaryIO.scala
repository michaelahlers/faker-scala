package ahlers.faker.datasets.heise

import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since October 03, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def loadTemplateEntries(): Seq[TemplateEntry]

  def loadUsageEntries(): Seq[UsageEntry]

}

object DictionaryIO {

  def using(
    parseTemplateEntries: TemplateEntriesParser,
    parseUsageRegionWeightEntries: UsageEntriesParser
  ): DictionaryIO =
    new DictionaryIO {

      override def loadTemplateEntries() =
        parseTemplateEntries(Resource.my.getAsStream("template.csv")
          .lines(StandardCharsets.UTF_8)
          .zipWithIndex
          .map(TemplateLine.tupled)
          .toIndexedSeq)

      override def loadUsageEntries() =
        parseUsageRegionWeightEntries(Resource.my.getAsStream("index,usage,country-code,weight.csv")
          .lines()
          .zipWithIndex
          .map(UsageLine.tupled)
          .toIndexedSeq)

    }

  val default: DictionaryIO =
    using(
      parseTemplateEntries = TemplateEntriesParser.default,
      parseUsageRegionWeightEntries = UsageEntriesParser.default)

}
