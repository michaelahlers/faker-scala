package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait TemplateEntryParser extends (TemplateLine => TemplateEntry)
private[heise] object TemplateEntryParser {

  val default: TemplateEntryParser = line =>
    TemplateEntry(
      index = TemplateIndex(line.toInt),
      template = Template(line.toText))

}
