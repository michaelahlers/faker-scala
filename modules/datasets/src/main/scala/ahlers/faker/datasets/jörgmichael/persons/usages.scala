package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object usages {

  private val entries: Seq[UsageEntry] =
    DictionaryIO.default
      .loadUsageEntries()

  val byUsageIndex: Map[UsageIndex, Usage] =
    entries
      .map(entry =>
        (entry.index, entry.usage))
      .toMap

  val byTemplateIndex: Map[TemplateIndex, Seq[Usage]] =
    entries
      .groupBy(_.template)
      .map { case (templateIndex, entries) =>
        (templateIndex, entries.map(_.usage))
      }

  val all: Seq[Usage] =
    entries
      .map(_.usage)

}
