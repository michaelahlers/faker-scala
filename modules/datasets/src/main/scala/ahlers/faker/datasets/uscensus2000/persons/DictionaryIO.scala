package ahlers.faker.datasets.uscensus2000.persons

import java.nio.charset.StandardCharsets
import scala.io.Codec
import scala.io.Source

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def loadNameEntries(): Seq[NameEntry]

  def loadUsageEntries(): Seq[UsageEntry]

}

object DictionaryIO {

  def using(): DictionaryIO = new DictionaryIO {

    override def loadNameEntries() =
      Source.fromResource("ahlers/faker/datasets/uscensus2000/persons/name.csv")(Codec.UTF8)
        .getLines()
        .zipWithIndex
        .map(NameLine.tupled)
        .map(line =>
          NameEntry(
            index = NameIndex(line.toInt),
            name = Name(line.toText)
          ))
        .toSeq

    override def loadUsageEntries() =
      Source.fromResource("ahlers/faker/datasets/uscensus2000/persons/name-index,usage.csv")(Codec.UTF8)
        .getLines()
        .zipWithIndex
        .map(UsageLine.tupled)
        .map(line =>
          line.toText.split(',') match {

            case Array(nameIndex, usage) =>
              UsageEntry(
                index = UsageIndex(line.toInt),
                name = NameIndex(Integer.parseInt(nameIndex, 16)),
                usage =
                  usage match {

                    case "S" => Usage.Sur

                    /** @todo Proper error-handling. */
                    case _ =>
                      ???

                  }
              )

            /** @todo Proper error-handling. */
            case _ =>
              ???

          })
        .toSeq

  }

  val default: DictionaryIO = using()

}
