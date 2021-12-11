package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[jörgmichael] trait TemplateEntriesParser extends (IndexedSeq[TemplateLine] => Seq[TemplateEntry])
private[jörgmichael] object TemplateEntriesParser {

  def using(parseEntry: TemplateEntryParser): TemplateEntriesParser = _
    .map(parseEntry(_))

  val default: TemplateEntriesParser =
    using(
      parseEntry = TemplateEntryParser.default)

}
