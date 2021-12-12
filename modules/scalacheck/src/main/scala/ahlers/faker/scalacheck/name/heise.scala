package ahlers.faker.scalacheck.name

import ahlers.faker.PersonGivenName
import ahlers.faker.datasets.heise.ClassifiedNameIterator
import ahlers.faker.datasets.heise.GenderedName
import eu.timepit.refined.api.Refined
import org.scalacheck.Gen
import org.scalacheck.Gen.const
import org.scalacheck.Gen.oneOf

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
