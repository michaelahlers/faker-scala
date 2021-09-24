package ahlers.faker.plugins.heise

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait ClassifiedName
object ClassifiedName {

  case class Equivalent(
    short: Name,
    long: Name,
    regionProbabilities: Seq[RegionProbability])
    extends ClassifiedName

  case class Gendered(
    gender: Gender,
    variations: Seq[Name],
    regionProbabilities: Seq[RegionProbability])
    extends ClassifiedName

}
