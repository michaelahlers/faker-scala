package ahlers.faker.plugins.heise

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since September 24, 2021
 */
sealed trait ClassifiedNamesOutputFormat
object ClassifiedNamesOutputFormat {
  case object Csv extends ClassifiedNamesOutputFormat
  // case object Json extends ClassifiedNamesWriterStrategy
  // case object Yaml extends ClassifiedNamesWriterStrategy
}
