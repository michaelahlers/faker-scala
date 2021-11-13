package ahlers.faker.datasets.heise

import scala.util.matching.Regex
import Template.Literal
import Template.Equivalent
import Template.Hyphenated

/**
 * @since October 02, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] trait TemplateEntryParser extends (TemplateLine => TemplateEntry)
private[heise] object TemplateEntryParser {

  private val EquivalentPattern: Regex =
    """^(\w+)=(\w+)$""".r

  private val HyphenatedPattern: Regex =
    """^(\w+)\+(\w+)$""".r

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
