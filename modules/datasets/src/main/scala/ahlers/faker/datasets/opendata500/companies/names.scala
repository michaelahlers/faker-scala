package ahlers.faker.datasets.opendata500.companies

/**
 * @since November 14, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object names {

  val byIndex: Map[NameIndex, Name] =
    DictionaryIO.default
      .loadNameEntries()
      .map(entry => (entry.index, entry.name))
      .toMap

}
