package ahlers.faker

import ahlers.faker.refined.extras
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection
import eu.timepit.refined.string
import io.estatico.newtype.macros._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
package object internet {

  /** @see [[https://emailregex.com]] */
  type EmailLocalType = String Refined
    (collection.NonEmpty
      And string.MatchesRegex["""(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")"""]
      And collection.MaxSize[64]
      And string.Trimmed)

  @newtype case class EmailLocal(toText: EmailLocalType)

  /** @see [[https://emailregex.com]] */
  type EmailDomainType = String Refined
    (collection.MaxSize[255]
      And string.Trimmed
      And (string.MatchesRegex["""(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)"""]
        Or extras.Drop[1, 1, string.IPv4]
        Or extras.Drop[1, 1, string.IPv6]))

  @newtype case class EmailDomain(toText: EmailDomainType)

}
