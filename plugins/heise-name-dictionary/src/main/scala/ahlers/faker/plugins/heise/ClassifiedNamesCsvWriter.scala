package ahlers.faker.plugins.heise

import sbt._

import java.nio.charset.StandardCharsets

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object ClassifiedNamesCsvWriter {

  def apply(outputDirectory: File): ClassifiedNamesWriter = {
    classifiedNames =>
      outputDirectory.mkdirs()

      val variationsFile = outputDirectory / "variations.csv"
      val localesFile = outputDirectory / "locales.csv"

      IO.writeLines(
        file = variationsFile,
        lines = Seq("reference,name"),
        StandardCharsets.UTF_8,
        append = false)

      IO.writeLines(
        file = localesFile,
        lines = Seq("reference,country-code,weight"),
        StandardCharsets.UTF_8,
        append = false)

      classifiedNames
        .toIterator
        .foreach {

          case equivalent: ClassifiedName.Equivalent =>

          case gendered: ClassifiedName.Gendered =>
            import gendered.reference

            IO.writeLines(
              file = variationsFile,
              lines =
                gendered
                  .variations
                  .map(name => s"${reference.toInt},${name.toString}"),
              StandardCharsets.UTF_8,
              append = true)

            IO.writeLines(
              file = localesFile,
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

        }

      Seq(
        variationsFile,
        localesFile)
  }

}
