package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[jörgmichael] trait UsageEntriesParser extends (IndexedSeq[UsageLine] => Seq[UsageEntry])
private[jörgmichael] object UsageEntriesParser {

  def using(parseEntry: UsageEntryParser): UsageEntriesParser = _
    .map(parseEntry(_))

  val default: UsageEntriesParser =
    using(
      parseEntry = UsageEntryParser.default)

}
