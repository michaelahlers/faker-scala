package ahlers.faker.plugins.heise.persons

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait WeightDecoder extends (String => Weight)
object WeightDecoder {

  case class InvalidWeightException(token: String)
    extends Exception(s"""Invalid weight token "$token".""")

  def apply(): WeightDecoder =
    token =>
      Weight(Integer.parseInt(token, 16))

}
