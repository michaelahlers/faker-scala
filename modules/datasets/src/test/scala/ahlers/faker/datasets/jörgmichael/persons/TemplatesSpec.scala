package ahlers.faker.datasets.jörgmichael.persons

import com.softwaremill.diffx.generic.AutoDerivation
import com.softwaremill.diffx.scalatest.DiffShouldMatcher._
import org.scalatest.Inside._
import org.scalatest.LoneElement._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class TemplatesSpec extends AnyWordSpec with AutoDerivation {

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
              .shouldMatchTo(Usage.Equivalent: Usage)

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
      .shouldMatchTo(TemplateEntry(
        index = TemplateIndex(85),
        template = Hyphenated(Part("Abdel"), Part("Hafiz"))
      ))

    templates
      .entries(45640)
      .shouldMatchTo(TemplateEntry(
        index = TemplateIndex(45640),
        template = Literal("Ådne")
      ))

    templates
      .entries(170)
      .shouldMatchTo(TemplateEntry(
        index = TemplateIndex(170),
        template = Equivalent(Literal("Abe"), Literal("Abraham"))
      ))

    templates
      .entries(18740)
      .shouldMatchTo(TemplateEntry(
        index = TemplateIndex(18740),
        template = Literal("Jane")
      ))

    templates
      .entries(25480)
      .shouldMatchTo(TemplateEntry(
        index = TemplateIndex(25480),
        template = Literal("Maria da Conceição")
      ))

    templates
      .entries(46101)
      .shouldMatchTo(TemplateEntry(
        index = TemplateIndex(46101),
        template = Literal("Žydronė")
      ))

  }

}
