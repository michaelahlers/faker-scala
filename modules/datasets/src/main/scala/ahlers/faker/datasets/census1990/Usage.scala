package ahlers.faker.datasets.census1990

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Usage
object Usage {

  sealed trait Gender
  object Gender {
    case object Female extends Usage with Gender
    case object Male extends Usage with Gender
  }

  val Female = Gender.Female
  val Male = Gender.Male

  case object Last extends Usage

}
