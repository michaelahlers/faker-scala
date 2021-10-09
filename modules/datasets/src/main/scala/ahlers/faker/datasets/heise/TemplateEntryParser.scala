package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait TemplateEntryParser extends (TemplateLine => TemplateEntry)
object TemplateEntryParser {

  val default: TemplateEntryParser = line =>
    TemplateEntry(
      index = TemplateIndex(line.toInt),
      template = Template(line.toText))

}
