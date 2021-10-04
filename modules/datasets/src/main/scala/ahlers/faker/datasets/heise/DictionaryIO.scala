package ahlers.faker.datasets.heise

import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since October 03, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {
  def loadEntries(): Seq[TemplateEntry]
}

object DictionaryIO {

  def using(
    parseEntries: TemplateEntriesParser
  ): DictionaryIO =
    new DictionaryIO {

      override def loadEntries(): Seq[TemplateEntry] =
        parseEntries(Resource.my.getAsStream("index,template.csv")
          .lines(StandardCharsets.UTF_8)
          .toSeq
          .map(TemplateLine(_)))

    }

  val default: DictionaryIO =
    using(
      parseEntries = TemplateEntriesParser.default)

}
