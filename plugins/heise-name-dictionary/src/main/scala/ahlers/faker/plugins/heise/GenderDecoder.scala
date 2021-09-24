package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait GenderDecoder extends (String => Gender)
object GenderDecoder {
  import Gender._

  case class UnknownGenderException(label: String)
    extends Exception(s"""Unexpected gender label "$label".""")

  def apply(): GenderDecoder = {
    case "M" => Male
    case "1M" => FirstMale
    case "?M" => MostlyMale
    case "F" => Female
    case "1F" => FirstFemale
    case "?F" => MostlyFemale
    case "?" => Unisex
    case label => throw UnknownGenderException(label)
  }

}
