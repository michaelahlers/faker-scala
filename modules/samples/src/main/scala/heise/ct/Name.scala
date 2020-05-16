package heise.ct

import ahlers.faker.samples._
import cats.syntax.option._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 15, 2020
 */
case class Name(givenNames: Seq[PersonGivenName], equivalentName: Option[EquivalentName], classification: Map[LocaleName, GivenNameProbability])
object Name {

  def apply(givenNames: Iterable[PersonGivenName]): Name = Name(givenNames.toSeq, none, Map.empty[LocaleName, GivenNameProbability])
  def apply(givenName: PersonGivenName, givenNames: PersonGivenName*): Name = Name(givenName +: givenNames)
  def apply(givenName: PersonGivenName, equivalentName: EquivalentName): Name = Name(Seq(givenName), equivalentName.some, Map.empty[LocaleName, GivenNameProbability])

  implicit class Ops(val name: Name) extends AnyVal {
    import name._
    def withClassifications(classification: Map[LocaleName, GivenNameProbability]): Name = copy(classification = classification)
    def withClassifications(classification: (LocaleName, GivenNameProbability)*): Name = withClassifications(classification.toMap)
  }

}
