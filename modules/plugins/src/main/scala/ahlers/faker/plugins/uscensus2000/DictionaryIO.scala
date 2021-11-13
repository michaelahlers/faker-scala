package ahlers.faker.plugins.uscensus2000

import sbt.Keys._
import sbt._
import sbt.io.Using

import java.net.URL
import java.nio.charset.StandardCharsets
import scala.collection.convert.ImplicitConversionsToScala._
import scala.util.control.NonFatal

/**
 * @since October 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryIO {

  def downloadDictionary(sourceUrl: URL, downloadDirectory: File, dictionaryFileName: String): File

}

object DictionaryIO {

  def using(
    logger: Logger
  ): DictionaryIO = new DictionaryIO {

    override def downloadDictionary(sourceUrl: URL, downloadDirectory: File, dictionaryFileName: String) = {
      downloadDirectory.mkdirs()

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

      logger.info(s"""Dictionary entries file saved to "$dictionaryFile".""")

      dictionaryFile
    }

  }

}
