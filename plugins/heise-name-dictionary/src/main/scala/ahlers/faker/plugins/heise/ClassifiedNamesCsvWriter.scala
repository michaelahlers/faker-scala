package ahlers.faker.plugins.heise

import sbt._

import java.nio.charset.StandardCharsets
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

  def apply(outputDirectory: File, logger: Logger): ClassifiedNamesWriter = {
    classifiedNames =>
      outputDirectory.mkdirs()

      val variationsFile = outputDirectory / "name_variations.csv"
//val usageCountryCodeWeightsFile = outputDirectory / "usage_country-code_weights.csv"

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

      val namesByReference: Map[ClassifiedNameReference, Seq[ClassifiedName]] =
        classifiedNames
          .toSeq
          .groupBy(_.reference)

      IO.writeLines(
        file = variationsFile,
        lines =
          namesByReference
            .values
            .toSeq
            .flatten
            .sortBy(_.reference.toText)
            .collect {
              case withGender: ClassifiedName.WithGender =>
                withGender
            }
            .flatMap(withGender =>
              withGender
                .variations
                .map((withGender.reference, _)))
            .map {
              case (reference, name) =>
                s"""$reference,$name"""
            },
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
