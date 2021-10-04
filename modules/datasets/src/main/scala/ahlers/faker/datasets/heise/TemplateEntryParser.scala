package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait TemplateEntryParser extends (TemplateLine => TemplateEntry)
object TemplateEntryParser {

  val default: TemplateEntryParser =
    line =>
      line
        .toText
        .split(',') match {

        case Array(index, template) =>
          TemplateEntry(
            index = Index(Integer.parseInt(index, 16)),
            template = Template(template))

        /** @todo Proper error-handling. */
        case _ =>
          ???

      }

}
