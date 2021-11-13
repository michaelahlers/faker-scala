package ahlers.faker.datasets.census1990

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object names {

  private[heise] val entries: Seq[NameEntry] =
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
