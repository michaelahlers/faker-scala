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
    Dictionary.localeDefinitions.size should be(55)

    Dictionary.localeDefinitions(0).should(matchTo(LocaleDefinition(LocaleIndex(0), Locales.`Great Britain`)))
    Dictionary.localeDefinitions(20).should(matchTo(LocaleDefinition(LocaleIndex(20), Locales.Estonia)))
    Dictionary.localeDefinitions(54).should(matchTo(LocaleDefinition(LocaleIndex(54), Locales.Other)))
  }

  "Names" in {
    Dictionary.names.size should be(48528)

    Dictionary.names(0).should(
      matchTo(Name(PersonGivenName("Aad"))
        .withClassifications(Locales.Netherlands -> GivenNameProbability(0x4)): ClassifiedName))

    Dictionary.names(3).should(
      matchTo(Name(PersonGivenName("Ådne"))
        .withClassifications(Locales.Norway -> GivenNameProbability(0x1)): ClassifiedName))

    Dictionary.names(95).should(
      matchTo(Name(PersonGivenName("Abdel"), PersonGivenName("Hafiz"))
        .withClassifications(Locales.`Arabia/Persia` -> GivenNameProbability(0x2)): ClassifiedName))

    Dictionary.names(186).should(
      matchTo(EquivalentNames(PersonGivenName("Abe"), PersonGivenName("Abraham"))
        .withClassifications(Locales.`United States` -> GivenNameProbability(0x1)): ClassifiedName))

    Dictionary.names(19982).should(
      matchTo(
        Name(PersonGivenName("Jane"))
          .withClassifications(
            Locales.`Great Britain` -> GivenNameProbability(0x7),
            Locales.Ireland -> GivenNameProbability(0x6),
            Locales.`United States` -> GivenNameProbability(0x6),
            Locales.Malta -> GivenNameProbability(0x6),
            Locales.Belgium -> GivenNameProbability(0x1),
            Locales.Netherlands -> GivenNameProbability(0x2),
            Locales.Austria -> GivenNameProbability(0x2),
            Locales.Swiss -> GivenNameProbability(0x3),
            Locales.Denmark -> GivenNameProbability(0x7),
            Locales.Norway -> GivenNameProbability(0x3),
            Locales.Sweden -> GivenNameProbability(0x3),
            Locales.Estonia -> GivenNameProbability(0x6)
          ): ClassifiedName))

    Dictionary.names(27155).should(
      matchTo(Name(PersonGivenName("Maria da Conceição"))
        .withClassifications(Locales.Portugal -> GivenNameProbability(0x3)): ClassifiedName))

    Dictionary.names(48173).should(
      matchTo(
        Name(PersonGivenName("Zina"))
          .withClassifications(
            Locales.`United States` -> GivenNameProbability(0x1),
            Locales.Italy -> GivenNameProbability(0x1),
            Locales.Lithuania -> GivenNameProbability(0x1),
            Locales.`Czech Republic` -> GivenNameProbability(0x1),
            Locales.Romania -> GivenNameProbability(0x3),
            Locales.Bulgaria -> GivenNameProbability(0x1),
            Locales.`Bosnia/Herzegovina` -> GivenNameProbability(0x1),
            Locales.Albania -> GivenNameProbability(0x1),
            Locales.Greece -> GivenNameProbability(0x1),
            Locales.Belarus -> GivenNameProbability(0x2),
            Locales.Moldova -> GivenNameProbability(0x3),
            Locales.Armenia -> GivenNameProbability(0x2),
            Locales.`Kazakhstan/Uzbekistan` -> GivenNameProbability(0x2),
            Locales.`Arabia/Persia` -> GivenNameProbability(0x4)
          ): ClassifiedName))

    Dictionary.names(48507).should(
      matchTo(Name(PersonGivenName("Žydronė"))
        .withClassifications(Locales.Lithuania -> GivenNameProbability(0x1)): ClassifiedName))

    Dictionary.names(48508).should(
      matchTo(Name(PersonGivenName("Žydrūnas"))
        .withClassifications(Locales.Lithuania -> GivenNameProbability(0x5)): ClassifiedName))

    Dictionary.names(48527).should(
      matchTo(Name(PersonGivenName("Zyta"))
        .withClassifications(Locales.Poland -> GivenNameProbability(0x2)): ClassifiedName))
  }

}
