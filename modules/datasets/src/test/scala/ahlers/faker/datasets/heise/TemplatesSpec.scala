package ahlers.faker.datasets.heise

import com.softwaremill.diffx.generic.auto._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.Inside._
import org.scalatest.LoneElement._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class TemplatesSpec extends AnyWordSpec {

  "Specify correct type" in {
    import Template.{ Equivalent, Hyphenated, Literal }

    templates
      .entries
      .foreach(inside(_) { case entry =>
        inside(entry.template) {
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

            usages
              .byTemplateIndex(entry.index)
              .loneElement
              .should(matchTo(Usage.Equivalent: Usage))

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

        }
      })
  }

  "Include known entries" in {
    import Template.{ Equivalent, Hyphenated }
    import Hyphenated.Part
    import Template.Literal

    templates
      .entries(85)
      .should(matchTo(TemplateEntry(
        index = TemplateIndex(85),
        template = Hyphenated(Part("Abdel"), Part("Hafiz"))
      )))

    templates
      .entries(45640)
      .should(matchTo(TemplateEntry(
        index = TemplateIndex(45640),
        template = Literal("Ådne")
      )))

    templates
      .entries(170)
      .should(matchTo(TemplateEntry(
        index = TemplateIndex(170),
        template = Equivalent(Literal("Abe"), Literal("Abraham"))
      )))

    templates
      .entries(18740)
      .should(matchTo(TemplateEntry(
        index = TemplateIndex(18740),
        template = Literal("Jane")
      )))

    templates
      .entries(25480)
      .should(matchTo(TemplateEntry(
        index = TemplateIndex(25480),
        template = Literal("Maria da Conceição")
      )))

    templates
      .entries(46101)
      .should(matchTo(TemplateEntry(
        index = TemplateIndex(46101),
        template = Literal("Žydronė")
      )))

  }

}

object TemplatesSpec {}
