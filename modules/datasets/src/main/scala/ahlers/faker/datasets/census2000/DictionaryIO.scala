package ahlers.faker.datasets.census2000

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

    override def loadNameEntries() = ???

    override def loadUsageEntries() = ???

  }

  val default: DictionaryIO = using()

}
