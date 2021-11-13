package ahlers.faker.plugins.uscensus2000

/**
 * @since October 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait DictionaryOutputFormat
object DictionaryOutputFormat {
  case object Csv extends DictionaryOutputFormat
  // case object Json extends ClassifiedNamesWriterStrategy
  // case object Yaml extends ClassifiedNamesWriterStrategy
}
