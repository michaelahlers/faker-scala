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
  }

  "Locales" in {
    Dictionary.localeDefinitions.size should be(55)

    Dictionary.localeDefinitions(0).should(matchTo(LocaleDefinition(LocaleIndex(0), LocaleName("Great Britain"))))
    Dictionary.localeDefinitions(20).should(matchTo(LocaleDefinition(LocaleIndex(20), LocaleName("Estonia"))))
    Dictionary.localeDefinitions(54).should(matchTo(LocaleDefinition(LocaleIndex(54), LocaleName("other countries"))))
  }

  "Names" in {
    Dictionary.names.size should be(48528)

    Dictionary.names(0).should(
      matchTo(Name(PersonGivenName("Aad"))
        .withClassifications(LocaleName("the Netherlands") -> GivenNameProbability(0x4))))

    Dictionary.names(3).should(
      matchTo(Name(PersonGivenName("Ådne"))
        .withClassifications(LocaleName("Norway") -> GivenNameProbability(0x1))))

    Dictionary.names(95).should(
      matchTo(Name(PersonGivenName("Abdel"), PersonGivenName("Hafiz"))
        .withClassifications(LocaleName("Arabia/Persia") -> GivenNameProbability(0x2))))

    Dictionary.names(186).should(
      matchTo(Name(PersonGivenName("Abe"), EquivalentName("Abraham"))
        .withClassifications(LocaleName("U.S.A.") -> GivenNameProbability(0x1))))

    Dictionary.names(19982).should(
      matchTo(
        Name(PersonGivenName("Jane"))
          .withClassifications(
            LocaleName("Great Britain") -> GivenNameProbability(0x7),
            LocaleName("Ireland") -> GivenNameProbability(0x6),
            LocaleName("U.S.A.") -> GivenNameProbability(0x6),
            LocaleName("Malta") -> GivenNameProbability(0x6),
            LocaleName("Belgium") -> GivenNameProbability(0x1),
            LocaleName("the Netherlands") -> GivenNameProbability(0x2),
            LocaleName("Austria") -> GivenNameProbability(0x2),
            LocaleName("Swiss") -> GivenNameProbability(0x3),
            LocaleName("Denmark") -> GivenNameProbability(0x7),
            LocaleName("Norway") -> GivenNameProbability(0x3),
            LocaleName("Sweden") -> GivenNameProbability(0x3),
            LocaleName("Estonia") -> GivenNameProbability(0x6)
          )))

    Dictionary.names(27155).should(
      matchTo(Name(PersonGivenName("Maria da Conceição"))
        .withClassifications(LocaleName("Portugal") -> GivenNameProbability(0x3))))

    Dictionary.names(48173).should(
      matchTo(
        Name(PersonGivenName("Zina"))
          .withClassifications(
            LocaleName("U.S.A.") -> GivenNameProbability(0x1),
            LocaleName("Italy") -> GivenNameProbability(0x1),
            LocaleName("Lithuania") -> GivenNameProbability(0x1),
            LocaleName("Czech Republic") -> GivenNameProbability(0x1),
            LocaleName("Romania") -> GivenNameProbability(0x3),
            LocaleName("Bulgaria") -> GivenNameProbability(0x1),
            LocaleName("Bosnia and Herzegovina") -> GivenNameProbability(0x1),
            LocaleName("Albania") -> GivenNameProbability(0x1),
            LocaleName("Greece") -> GivenNameProbability(0x1),
            LocaleName("Belarus") -> GivenNameProbability(0x2),
            LocaleName("Moldova") -> GivenNameProbability(0x3),
            LocaleName("Armenia") -> GivenNameProbability(0x2),
            LocaleName("Kazakhstan/Uzbekistan,etc.") -> GivenNameProbability(0x2),
            LocaleName("Arabia/Persia") -> GivenNameProbability(0x4)
          )))

    Dictionary.names(48507).should(
      matchTo(Name(PersonGivenName("Žydronė"))
        .withClassifications(LocaleName("Lithuania") -> GivenNameProbability(0x1))))

    Dictionary.names(48508).should(
      matchTo(Name(PersonGivenName("Žydrūnas"))
        .withClassifications(LocaleName("Lithuania") -> GivenNameProbability(0x5))))

    Dictionary.names(48527).should(
      matchTo(Name(PersonGivenName("Zyta"))
        .withClassifications(LocaleName("Poland") -> GivenNameProbability(0x2))))
  }

}
