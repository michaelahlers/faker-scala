package ahlers.faker.datasets.census2000

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Usage
object Usage {
  case object Sur extends Usage
}
