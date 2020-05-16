package heise.ct

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
trait Gender
object Genders {

  case object Male extends Gender
  case object FirstMale extends Gender
  case object MostlyMale extends Gender

  case object Female extends Gender
  case object FirstFemale extends Gender
  case object MostlyFemale extends Gender

  case object Unisex extends Gender

}
