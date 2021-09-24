package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait CharacterEncodingsParser extends (TraversableOnce[DictionaryLine] => Seq[CharacterEncoding])
object CharacterEncodingsParser {

  def apply(): CharacterEncodingsParser = {
    lines =>
      lines
        .toIndexedSeq
        .map(_.toString)
        .map(_.tail.init)
        .map(_.replaceAll("""\/\*\*.*""", ""))
        .map(_.trim().split('='))
        .flatMap {

          case Array(character, encodings) =>
            encodings
              .split("or")
              .map(_.trim())
              .filter(_.nonEmpty)
              .map(CharacterEncoding(_, character.trim().toInt.toChar.toString))

          /** @todo Handle errors properly. */
          case _ =>
            ???

        }
  }

}
