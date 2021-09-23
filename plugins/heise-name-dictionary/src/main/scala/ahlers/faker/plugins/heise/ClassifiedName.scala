package ahlers.faker.plugins.heise

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait ClassifiedName
object ClassifiedName {

  case class Equivalent(
    short: String,
    long: String,
    probabilityByLocale: Map[Region, Int])
    extends ClassifiedName

  case class Gendered(
    gender: String,
    variations: Seq[String],
    probabilityByLocale: Map[Region, Int])
    extends ClassifiedName

}
