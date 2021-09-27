package ahlers.faker.plugins.heise

import sbt.File

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait ClassifiedNamesWriter extends (TraversableOnce[DictionaryEntry] => Seq[File])
