package ahlers.faker.refined

import eu.timepit.refined.api._
import eu.timepit.refined.string._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 06, 2020
 */
object string {

  /** @see [[https://emailregex.com]] */
  final case class MatchEmailLocal()
  object MatchEmailLocal {

    private val regex = """^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")$""".r.pattern
    private val predicate: String => Boolean = s => {
      val matcher = regex.matcher(s)
      matcher.find() && matcher.matches()
    }

    implicit val emailLocalValidate: Validate.Plain[String, MatchEmailLocal] =
      Validate.fromPredicate(predicate, t => s"$t is a valid email local part", MatchEmailLocal())

  }

  /** @see [[https://emailregex.com]] */
  final case class MatchEmailDomainHost()
  object MatchEmailDomainHost {

    private val regex = """^(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)$""".r.pattern
    private val predicate: String => Boolean = s => {
      val matcher = regex.matcher(s)
      matcher.find() && matcher.matches()
    }

    implicit val emailDomainHostValidate: Validate.Plain[String, MatchEmailDomainHost] =
      Validate.fromPredicate(predicate, t => s"$t is a valid email domain host", MatchEmailDomainHost())

  }

  final case class MatchEmailDomainIPv4()
  object MatchEmailDomainIPv4 {

    import IPv4._

    implicit def emailDomainIPv4Validate: Validate.Plain[String, MatchEmailDomainIPv4] =
      Validate.fromPredicate(predicate, t => s"$t is a valid email domain IPv4 address", MatchEmailDomainIPv4())

    private val predicate: String => Boolean = s =>
      s.startsWith("[") &&
        s.endsWith("]") &&
        ipv4Validate.validate(s.drop(1).dropRight(1)).isPassed

  }

  final case class MatchEmailDomainIPv6()
  object MatchEmailDomainIPv6 {

    import IPv6._

    implicit def emailDomainIPv6Validate: Validate.Plain[String, MatchEmailDomainIPv6] =
      Validate.fromPredicate(predicate, t => s"$t is a valid email domain IPv6 address", MatchEmailDomainIPv6())

    private val predicate: String => Boolean = s =>
      s.startsWith("[") &&
        s.endsWith("]") &&
        ipv6Validate.validate(s.drop(1).dropRight(1)).isPassed

  }

}
