package ahlers.faker.plugins.heise

import sbt.template
import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object DictionaryEntriesCsvWriter {

  implicit val orderingTemplate: Ordering[Template] =
    Ordering.by(_.toText)

  def using(
    templatesFile: File,
    usageFile: File,
    countryCodeWeightFile: File,
    logger: Logger
  ): DictionaryEntriesWriter = { dictionaryEntries =>
    templatesFile.getParentFile.mkdirs()
    usageFile.getParentFile.mkdirs()
    countryCodeWeightFile.getParentFile.mkdirs()

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
      file = templatesFile,
      lines =
        templates
          .map(template =>
            """%s"""
              .format(
                template.toText)),
      StandardCharsets.UTF_8,
      append = false)

    IO.writeLines(
      file = usageFile,
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
          },
      append = false
    )

    IO.writeLines(
      file = countryCodeWeightFile,
      lines =
        countryCodeWeightByTemplateUsage
          .map { case (template, usage, countryCode, weight) =>
            """%x,%s,%x"""
              .format(
                usageByTemplate.indexOf((template, usage)),
                countryCode.toText,
                weight.toInt)
          },
      append = false
    )

    /*logger.info("""Wrote %d bytes to "%s"."""
        .format(
          usageCountryCodeWeightsFile.length(),
          usageCountryCodeWeightsFile))*/

    Seq(
      templatesFile,
      usageFile,
      countryCodeWeightFile
    )
  }

  def apply(outputDirectory: File, logger: Logger): DictionaryEntriesWriter = {
    val templatesFile = outputDirectory / "template.csv"
    val usageFile = outputDirectory / "index,usage.csv"
    val countryCodeWeightFile = outputDirectory / "index,country-code,weight.csv"

    using(
      templatesFile = templatesFile,
      usageFile = usageFile,
      countryCodeWeightFile = countryCodeWeightFile,
      logger = logger
    )
  }

}
