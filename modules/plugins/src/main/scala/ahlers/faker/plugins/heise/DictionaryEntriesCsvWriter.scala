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

      val namesFile = outputDirectory / "reference,name.csv"
      val usageCountryCodeWeightsFile = outputDirectory / "reference,usage,country-code,weights.csv"

      namesFile.delete()
      usageCountryCodeWeightsFile.delete()

      /*IO.writeLines(
        file = namesFile,
        lines = Seq("reference,name"),
        StandardCharsets.UTF_8,
        append = false)*/

      /*IO.writeLines(
        file = usageCountryCodeWeightsFile,
        lines = Seq("reference,usage,country-code,weight"),
        StandardCharsets.UTF_8,
        append = false)*/

      /** Group around unique [[Template]] values. */
      val entriesByName: Map[Template, Seq[DictionaryEntry]] =
        dictionaryEntries
          .toSeq
          .groupBy(_.template)

      /** Retain an always-sorted collection. */
      val names: Seq[Template] =
        entriesByName
          .keySet
          .toSeq
          .sorted

      /** Number them for brevity in the output. */
      val indexByName: Map[Template, Reference] =
        names
          .zipWithIndex
          .toMap
          .mapValues(Reference(_))

      IO.writeLines(
        file = namesFile,
        lines =
          names
            .map(name =>
              s"""${Integer.toHexString(indexByName(name).toInt)},${name.toText}"""),
        StandardCharsets.UTF_8,
        append = true
      )

      IO.writeLines(
        file = usageCountryCodeWeightsFile,
        lines =
          names
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
                            s"${indexByName(entry.template)},${entry.usage.toString},${countryCode.toText},$weight")
                    })),
        StandardCharsets.UTF_8,
        append = true
      )

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          namesFile.length(),
          namesFile))

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          usageCountryCodeWeightsFile.length(),
          usageCountryCodeWeightsFile))

      Seq(
        namesFile,
        usageCountryCodeWeightsFile)
  }

}
