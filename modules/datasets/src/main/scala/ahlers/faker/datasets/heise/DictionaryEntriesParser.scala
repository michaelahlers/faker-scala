package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesParser extends (Seq[DictionaryLine] => Seq[DictionaryEntry])
object DictionaryEntriesParser {

  def using(parseEntry: DictionaryEntryParser): DictionaryEntriesParser =
    lines =>
      lines
        .map(parseEntry(_))

  val default: DictionaryEntriesParser =
    using(
      parseEntry = DictionaryEntryParser.default)

}
