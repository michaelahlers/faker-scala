package ahlers.faker.plugins.heise

/**
 * @since September 25, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait NamesParser extends ((Usage, Name) => Seq[Name])
object NamesParser {

  def apply(): NamesParser = {
    (usage, name) =>
      usage match {

        case Usage.Equivalent =>
          name
            .toText
            .split(' ')
            .map(Name(_))

        case _ =>
          name
            .toText
            .split('+') match {

            case Array(first, second) =>
              Array(
                s"$first $second",
                s"$first-$second",
                s"$first${second.toLowerCase}")
                .map(Name(_))

            case parts =>
              parts.map(Name(_))

          }
      }

  }

}
