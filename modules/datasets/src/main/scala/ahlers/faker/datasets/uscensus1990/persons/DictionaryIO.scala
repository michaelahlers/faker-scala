package ahlers.faker.datasets.uscensus1990.persons

import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[uscensus1990] trait DictionaryIO {

  def loadNameEntries(): Seq[NameEntry]

  def loadUsageEntries(): Seq[UsageEntry]

}

private[uscensus1990] object DictionaryIO {

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

    override def loadUsageEntries() =
      Resource.my.getAsStream("index,usage.csv")
        .lines(StandardCharsets.UTF_8)
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
                    case "F" => Usage.Female
                    case "M" => Usage.Male
                    case "L" => Usage.Last
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
