package ahlers.faker.datasets.opendata500.companies

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object websites {

  val byIndex: Map[WebsiteIndex, Website] =
    DictionaryIO.default
      .loadWebsiteEntries()
      .map(entry => (entry.index, entry.website))
      .toMap

}
