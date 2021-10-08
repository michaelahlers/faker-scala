package ahlers.faker.plugins.heise

import sbt.File

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesWriter extends (IndexedSeq[DictionaryEntry] => Seq[File])
