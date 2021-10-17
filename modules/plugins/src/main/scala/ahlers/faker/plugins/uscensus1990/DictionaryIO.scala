package ahlers.faker.plugins.uscensus1990

import sbt.File
import java.net.URL

/**
 * @since October 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def downloadDictionary(sourceUrl: URL, downloadDirectory: File): File

  def loadDictionaryEntries(sourceFile: File): Seq[DictionaryEntry]

  def writeDictionary(entries: Seq[DictionaryEntry], outputDirectory: File): File

}

object DictionaryIO
