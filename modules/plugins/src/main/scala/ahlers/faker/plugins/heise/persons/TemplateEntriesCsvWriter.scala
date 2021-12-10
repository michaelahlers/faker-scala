package ahlers.faker.plugins.heise.persons

import sbt.template
import sbt._

import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait TemplateEntriesCsvWriter {

  def apply(
    dictionaryEntries: IndexedSeq[TemplateEntry],
    templatesStream: OutputStream,
    usageStream: OutputStream,
    countryCodeWeightStream: OutputStream
  ): Unit

  final def apply(
    dictionaryEntries: IndexedSeq[TemplateEntry],
    templatesFile: File,
    usageFile: File,
    countryCodeWeightFile: File
  ): Unit = {
    val templatesStream = new FileOutputStream(templatesFile, false)
    val usageStream = new FileOutputStream(usageFile, false)
    val countryCodeWeightStream = new FileOutputStream(countryCodeWeightFile, false)

    try apply(
      dictionaryEntries = dictionaryEntries,
      templatesStream = templatesStream,
      usageStream = usageStream,
      countryCodeWeightStream = countryCodeWeightStream
    )
    finally {
      templatesStream.flush()
      usageStream.flush()
      countryCodeWeightStream.flush()

      templatesStream.close()
      usageStream.close()
      countryCodeWeightStream.close()
    }
  }

}

object TemplateEntriesCsvWriter {

  implicit val orderingTemplate: Ordering[Template] =
    Ordering.by(_.toText)

  def using(
    logger: Logger
  ): TemplateEntriesCsvWriter = {
    (
      dictionaryEntries,
      templatesStream,
      usageStream,
      countryCodeWeightStream
    ) =>
      /** Group around unique [[Template]] values. */
      val templates: Seq[Template] =
        dictionaryEntries
          .sortBy(_.template)
          .map(_.template)
          .distinct

      val usageByTemplate: Seq[(Template, Usage)] =
        dictionaryEntries
          .sortBy(_.template)
          .map(entry =>
            (entry.template, entry.usage))
          .distinct

      val countryCodeWeightByTemplateUsage: Seq[(Template, Usage, CountryCode, Weight)] =
        dictionaryEntries
          .sortBy(_.template)
          .flatMap(entry =>
            entry
              .regionWeights
              .flatMap(regionWeight =>
                regionWeight
                  .region
                  .countryCodes
                  .map((entry.template, entry.usage, _, regionWeight.weight))))

      IO.writeLines(
        writer = new PrintWriter(templatesStream, true),
        lines =
          templates
            .map(template =>
              """%s"""
                .format(
                  template.toText))
      )

      IO.writeLines(
        writer = new PrintWriter(usageStream, true),
        lines =
          usageByTemplate
            .map { case (template, usage) =>
              """%x,%s"""
                .format(
                  templates.indexOf(template),
                  usage match {
                    case Usage.Female => "F"
                    case Usage.FirstFemale => "1F"
                    case Usage.MostlyFemale => "?F"
                    case Usage.Male => "M"
                    case Usage.FirstMale => "1M"
                    case Usage.MostlyMale => "?M"
                    case Usage.Unisex => "?"
                    case Usage.Equivalent => "="
                  }
                )
            }
      )

      IO.writeLines(
        writer = new PrintWriter(countryCodeWeightStream, true),
        lines =
          countryCodeWeightByTemplateUsage
            .map { case (template, usage, countryCode, weight) =>
              """%x,%s,%x"""
                .format(
                  usageByTemplate.indexOf((template, usage)),
                  countryCode.toText,
                  weight.toInt)
            }
      )

      /*logger.info("""Wrote %d bytes to "%s"."""
        .format(
          usageCountryCodeWeightsFile.length(),
          usageCountryCodeWeightsFile))*/
  }

}
