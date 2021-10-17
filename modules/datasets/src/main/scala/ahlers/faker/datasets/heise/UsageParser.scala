package ahlers.faker.datasets.heise

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait UsageParser extends (String => Usage)
private[heise] object UsageParser {

  import Usage._

  val default: UsageParser = {
    case "Male" => Gender.Male
    case "FirstMale" => Gender.FirstMale
    case "MostlyMale" => Gender.MostlyMale

    case "Female" => Gender.Female
    case "FirstFemale" => Gender.FirstFemale
    case "MostlyFemale" => Gender.MostlyFemale

    case "Unisex" => Gender.Unisex

    case "Equivalent" => Equivalent
  }
}
