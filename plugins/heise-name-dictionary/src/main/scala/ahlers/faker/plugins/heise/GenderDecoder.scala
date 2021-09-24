package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait GenderDecoder extends (String => Option[Gender])
object GenderDecoder {
  import Gender._

  case class UnknownGenderException(label: String)
    extends Exception(s"""Unexpected gender label "$label".""")

  def apply(): GenderDecoder = {
    case "M" => Some(Male)
    case "1M" => Some(FirstMale)
    case "?M" => Some(MostlyMale)
    case "F" => Some(Female)
    case "1F" => Some(FirstFemale)
    case "?F" => Some(MostlyFemale)
    case "?" => Some(Unisex)
    case "=" => None
    case label => throw UnknownGenderException(label)
  }

}
