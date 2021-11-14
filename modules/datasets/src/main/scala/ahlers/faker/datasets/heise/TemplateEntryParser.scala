package ahlers.faker.datasets.heise

import ahlers.faker.datasets.heise.Template.Equivalent
import ahlers.faker.datasets.heise.Template.Hyphenated
import ahlers.faker.datasets.heise.Template.Literal

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait TemplateEntryParser extends (TemplateLine => TemplateEntry)
private[heise] object TemplateEntryParser {

  private object EquivalentPattern {
    def unapply(fromText: String): Option[(String, String)] =
      fromText.split('=') match {
        case Array(short, long) => Some((short, long))
        case _ => None
      }
  }

  private object HyphenatedPattern {
    def unapply(fromText: String): Option[(String, String)] =
      fromText.split('+') match {
        case Array(short, long) => Some((short, long))
        case _ => None
      }
  }

  val default: TemplateEntryParser = line =>
    TemplateEntry(
      index = TemplateIndex(line.toInt),
      template =
        line.toText match {

          case EquivalentPattern(short, long) =>
            Equivalent(
              short = Literal(short),
              long = Literal(long))

          case HyphenatedPattern(left, right) =>
            import Hyphenated.Part
            Hyphenated(
              left = Part(left),
              right = Part(right))

          case literal =>
            Literal(literal)

        }
    )

}
