package ahlers.faker.datasets.heise

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait UsageParser extends (String => Usage)
private[heise] object UsageParser {

  import Usage._

  val default: UsageParser = {
    case "M" => Gender.Male
    case "1M" => Gender.FirstMale
    case "?M" => Gender.MostlyMale

    case "F" => Gender.Female
    case "1F" => Gender.FirstFemale
    case "?F" => Gender.MostlyFemale

    case "?" => Gender.Unisex

    case "=" => Equivalent
  }
}
