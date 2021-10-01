package ahlers.faker.plugins.heise

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait NameDecoder extends (String => Name)
object NameDecoder {

  def apply(characterEncodings: IndexedSeq[CharacterEncoding]): NameDecoder = {
    val prioritizedCharacterEncodings =
      characterEncodings
        .sortBy(_.pattern.length())
        .distinct

    encodedName =>
      val decodedName: String =
        prioritizedCharacterEncodings
          .foldLeft(encodedName) {
            case (decodedName, characterEncoding) =>
              decodedName
                .replace(
                  characterEncoding.pattern,
                  characterEncoding.substitution)
          }

      Name(decodedName)
  }

}
