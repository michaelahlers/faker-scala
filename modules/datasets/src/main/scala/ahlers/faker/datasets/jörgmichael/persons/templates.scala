package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 08, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case object templates {

  private[jörgmichael] val entries: Seq[TemplateEntry] =
    DictionaryIO.default
      .loadTemplateEntries()

  val byTemplateIndex: Map[TemplateIndex, Template] =
    entries
      .map(entry =>
        (entry.index, entry.template))
      .toMap

  val all: Seq[Template] =
    entries
      .map(_.template)

}
