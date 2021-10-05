package ahlers.faker.plugins.heise

import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object DictionaryEntriesCsvWriter {

  implicit val orderingName: Ordering[Template] =
    Ordering.by(_.toText)

  def apply(outputDirectory: File, logger: Logger): DictionaryEntriesWriter = {
    dictionaryEntries =>
      outputDirectory.mkdirs()

      val templatesFile = outputDirectory / "index,template.csv"
      val usageCountryCodeWeightsFile = outputDirectory / "index,usage,country-code,weight.csv"

      /** Group around unique [[Template]] values. */
      val entriesByName: Map[Template, Seq[DictionaryEntry]] =
        dictionaryEntries
          .toSeq
          .groupBy(_.template)

      /** Retain an always-sorted collection. */
      val templates: Seq[Template] =
        entriesByName
          .keySet
          .toSeq
          .sorted

      /** Number them for brevity in the output. */
      val indexByName: Map[Template, Index] =
        templates
          .zipWithIndex
          .toMap
          .mapValues(Index(_))

      IO.writeLines(
        file = templatesFile,
        lines =
          templates
            .map(template =>
              """%x,%s"""
                .format(
                  indexByName(template).toInt,
                  template)),
        StandardCharsets.UTF_8,
        append = false
      )

      IO.writeLines(
        file = usageCountryCodeWeightsFile,
        lines =
          templates
            .flatMap(
              entriesByName(_)
                .flatMap(entry =>
                  entry
                    .regionWeights
                    .flatMap {
                      case RegionWeight(region, weight) =>
                        region
                          .countryCodes
                          .map(countryCode =>
                            """%x,%s,%s,%x"""
                              .format(
                                indexByName(entry.template).toInt,
                                entry.usage.toString,
                                countryCode.toText,
                                weight))
                    })),
        StandardCharsets.UTF_8,
        append = false
      )

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          templatesFile.length(),
          templatesFile))

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          usageCountryCodeWeightsFile.length(),
          usageCountryCodeWeightsFile))

      Seq(
        templatesFile,
        usageCountryCodeWeightsFile)
  }

}
