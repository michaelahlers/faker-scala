package ahlers.faker.datasets.heise

import org.scalatest.Inside._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class TemplatesSpec extends AnyWordSpec {

  "Specify correct type" in {
    import Template.Literal
    import Template.Equivalent
    import Template.Hyphenated

    templates
      .all
      .foreach(inside(_) {

        case template: Literal =>
          template
            .toText
            .should(not(include("+").or(include("="))))

        case template: Equivalent =>
          inside(template.short) { case template =>
            template
              .toText
              .should(not(include("+").or(include("="))))
          }

          inside(template.long) { case template =>
            template
              .toText
              .should(not(include("+").or(include("="))))
          }

        case template: Hyphenated =>
          inside(template.left) { case part =>
            part
              .toText
              .should(not(include("+").or(include("="))))
          }

          inside(template.right) { case part =>
            part
              .toText
              .should(not(include("+").or(include("="))))
          }

      })
  }

}

object TemplatesSpec {}
