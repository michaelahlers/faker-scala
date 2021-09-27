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

      val namesByReference: Map[ClassifiedNameReference, Seq[ClassifiedName]] =
        classifiedNames
          .toSeq
          .groupBy(_.reference)

      val indexByReference: Map[ClassifiedNameReference, Int] =
        namesByReference
          .keys
          .toSeq
          .sortBy(_.toText)
          .zipWithIndex
          .toMap

      val namesWithGender: Seq[ClassifiedName.WithGender] =
        namesByReference
          .values
          .toSeq
          .flatten
          .collect {
            case withGender: ClassifiedName.WithGender =>
              withGender
          }
          .sortBy(_.reference.toText)

      val namesWithEquivalents: Seq[ClassifiedName.WithEquivalents] =
        namesByReference
          .values
          .toSeq
          .flatten
          .collect {
            case withEquivalents: ClassifiedName.WithEquivalents =>
              withEquivalents
          }

      namesWithEquivalents
        .map { withEquivalent =>
          withEquivalent
            .equivalents
            .map(equivalent => namesByReference.keySet.find(equivalent == _))
        }
        .filterNot(_.forall(_.isDefined))
        .foreach(println(_))

      IO.writeLines(
        file = variationsFile,
        lines =
          namesWithGender
            .flatMap(withGender =>
              withGender
                .variations
                .map((withGender.reference, _)))
            .map {
              case (reference, name) =>
                (indexByReference(reference), name.toText)
            }
            .sortBy {
              case (index, _) =>
                index
            }
            .map {
              case (index, name) =>
                s"""$index,$name"""
            },
        StandardCharsets.UTF_8,
        append = false
      )

      IO.writeLines(
        file = usageCountryCodeWeightsFile,
        lines =
          namesByReference
            .values
            .flatten
            .toSeq
            .flatMap(name =>
              name
                .regionWeights
                .flatMap(regionWeight =>
                  regionWeight
                    .region
                    .countryCodes
                    .map((name.reference, _, regionWeight.weight))))
            .map {
              case (reference, countryCode, weight) =>
                (indexByReference(reference), countryCode.toText, weight)
            }
            .sortBy {
              case (index, _, _) =>
                index
            }
            .map {
              case (index, countryCode, weight) =>
                s"""$index,$countryCode,$weight"""
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
