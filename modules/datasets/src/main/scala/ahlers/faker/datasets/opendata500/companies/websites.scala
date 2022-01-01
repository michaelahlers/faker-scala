package ahlers.faker.datasets.opendata500.companies

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object websites {

  private val entries: Seq[WebsiteEntry] =
    DictionaryIO.default
      .loadWebsiteEntries()

  val byWebsiteIndex: Map[WebsiteIndex, Website] =
    entries
      .map(entry =>
        (entry.index, entry.website))
      .toMap

  val byNameIndex: Map[NameIndex, Seq[Website]] =
    entries
      .groupBy(_.name)
      .map { case (nameIndex, entries) =>
        (nameIndex, entries.map(_.website))
      }

  val all: Seq[Website] =
    entries
      .map(_.website)

}
