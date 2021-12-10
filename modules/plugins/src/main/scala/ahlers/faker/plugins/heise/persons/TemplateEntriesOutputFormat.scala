package ahlers.faker.plugins.heise.persons

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since September 24, 2021
 */
sealed trait TemplateEntriesOutputFormat
object TemplateEntriesOutputFormat {
  case object Csv extends TemplateEntriesOutputFormat
  // case object Json extends ClassifiedNamesWriterStrategy
  // case object Yaml extends ClassifiedNamesWriterStrategy
}
