package ahlers.faker.datasets.heise

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Template
object Template {

  case class Literal(toText: String) extends Template
  case class Equivalent(short: Literal, long: Literal) extends Template
  case class Hyphenated(left: Hyphenated.Part, right: Hyphenated.Part) extends Template
  object Hyphenated {
    case class Part(toText: String)
  }

}
