package ahlers.faker.datasets.jörgmichael.persons

import ahlers.faker.PersonGivenName
import ahlers.faker.PersonGivenNameType
import ahlers.faker.datasets.jörgmichael.persons.Template.Equivalent
import ahlers.faker.datasets.jörgmichael.persons.Template.Hyphenated
import ahlers.faker.datasets.jörgmichael.persons.Template.Literal
import eu.timepit.refined.api.Refined

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object personGivenNames {

  implicit private[jörgmichael] class LiteralOps(private val self: Literal) extends AnyVal {
    import self._
    def toPersonGivenName: PersonGivenName =
      PersonGivenName(Refined.unsafeApply(toText))
  }

  implicit private[jörgmichael] class EquivalentOps(private val self: Equivalent) extends AnyVal {
    import self._

    def toPersonGivenNames: Seq[PersonGivenName] =
      short.toPersonGivenName ::
        long.toPersonGivenName ::
        Nil

  }

  implicit private[jörgmichael] class HyphenatedOps(private val self: Hyphenated) extends AnyVal {
    import self._

    def toLiterals: Seq[Literal] =
      Literal(s"${left.toText} ${right.toText}") ::
        Literal(s"${left.toText}-${right.toText}") ::
        Literal(s"${left.toText}${right.toText.toLowerCase}") ::
        Nil

    def toPersonGivenNames: Seq[PersonGivenName] =
      toLiterals
        .map(_.toPersonGivenName)

  }

  val all: Seq[PersonGivenName] =
    templates
      .all
      .flatMap {
        case template: Literal => Seq(template.toPersonGivenName)
        case template: Equivalent => template.toPersonGivenNames
        case template: Hyphenated => template.toPersonGivenNames
      }

}
