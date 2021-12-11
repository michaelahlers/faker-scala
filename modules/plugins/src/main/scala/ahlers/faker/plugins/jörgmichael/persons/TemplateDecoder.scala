package ahlers.faker.plugins.jörgmichael.persons

/**
 * @since September 22, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait TemplateDecoder extends (String => Template)
object TemplateDecoder {

  def apply(characterEncodings: IndexedSeq[CharacterEncoding]): TemplateDecoder = {
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

      Template(decodedName)
  }

}
