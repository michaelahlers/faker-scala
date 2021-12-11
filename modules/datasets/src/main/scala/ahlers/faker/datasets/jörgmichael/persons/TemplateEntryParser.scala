package ahlers.faker.datasets.jörgmichael.persons

import ahlers.faker.datasets.jörgmichael.persons.Template.Equivalent
import ahlers.faker.datasets.jörgmichael.persons.Template.Hyphenated
import ahlers.faker.datasets.jörgmichael.persons.Template.Literal

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[jörgmichael] trait TemplateEntryParser extends (TemplateLine => TemplateEntry)
private[jörgmichael] object TemplateEntryParser {

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
