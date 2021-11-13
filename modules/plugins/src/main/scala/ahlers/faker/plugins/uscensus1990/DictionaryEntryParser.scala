package ahlers.faker.plugins.uscensus1990

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntryParser extends ((Usage, DictionaryLine) => DictionaryEntry)
object DictionaryEntryParser {

  def using(): DictionaryEntryParser = { (usage, line) =>
    line.toText.split(' ') match {
      case Array(name, frequency, cumulativeFrequency, rank) =>
        DictionaryEntry(
          usage = usage,
          name = Name(name),
          frequency = Frequency(frequency.toFloat),
          cumulativeFrequency = CumulativeFrequency(cumulativeFrequency.toFloat),
          rank = Rank(rank.toInt)
        )
    }
  }

  val default: DictionaryEntryParser =
    using()

}
