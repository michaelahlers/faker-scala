package ahlers.faker.datasets.opendata500.companies

import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since December 6, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def loadNameEntries(): Seq[NameEntry]

  //def loadWebsiteEntries(): Seq[WebsiteEntry]

}

object DictionaryIO {

  def using(): DictionaryIO = new DictionaryIO {

    override def loadNameEntries() =
      Resource.my.getAsStream("name.csv")
        .lines(StandardCharsets.UTF_8)
        .zipWithIndex
        .map(NameLine.tupled)
        .map(line =>
          NameEntry(
            index = NameIndex(line.toInt),
            name = Name(line.toText)
          ))
        .toSeq

    //override def loadWebsiteEntries() =
    //  Resource.my.getAsStream("index,website.csv")
    //    .lines(StandardCharsets.UTF_8)
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
