package gov.census.genealogoy.census1990

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
sealed trait Gender
object Gender {
  case object Female extends Gender
  case object Male extends Gender
}
