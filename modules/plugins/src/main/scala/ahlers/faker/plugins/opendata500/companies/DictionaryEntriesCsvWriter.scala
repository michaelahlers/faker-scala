package ahlers.faker.plugins.opendata500.companies

import sbt.Logger
import sbt.File

import java.io.PrintWriter

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntriesCsvWriter {

  def apply(
    dictionaryEntries: IndexedSeq[DictionaryEntry],
    nameWriter: PrintWriter,
    websiteWriter: PrintWriter,
    nameWebsiteWriter: PrintWriter
  ): Unit

  final def apply(
    dictionaryEntries: IndexedSeq[DictionaryEntry],
    nameFile: File,
    websiteFile: File,
    nameWebsiteFile: File
  ): Unit = {
    import better.files._

    (for {
      nameWriter <- nameFile.toScala.newFileOutputStream().printWriter().autoClosed
      websiteWriter <- websiteFile.toScala.newFileOutputStream().printWriter().autoClosed
      nameWebsiteWriter <- nameWebsiteFile.toScala.newFileOutputStream().printWriter().autoClosed
    } yield {
      apply(
        dictionaryEntries = dictionaryEntries,
        nameWriter = nameWriter,
        websiteWriter = websiteWriter,
        nameWebsiteWriter = nameWebsiteWriter
      )
    }).get()
  }

}

object DictionaryEntriesCsvWriter {

  def using(
    logger: Logger
  ): DictionaryEntriesCsvWriter = { (companyEntries, nameWriter, websiteWriter, nameWebsiteWriter) =>
    var nameIndex = 0
    var websiteIndex = 0

    companyEntries.foreach { companyEntry =>
      val name = companyEntry.name
      nameWriter.println(s"${name.toText}")

      companyEntry.website.foreach { website =>
        websiteWriter.println(s"${website.toText}")
        nameWebsiteWriter.println(f"$nameIndex%x,$websiteIndex%x")
        websiteIndex += 1
      }

      nameIndex += 1
    }

    nameWriter.flush()
    websiteWriter.flush()
    nameWebsiteWriter.flush()

    ()
  }

}
