package ahlers.faker.plugins.heise

import sbt.File
import sbt.IO
import sbt.MessageOnlyException
import sbt.util.Logger
import better.files._

import scala.collection.convert.ImplicitConversionsToScala._
import java.net.URL
import java.nio.charset.StandardCharsets

/**
 * @since September 18, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object HeiseNameDictionaryUtilities {

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

  def decodeName(characterEncodings: Seq[CharacterEncoding], encodedName: String): String =
    characterEncodings
      .sortBy(_.pattern.length())
      .foldLeft(encodedName) {
        case (decodedName, characterEncoding) =>
          decodedName
            .replace(
              characterEncoding.pattern,
              characterEncoding.substitution)
      }

  def classifiedNames(dictionaryFile: File): Iterator[ClassifiedName] = {
    val lines: Iterator[String] =
      File(dictionaryFile.toPath)
        .lineIterator(StandardCharsets.ISO_8859_1)

    val characterEncodings: Seq[CharacterEncoding] =
      lines
        .dropWhile(!_.contains("char set"))
        .drop(6)
        .take(67)
        .map(_.tail.init)
        .map(_.replaceAll("""\/\*\*.*""", ""))
        .map(_.trim().split('='))
        .flatMap {

          case Array(character, encodings) =>
            encodings
              .split("or")
              .map(_.trim())
              .filter(_.nonEmpty)
              .map(CharacterEncoding(_, character.trim().toInt.toChar.toString))

          /** @todo Handle errors properly. */
          case _ =>
            ???

        }
        .toIndexedSeq

    val regionByIndex: Map[Int, String] =
      lines
        .dropWhile(!_.contains("list of countries"))
        .drop(7)
        .take(164)
        .sliding(2, 3)
        .map {

          case Seq(label, index) =>
            //(index.indexOf('|') - 30, localeByLabel(label.tail.init.trim()))
            (index.indexOf('|') - 30, label.tail.init.trim())

          /** @todo Handle errors properly. */
          case _ =>
            ???

        }
        .toMap

    /* Moves the iterator to the correct position for names. */
    lines
      .dropWhile(!_.contains("begin of name list"))
      .drop(2)

    //val decodeName = decodeName(characterEncodings, _)

    lines
      .flatMap { entry =>
        val genderO: Option[String] =
          Option(entry
            .take(2)
            .trim())
            .filter(_.nonEmpty)

        genderO match {

          case None =>
            None

          case Some(gender) =>
            val name =
              entry
                .slice(3, 29)
                .trim()

            val parts =
              decodeName(characterEncodings, name)
                .split('+')
                .toSeq

            Some(ClassifiedName(
              gender = gender,
              parts = parts))

        }
      }
  }

}
