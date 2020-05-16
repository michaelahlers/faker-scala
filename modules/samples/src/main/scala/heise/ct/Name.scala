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

case class Name(givenNames: Seq[PersonGivenName], classification: Map[Locale, GivenNameProbability]) extends ClassifiedName
object Name {

  def apply(givenNames: Iterable[PersonGivenName]): Name = Name(givenNames.toSeq, Map.empty[Locale, GivenNameProbability])
  def apply(givenName: PersonGivenName, givenNames: PersonGivenName*): Name = Name(givenName +: givenNames)

  implicit class Ops(val name: Name) extends AnyVal {
    import name._
    def withClassifications(classification: Map[Locale, GivenNameProbability]): Name = copy(classification = classification)
    def withClassifications(classification: (Locale, GivenNameProbability)*): Name = withClassifications(classification.toMap)
  }

}
