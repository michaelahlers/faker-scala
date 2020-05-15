package heise.ct

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 15, 2020
 */
case class CharacterEncoding(pattern: String, substitution: Char)
object CharacterEncoding {
  implicit class Ops(val encoding: CharacterEncoding) extends AnyVal {
    import encoding._
    def replace(x: String): String =
      x.replaceAllLiterally(pattern, substitution.toString)
  }
}
