package ahlers.faker.plugins.uscensus1990

/**
 * @since October 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait Usage
object Usage {

  case object Last extends Usage
  case object FemaleFirst extends Usage
  case object MaleFirst extends Usage

}
