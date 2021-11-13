package ahlers.faker.datasets.census1990

import ahlers.faker.PersonGivenName
import eu.timepit.refined.api.Refined

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object personGivenNames {

  implicit private[census1990] class NameOps(private val self: Name) /*extends AnyVal*/ {
    import self._
    def toPersonGivenName: PersonGivenName =
      PersonGivenName(Refined.unsafeApply(toText))
  }

  val all: Seq[PersonGivenName] =
    usages
      .entries
      .collect {
        case entry if entry.usage == Usage.Female || entry.usage == Usage.Male =>
          names
            .byNameIndex(entry.name)
            .toPersonGivenName
      }

}
