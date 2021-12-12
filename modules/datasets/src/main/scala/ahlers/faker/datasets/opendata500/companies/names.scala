package ahlers.faker.datasets.opendata500.companies

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object names {

  private[opendata500] val entries: Seq[NameEntry] =
    DictionaryIO.default
      .loadNameEntries()

  val byNameIndex: Map[NameIndex, Name] =
    entries
      .map(entry =>
        (entry.index, entry.name))
      .toMap

  val all: Seq[Name] =
    entries
      .map(_.name)

}
