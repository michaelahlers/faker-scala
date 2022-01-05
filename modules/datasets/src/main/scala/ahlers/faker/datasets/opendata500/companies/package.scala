package ahlers.faker.datasets.opendata500

/**
 * @since January 01, 2022
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
package object companies {

  lazy val names: Map[NameIndex, Name] = {
    DictionaryIO.default
      .loadNameEntries()
      .map(entry => (entry.index, entry.name))
      .toMap
  }

  lazy val websites: Map[WebsiteIndex, Website] = {
    DictionaryIO.default
      .loadWebsiteEntries()
      .map(entry => (entry.index, entry.website))
      .toMap
  }

  lazy val nameWebsites: Map[NameIndex, Seq[WebsiteIndex]] = {
    DictionaryIO.default
      .loadNameWebsiteRelations()
      .groupBy(_.name)
      .map { case (name, relations) =>
        (name, relations.map(_.website))
      }
  }

  lazy val websiteNames: Map[WebsiteIndex, Seq[NameIndex]] = {
    DictionaryIO.default
      .loadNameWebsiteRelations()
      .groupBy(_.website)
      .map { case (website, relations) =>
        (website, relations.map(_.name))
      }
  }

  def nameBy(website: WebsiteIndex): Seq[Name] =
    websiteNames
      .get(website)
      .map(_.flatMap(names.get))
      .getOrElse(Nil)

  def websiteBy(name: NameIndex): Seq[Website] =
    nameWebsites
      .get(name)
      .map(_.flatMap(websites.get))
      .getOrElse(Nil)

}
