package ahlers.faker.plugins.heise

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait NameDecoder extends (String => String)
object NameDecoder {

  def apply(characterEncodings: Seq[CharacterEncoding]): NameDecoder = {
    val shortToLong =
      characterEncodings
        .sortBy(_.pattern.length())
        .distinct

    (encodedName: String) =>
      shortToLong.foldLeft(encodedName) {
        case (decodedName, characterEncoding) =>
          decodedName
            .replace(
              characterEncoding.pattern,
              characterEncoding.substitution)
      }
  }

}
