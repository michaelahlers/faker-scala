package ahlers.faker.plugins.heise

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since September 24, 2021
 */
sealed trait DictionaryEntriesOutputFormat
object DictionaryEntriesOutputFormat {
  case object Csv extends DictionaryEntriesOutputFormat
  // case object Json extends ClassifiedNamesWriterStrategy
  // case object Yaml extends ClassifiedNamesWriterStrategy
}
