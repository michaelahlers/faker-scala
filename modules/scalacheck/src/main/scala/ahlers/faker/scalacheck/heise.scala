package ahlers.faker.scalacheck

import ahlers.faker.datasets.heise._
import ahlers.faker._
import org.scalacheck.Gen._
import org.scalacheck._
import eu.timepit.refined.api.Refined

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 18, 2020
 */
object heise {

  val genPersonGivenName: Gen[PersonGivenName] =
    oneOf(
      ClassifiedNameIterator()
        .collect { case name: GenderedName => name }
        .toIndexedSeq)
      .flatMap {
        case GenderedName(_, Seq(givenName), _) => const(givenName)
        case GenderedName(_, givenNames, _) =>
          oneOf(" ", "-")
            .map(givenNames.mkString(_))
            .map(givenName => PersonGivenName(Refined.unsafeApply(givenName)))
      }

}
