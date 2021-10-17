package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait UsageEntriesParser extends (IndexedSeq[UsageLine] => Seq[UsageEntry])
private[heise] object UsageEntriesParser {

  def using(parseEntry: UsageEntryParser): UsageEntriesParser = _
    .map(parseEntry(_))

  val default: UsageEntriesParser =
    using(
      parseEntry = UsageEntryParser.default)

}
