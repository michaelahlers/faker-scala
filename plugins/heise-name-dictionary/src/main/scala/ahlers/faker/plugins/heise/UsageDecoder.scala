package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait UsageDecoder extends (String => Usage)
object UsageDecoder {
  import Usage._

  case class UnknownUsageException(token: String)
    extends Exception(s"""Unexpected usage token "$token".""")

  def apply(): UsageDecoder = {
    case "M" => Male
    case "1M" => FirstMale
    case "?M" => MostlyMale
    case "F" => Female
    case "1F" => FirstFemale
    case "?F" => MostlyFemale
    case "?" => Unisex
    case "=" => Equivalent
    case token => throw UnknownUsageException(token)
  }

}
