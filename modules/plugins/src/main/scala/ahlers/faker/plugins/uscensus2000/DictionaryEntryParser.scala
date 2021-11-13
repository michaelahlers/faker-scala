package ahlers.faker.plugins.uscensus2000

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntryParser extends ((Usage, DictionaryLine) => DictionaryEntry)
object DictionaryEntryParser {

  def using(
  ): DictionaryEntryParser = { (usage, line) =>
    line.toText
      .split(',') match {

      case Array(name, rank, _, _, _, _, _, _, _, _, _) =>
        DictionaryEntry(
          usage = usage,
          name = Name(name),
          rank = Rank(rank.toInt)
        )

      case _ =>
        /** @todo Proper error handling. */
        ???

    }
  }

  val default: DictionaryEntryParser =
    using()

}
