package ahlers.faker.plugins.heise

import better.files._
import com.typesafe.config.ConfigFactory
import sbt.File
import sbt.IO
import sbt.MessageOnlyException
import sbt.util.Logger

import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Locale
import scala.collection.convert.ImplicitConversionsToScala._
import scala.util.control.NonFatal

/**
 * @since September 18, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object DictionaryIO {

  def downloadDictionary(sourceUrl: URL, downloadDirectory: File, dictionaryFileName: String, logger: Logger): File = {
    val downloadFiles =
      IO.unzipURL(
        sourceUrl,
        downloadDirectory)

    logger.info("""Extracted %d files from archive "%s" to "%s": %s."""
      .format(
        downloadFiles.size,
        sourceUrl,
        downloadDirectory,
        downloadFiles
          .map(_
            .toPath
            .drop(downloadDirectory
              .toPath
              .size)
            .reduce(_.resolve(_)))
          .mkString("\"", "\", \"", "\"")
      ))

    val dictionaryFile =
      downloadFiles
        .find(_.getName() == dictionaryFileName)
        .getOrElse(throw new MessageOnlyException(s"""File "$dictionaryFileName" wasn't found in archive "$sourceUrl"."""))

    logger.info(s"""Heise name dictionary saved to "$dictionaryFile".""")

    dictionaryFile
  }

}
