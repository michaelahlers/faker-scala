package heise.ct

import cats.syntax.option._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 15, 2020
 */
case class Name(nameParts: Seq[NamePart], equivalentName: Option[EquivalentName])
object Name {
  def apply(nameParts: Iterable[NamePart]): Name = Name(nameParts.toSeq, none)
  def apply(namePart: NamePart, nameParts: NamePart*): Name = Name(namePart +: nameParts)
  def apply(namePart: NamePart, equivalentName: EquivalentName): Name = Name(Seq(namePart), equivalentName.some)
}
