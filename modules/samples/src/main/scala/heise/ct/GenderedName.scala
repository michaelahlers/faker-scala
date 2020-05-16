package heise.ct

import ahlers.faker.samples._
import cats.syntax.option._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 15, 2020
 */
sealed trait ClassifiedName

case class EquivalentNames(givenNames: Set[PersonGivenName], classification: Map[Locale, GivenNameProbability]) extends ClassifiedName
object EquivalentNames {

  def apply(givenNames: Iterable[PersonGivenName]): EquivalentNames = EquivalentNames(givenNames.toSet, Map.empty[Locale, GivenNameProbability])
  def apply(givenName: PersonGivenName, givenNames: PersonGivenName*): EquivalentNames = EquivalentNames(givenName +: givenNames)

  implicit class Ops(val name: EquivalentNames) extends AnyVal {
    import name._
    def withClassifications(classification: Map[Locale, GivenNameProbability]): EquivalentNames = copy(classification = classification)
    def withClassifications(classification: (Locale, GivenNameProbability)*): EquivalentNames = withClassifications(classification.toMap)
  }

}

case class GenderedName(gender: Gender, givenNames: Seq[PersonGivenName], classification: Map[Locale, GivenNameProbability]) extends ClassifiedName
object GenderedName {

  def apply(gender: Gender, givenNames: Iterable[PersonGivenName]): GenderedName = GenderedName(gender, givenNames.toSeq, Map.empty[Locale, GivenNameProbability])
  def apply(gender: Gender, givenName: PersonGivenName, givenNames: PersonGivenName*): GenderedName = GenderedName(gender, givenName +: givenNames)

  implicit class Ops(val name: GenderedName) extends AnyVal {
    import name._
    def withClassifications(classification: Map[Locale, GivenNameProbability]): GenderedName = copy(classification = classification)
    def withClassifications(classification: (Locale, GivenNameProbability)*): GenderedName = withClassifications(classification.toMap)
  }

}
