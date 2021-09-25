package ahlers.faker.plugins.heise

/**
 * @since September 25, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Usage
sealed trait Gender
object Usage {

  case object Male extends Usage with Gender
  case object FirstMale extends Usage with Gender
  case object MostlyMale extends Usage with Gender

  case object Female extends Usage with Gender
  case object FirstFemale extends Usage with Gender
  case object MostlyFemale extends Usage with Gender

  case object Unisex extends Usage with Gender

  case object Equivalent extends Usage

}
