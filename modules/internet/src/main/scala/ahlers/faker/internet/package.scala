package ahlers.faker

import ahlers.refined.string._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import io.estatico.newtype.macros._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 05, 2020
 */
package object internet {

  /** @see [[https://emailregex.com]] */
  type EmailLocalType = String Refined MatchEmailLocal

  /** @see [[https://en.wikipedia.org/wiki/Email_address#Local-part]] */
  @newtype case class EmailLocal(toText: EmailLocalType)

  /** @see [[https://emailregex.com]] */
  type EmailDomainType = String Refined
    (MatchEmailDomainHost
      Or MatchEmailDomainIPv4
      Or MatchEmailDomainIPv6)

  /** @see [[https://en.wikipedia.org/wiki/Email_address#Domain]] */
  @newtype case class EmailDomain(toText: EmailDomainType)

}
