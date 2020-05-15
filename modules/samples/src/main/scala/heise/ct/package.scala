package heise

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.string._
import eu.timepit.refined.numeric._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 13, 2020
 */
package object ct {

  type LocaleIndexType = Int Refined (NonNegative And LessEqual[W.`55`.T])
  @newtype case class LocaleIndex(toInt: LocaleIndexType)

  type LocaleNameType = String Refined (NonEmpty And Trimmed)
  @newtype case class LocaleName(toText: LocaleNameType)

  implicit val OrderingLocaleIndex: Ordering[LocaleIndex] = Ordering.by(_.toInt.value)
  implicit val OrderingLocaleName: Ordering[LocaleName] = Ordering.by(_.toText.value)

}
