package ahlers.faker.datasets.uscensus1990.persons

import ahlers.faker.PersonFamilyName
import eu.timepit.refined.api.Refined

/**
 * @since October 16, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object personFamilyNames {

  implicit private[uscensus1990] class NameOps(private val self: Name) /*extends AnyVal*/ {
    import self._
    def toPersonFamilyName: PersonFamilyName =
      PersonFamilyName(Refined.unsafeApply(toText))
  }

  val all: Seq[PersonFamilyName] =
    usages
      .entries
      .collect {
        case entry if entry.usage == Usage.Last =>
          names
            .byNameIndex(entry.name)
            .toPersonFamilyName
      }

}
