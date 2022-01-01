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

  // def loadWebsiteEntries(): Seq[WebsiteEntry]

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

    // override def loadWebsiteEntries() =
    //  Source.fromResource("ahlers/faker/datasets/opendata500/companies/name.csv")(Codec.UTF8)
    //    .getLines()
    //    .zipWithIndex
    //    .map(WebsiteLine.tupled)
    //    .map(line =>
    //      line.toText.split(',') match {
    //        case Array(nameIndex, website) =>
    //          WebsiteEntry(
    //            index = WebsiteIndex(line.toInt),
    //            name = NameIndex(nameIndex.toInt),
    //            website =
    //              website match {
    //                case "S" => Website.Sur
    //              }
    //          )
    //      })
    //    .toSeq

  }

  val default: DictionaryIO = using()

}
