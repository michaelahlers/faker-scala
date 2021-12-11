package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since September 25, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Usage
object Usage {

  sealed trait Gender
  object Gender {

    case object Male extends Usage with Gender
    case object FirstMale extends Usage with Gender
    case object MostlyMale extends Usage with Gender

    case object Female extends Usage with Gender
    case object FirstFemale extends Usage with Gender
    case object MostlyFemale extends Usage with Gender

    case object Unisex extends Usage with Gender

  }

  val Male = Gender.Male
  val FirstMale = Gender.FirstMale
  val MostlyMale = Gender.MostlyMale

  val Female = Gender.Female
  val FirstFemale = Gender.FirstFemale
  val MostlyFemale = Gender.MostlyFemale

  val Unisex = Gender.Unisex

  case object Equivalent extends Usage

}
