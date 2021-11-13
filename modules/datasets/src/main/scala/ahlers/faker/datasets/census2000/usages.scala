package ahlers.faker.datasets.census2000

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object usages {

  private[census1990] val entries: Seq[UsageEntry] =
    DictionaryIO.default
      .loadUsageEntries()

  val byUsageIndex: Map[UsageIndex, Usage] =
    entries
      .map(entry =>
        (entry.index, entry.usage))
      .toMap

  val byNameIndex: Map[NameIndex, Seq[Usage]] =
    entries
      .groupBy(_.name)
      .map { case (nameIndex, entries) =>
        (nameIndex, entries.map(_.usage))
      }

  val all: Seq[Usage] =
    entries
      .map(_.usage)

}
