package ahlers.faker.datasets.opendata500.companies

import java.nio.charset.StandardCharsets
import scala.io.Codec
import scala.io.Source

/**
 * @since December 6, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def loadNameEntries(): Seq[NameEntry]

  def loadWebsiteEntries(): Seq[WebsiteEntry]

  def loadNameWebsiteRelations(): Seq[NameWebsiteRelation]

}

object DictionaryIO {

  def using(): DictionaryIO = new DictionaryIO {

    override def loadNameEntries() =
      Source.fromResource("ahlers/faker/datasets/opendata500/companies/name.csv")(Codec.UTF8)
        .getLines()
        .zipWithIndex
        .map((NameLine(_, _)).tupled)
        .map(line =>
          NameEntry(
            index = NameIndex(line.toInt),
            name = Name(line.toText)
          ))
        .toSeq

    override def loadWebsiteEntries() =
      Source.fromResource("ahlers/faker/datasets/opendata500/companies/website.csv")(Codec.UTF8)
        .getLines()
        .zipWithIndex
        .map((WebsiteLine(_, _)).tupled)
        .map(line =>
          WebsiteEntry(
            index = WebsiteIndex(line.toInt),
            website = Website(line.toText)
          ))
        .toSeq

    override def loadNameWebsiteRelations() =
      Source.fromResource("ahlers/faker/datasets/opendata500/companies/name,website.csv")
        .getLines()
        .map(_.split(',') match {

          case Array(name, website) =>
            NameWebsiteRelation(
              NameIndex(Integer.parseInt(name, 16)),
              WebsiteIndex(Integer.parseInt(website, 16))
            )

          case _ =>
            /** @todo Proper error handling. */
            ???

        })
        .toSeq

  }

  val default: DictionaryIO = using()

}
