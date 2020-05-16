package heise.ct

import cats.syntax.option._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 15, 2020
 */
case class Name(nameParts: Seq[NamePart], equivalentName: Option[EquivalentName], classification: Map[LocaleName, GivenNameProbability])
object Name {

  def apply(nameParts: Iterable[NamePart]): Name = Name(nameParts.toSeq, none, Map.empty[LocaleName, GivenNameProbability])
  def apply(namePart: NamePart, nameParts: NamePart*): Name = Name(namePart +: nameParts)
  def apply(namePart: NamePart, equivalentName: EquivalentName): Name = Name(Seq(namePart), equivalentName.some, Map.empty[LocaleName, GivenNameProbability])

  implicit class Ops(val name: Name) extends AnyVal {
    import name._
    def withClassifications(classification: Map[LocaleName, GivenNameProbability]): Name = copy(classification = classification)
    def withClassifications(classification: (LocaleName, GivenNameProbability)*): Name = withClassifications(classification.toMap)
  }

}
