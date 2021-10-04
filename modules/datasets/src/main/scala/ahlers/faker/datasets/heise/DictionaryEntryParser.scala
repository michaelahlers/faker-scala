package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntryParser extends (DictionaryLine => DictionaryEntry)
object DictionaryEntryParser {

  val default: DictionaryEntryParser =
    line =>
      line
        .toText
        .split(',') match {

        case Array(index, template) =>
          DictionaryEntry(
            index = Index(Integer.parseInt(index, 16)),
            template = Template(template))

        /** @todo Proper error-handling. */
        case _ =>
          ???

      }

}
