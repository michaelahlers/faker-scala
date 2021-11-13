package ahlers.faker.plugins.uscensus1990

import sbt._

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesWriter extends (IndexedSeq[DictionaryEntry] => Seq[File])
