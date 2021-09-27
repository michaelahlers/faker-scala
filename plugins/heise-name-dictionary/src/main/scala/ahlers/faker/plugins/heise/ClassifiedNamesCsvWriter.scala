package ahlers.faker.plugins.heise

import sbt._

import java.nio.charset.StandardCharsets
import scala.collection.SortedMap
import scala.collection.mutable
import cats.syntax.semigroup._
import cats.instances.map._
import cats.instances.list._
import cats.instances.vector._

/**
 * @since September 24, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object ClassifiedNamesCsvWriter {

  implicit val orderingName: Ordering[Name] =
    Ordering.by(_.toText)

  def apply(outputDirectory: File, logger: Logger): ClassifiedNamesWriter = {
    dictionaryEntries =>
      outputDirectory.mkdirs()

      val variationsFile = outputDirectory / "name_variations.csv"
      val usageCountryCodeWeightsFile = outputDirectory / "usage_country-code_weights.csv"

      IO.writeLines(
        file = variationsFile,
        lines = Seq("reference,variation"),
        StandardCharsets.UTF_8,
        append = false)

//IO.writeLines(
//  file = usageCountryCodeWeightsFile,
//  lines = Seq("reference,country-code,weight"),
//  StandardCharsets.UTF_8,
//  append = false)

      /** Group around unique [[Name]] values. */
      val entriesByName: Map[Name, Seq[DictionaryEntry]] =
        dictionaryEntries
          .toSeq
          .groupBy(_.template)

      /** Retain an always-sorted collection. */
      val names: Seq[Name] =
        entriesByName
          .keySet
          .toSeq
          .sorted

      /** Number them for brevity in the output. */
      val indexByName: Map[Name, Int] =
        names
          .zipWithIndex
          .toMap

      IO.writeLines(
        file = variationsFile,
        lines =
          names
            .map(name =>
              s"""${indexByName(name)},${name.toText}"""),
        StandardCharsets.UTF_8,
        append = false
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
        append = false
      )

//logger.info("""Wrote %d bytes to "%s"."""
//  .format(
//    variationsFile.length(),
//    variationsFile))

//logger.info("""Wrote %d bytes to "%s"."""
//  .format(
//    usageCountryCodeWeightsFile.length(),
//    usageCountryCodeWeightsFile))

      Seq.empty
  }

}
