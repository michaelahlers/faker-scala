package ahlers.faker.plugins.opendata500

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

  def downloadDictionary(sourceUrl: URL, downloadFile: File): File

}

object DictionaryIO {

  def using(
    logger: Logger
  ): DictionaryIO = new DictionaryIO {

    override def downloadDictionary(sourceUrl: URL, downloadFile: File) = {
      downloadFile.getParentFile().mkdirs()

      try Using.urlInputStream(sourceUrl)(IO.transfer(_, downloadFile))
      catch {
        case NonFatal(reason) =>
          throw new MessageOnlyException(s"""Couldn't download "$sourceUrl" to "$downloadFile"; $reason.""")
      }

      logger.info("""Downloaded %d bytes from "%s" to "%s"."""
        .format(
          downloadFile.length(),
          sourceUrl,
          downloadFile
        ))

      downloadFile
    }

  }

}
