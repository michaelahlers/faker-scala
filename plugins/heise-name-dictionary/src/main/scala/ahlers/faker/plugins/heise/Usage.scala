package ahlers.faker.plugins.heise

/**
 * @since September 25, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Usage
object Usage {

  case object Male extends Usage
  case object FirstMale extends Usage
  case object MostlyMale extends Usage

  case object Female extends Usage
  case object FirstFemale extends Usage
  case object MostlyFemale extends Usage

  case object Unisex extends Usage

  case object Equivalent extends Usage

}
