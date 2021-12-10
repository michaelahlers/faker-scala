package ahlers.faker.plugins.heise.persons

import scala.util.control.NoStackTrace
import scala.util.control.NonFatal

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait WeightDecoder extends (String => Weight)
object WeightDecoder {

  case class InvalidWeightException(token: String, cause: Throwable)
    extends Exception(s"""Invalid weight token "$token".""", cause)
      with NoStackTrace

  def apply(): WeightDecoder =
    token =>
      try Weight(Integer.parseInt(token, 16))
      catch {
        case NonFatal(cause) =>
          throw InvalidWeightException(token, cause)
      }

}
