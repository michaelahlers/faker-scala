package ahlers.faker.datasets.heise

import ahlers.faker._
import cats.syntax.option._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 15, 2020
 */
sealed trait ClassifiedName

case class EquivalentNames(
  givenNames: Set[PersonGivenName],
  probabilityByLocale: Map[Locale, LocaleProbability])
  extends ClassifiedName

object EquivalentNames {

  def apply(givenNames: Iterable[PersonGivenName]): EquivalentNames =
    EquivalentNames(
      givenNames = givenNames.toSet,
      probabilityByLocale = Map.empty)

  def apply(givenName: PersonGivenName, givenNames: PersonGivenName*): EquivalentNames =
    EquivalentNames(givenName +: givenNames)

  implicit class Ops(val name: EquivalentNames) extends AnyVal {

    import name._

    def withProbabilities(classification: Map[Locale, LocaleProbability]): EquivalentNames =
      copy(probabilityByLocale = classification)

    def withProbabilities(classification: (Locale, LocaleProbability)*): EquivalentNames =
      withProbabilities(classification.toMap)

  }

}

case class GenderedName(
  gender: Gender,
  givenNames: Seq[PersonGivenName],
  probabilityByLocale: Map[Locale, LocaleProbability])
  extends ClassifiedName

object GenderedName {

  def apply(
    gender: Gender,
    givenNames: Iterable[PersonGivenName]
  ): GenderedName =
    GenderedName(
      gender = gender,
      givenNames = givenNames.toSeq,
      probabilityByLocale = Map.empty)

  def apply(
    gender: Gender,
    givenName: PersonGivenName,
    givenNames: PersonGivenName*
  ): GenderedName =
    GenderedName(gender, givenName +: givenNames)

  implicit class Ops(val name: GenderedName) extends AnyVal {

    import name._

    def withProbabilities(probabilityByLocale: Map[Locale, LocaleProbability]): GenderedName =
      copy(probabilityByLocale = probabilityByLocale)

    def withProbabilities(probabilityByLocale: (Locale, LocaleProbability)*): GenderedName =
      withProbabilities(probabilityByLocale.toMap)

  }

}
