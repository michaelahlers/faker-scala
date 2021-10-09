package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait TemplateEntriesParser extends (IndexedSeq[TemplateLine] => Seq[TemplateEntry])
object TemplateEntriesParser {

  def using(parseEntry: TemplateEntryParser): TemplateEntriesParser = _
    .map(parseEntry(_))

  val default: TemplateEntriesParser =
    using(
      parseEntry = TemplateEntryParser.default)

}