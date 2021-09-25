package ahlers.faker.plugins.heise

import sbt._

import java.nio.charset.StandardCharsets
import scala.collection.mutable

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object ClassifiedNamesCsvWriter {

  def apply(outputDirectory: File, logger: Logger): ClassifiedNamesWriter = {
    classifiedNames =>
      outputDirectory.mkdirs()

      val nameVariationsFile = outputDirectory / "name-variations.csv"
      val nameLocalesFile = outputDirectory / "name-locales.csv"
      val equivalentNamesFile = outputDirectory / "equivalents-names.csv"
      //val equivalentLocalesFile = outputDirectory / "equivalents-locales.csv"

      IO.writeLines(
        file = nameVariationsFile,
        lines = Seq("reference,person-name"),
        StandardCharsets.UTF_8,
        append = false)

      IO.writeLines(
        file = nameLocalesFile,
        lines = Seq("reference,country-code,weight"),
        StandardCharsets.UTF_8,
        append = false)

      IO.writeLines(
        file = equivalentNamesFile,
        lines = Seq("reference,short-reference,long-reference"),
        StandardCharsets.UTF_8,
        append = false)

      val equivalents: mutable.Buffer[ClassifiedName.Equivalent] =
        mutable.Buffer.empty

      val generedByNameVariation: mutable.Map[Name, ClassifiedName.Gendered] =
        mutable.Map.empty

      classifiedNames
        .toIterator
        .foreach {

          case equivalent: ClassifiedName.Equivalent =>
            equivalents += equivalent

          case gendered: ClassifiedName.Gendered =>
            import gendered.reference

            IO.writeLines(
              file = nameVariationsFile,
              lines =
                gendered
                  .variations
                  .map(name => s"${reference.toInt},${name.toString}"),
              StandardCharsets.UTF_8,
              append = true)

            IO.writeLines(
              file = nameLocalesFile,
              lines =
                gendered
                  .regionWeights
                  .flatMap(regionWeight =>
                    regionWeight
                      .region
                      .countryCodes
                      .map(countryCode =>
                        s"${reference.toInt},${countryCode.toText},${regionWeight.weight}")),
              StandardCharsets.UTF_8,
              append = true
            )

            gendered
              .variations
              .foreach(name =>
                generedByNameVariation
                  .put(name, gendered)
                  .foreach(original =>
                    logger.warn(s"""Replaced index key $name value $original with $gendered.""")))

        }

      IO.writeLines(
        file = nameVariationsFile,
        lines =
          equivalents
            .map { equivalent =>
              val short = generedByNameVariation(equivalent.short)
              val long = generedByNameVariation(equivalent.long)
              s"""${equivalent.reference},${short.reference},${long.reference}"""
            },
        StandardCharsets.UTF_8,
        append = true
      )

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          nameVariationsFile.length(),
          nameVariationsFile))

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          nameLocalesFile.length(),
          nameLocalesFile))

      logger.info("""Wrote %d bytes to "%s"."""
        .format(
          equivalentNamesFile.length(),
          equivalentNamesFile))

      Seq(
        nameVariationsFile,
        nameLocalesFile)
  }

}
