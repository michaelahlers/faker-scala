package heise.ct

import ahlers.faker.samples.PersonGivenName
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 14, 2020
 */
class DictionarySpec extends AnyWordSpec {

  "Encodings" in {
    Dictionary.encodings.size should be(79)

    Dictionary.encodings(0).should(matchTo(CharacterEncoding("<A/>", "Ā")))

    Dictionary.encodings(61).should(matchTo(CharacterEncoding("\u009A", "š")))
    Dictionary.encodings(62).should(matchTo(CharacterEncoding("<s^>", "š")))
    Dictionary.encodings(63).should(matchTo(CharacterEncoding("<sch>", "š")))
    Dictionary.encodings(64).should(matchTo(CharacterEncoding("<sh>", "š")))

    Dictionary.encodings(78).should(matchTo(CharacterEncoding("<ß>", "ẞ")))
  }

  "Locales" in {
    import Locales._

    Dictionary.localeDefinitions.size should be(55)

    Dictionary.localeDefinitions(0).should(matchTo(LocaleDefinition(LocaleIndex(0), `Great Britain`)))
    Dictionary.localeDefinitions(20).should(matchTo(LocaleDefinition(LocaleIndex(20), Estonia)))
    Dictionary.localeDefinitions(54).should(matchTo(LocaleDefinition(LocaleIndex(54), Other)))
  }

  "Names" in {
    import Genders._
    import Locales._

    Dictionary.names.size should be(48528)

    Dictionary.names(0).should(
      matchTo(GenderedName(Male, PersonGivenName("Aad"))
        .withClassifications(Netherlands -> GivenNameProbability(0x4)): ClassifiedName))

    Dictionary.names(3).should(
      matchTo(GenderedName(Male, PersonGivenName("Ådne"))
        .withClassifications(Norway -> GivenNameProbability(0x1)): ClassifiedName))

    Dictionary.names(95).should(
      matchTo(GenderedName(Male, PersonGivenName("Abdel"), PersonGivenName("Hafiz"))
        .withClassifications(`Arabia/Persia` -> GivenNameProbability(0x2)): ClassifiedName))

    Dictionary.names(186).should(
      matchTo(EquivalentNames(PersonGivenName("Abe"), PersonGivenName("Abraham"))
        .withClassifications(`United States` -> GivenNameProbability(0x1)): ClassifiedName))

    Dictionary.names(19982).should(
      matchTo(
        GenderedName(Female, PersonGivenName("Jane"))
          .withClassifications(
            `Great Britain` -> GivenNameProbability(0x7),
            Ireland -> GivenNameProbability(0x6),
            `United States` -> GivenNameProbability(0x6),
            Malta -> GivenNameProbability(0x6),
            Belgium -> GivenNameProbability(0x1),
            Netherlands -> GivenNameProbability(0x2),
            Austria -> GivenNameProbability(0x2),
            Swiss -> GivenNameProbability(0x3),
            Denmark -> GivenNameProbability(0x7),
            Norway -> GivenNameProbability(0x3),
            Sweden -> GivenNameProbability(0x3),
            Estonia -> GivenNameProbability(0x6)
          ): ClassifiedName))

    Dictionary.names(27155).should(
      matchTo(GenderedName(Female, PersonGivenName("Maria da Conceição"))
        .withClassifications(Portugal -> GivenNameProbability(0x3)): ClassifiedName))

    Dictionary.names(48173).should(
      matchTo(
        GenderedName(Female, PersonGivenName("Zina"))
          .withClassifications(
            `United States` -> GivenNameProbability(0x1),
            Italy -> GivenNameProbability(0x1),
            Lithuania -> GivenNameProbability(0x1),
            `Czech Republic` -> GivenNameProbability(0x1),
            Romania -> GivenNameProbability(0x3),
            Bulgaria -> GivenNameProbability(0x1),
            `Bosnia/Herzegovina` -> GivenNameProbability(0x1),
            Albania -> GivenNameProbability(0x1),
            Greece -> GivenNameProbability(0x1),
            Belarus -> GivenNameProbability(0x2),
            Moldova -> GivenNameProbability(0x3),
            Armenia -> GivenNameProbability(0x2),
            `Kazakhstan/Uzbekistan` -> GivenNameProbability(0x2),
            `Arabia/Persia` -> GivenNameProbability(0x4)
          ): ClassifiedName))

    Dictionary.names(48507).should(
      matchTo(GenderedName(Female, PersonGivenName("Žydronė"))
        .withClassifications(Lithuania -> GivenNameProbability(0x1)): ClassifiedName))

    Dictionary.names(48508).should(
      matchTo(GenderedName(Male, PersonGivenName("Žydrūnas"))
        .withClassifications(Lithuania -> GivenNameProbability(0x5)): ClassifiedName))

    Dictionary.names(48527).should(
      matchTo(GenderedName(Female, PersonGivenName("Zyta"))
        .withClassifications(Poland -> GivenNameProbability(0x2)): ClassifiedName))
  }

}
