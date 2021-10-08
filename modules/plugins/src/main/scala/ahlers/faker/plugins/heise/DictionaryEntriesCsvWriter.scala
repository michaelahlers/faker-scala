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

  def apply(outputDirectory: File, logger: Logger): DictionaryEntriesWriter = {
    dictionaryEntries =>
      outputDirectory.mkdirs()

      val templatesFile = outputDirectory / "template.csv"
      val usageFile = outputDirectory / "index,usage.csv"
      val countryCodeWeightFile = outputDirectory / "index,country-code,weight.csv"

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
                  usage)
            },
        append = false)

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

}
