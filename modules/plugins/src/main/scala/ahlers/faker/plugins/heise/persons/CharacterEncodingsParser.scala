package ahlers.faker.plugins.heise.persons

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait CharacterEncodingsParser extends (IndexedSeq[DictionaryLine] => Seq[CharacterEncoding])
object CharacterEncodingsParser {

  val default: CharacterEncodingsParser = {
    lines =>
      lines
        .dropWhile(!_.toText.contains("char set"))
        .drop(6)
        .take(67)
        .map(_
          .toText
          .tail.init
          .replaceAll("""\/\*\*.*""", "")
          .trim()
          .split('='))
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
