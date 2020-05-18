package ahlers.faker.datasets.heise

import ahlers.faker.models._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 14, 2020
 */
class GivenNamesLoaderSpec extends AnyWordSpec {

  "Character encodings" in {
    GivenNamesLoader.characterEncodings.size should be(79)

    GivenNamesLoader.characterEncodings(0).should(matchTo(CharacterEncoding("<A/>", "Ā")))

    GivenNamesLoader.characterEncodings(61).should(matchTo(CharacterEncoding("\u009A", "š")))
    GivenNamesLoader.characterEncodings(62).should(matchTo(CharacterEncoding("<s^>", "š")))
    GivenNamesLoader.characterEncodings(63).should(matchTo(CharacterEncoding("<sch>", "š")))
    GivenNamesLoader.characterEncodings(64).should(matchTo(CharacterEncoding("<sh>", "š")))

    GivenNamesLoader.characterEncodings(78).should(matchTo(CharacterEncoding("<ß>", "ẞ")))
  }

  "Locale by index" in {
    import Locales._

    GivenNamesLoader.localeByIndex.size should be(55)

    GivenNamesLoader.localeByIndex(0).should(be(`Great Britain`))
    GivenNamesLoader.localeByIndex(20).should(be(Estonia))
    GivenNamesLoader.localeByIndex(54).should(be(Other))
  }

  "Classified names" in {
    import Genders._
    import Locales._

    GivenNamesLoader.classifiedNames.size should be(48528)

    GivenNamesLoader.classifiedNames(0).should(
      matchTo(GenderedName(Male, PersonGivenName("Aad"))
        .withProbabilities(Netherlands -> LocaleProbability(0x4)): ClassifiedName))

    GivenNamesLoader.classifiedNames(3).should(
      matchTo(GenderedName(Male, PersonGivenName("Ådne"))
        .withProbabilities(Norway -> LocaleProbability(0x1)): ClassifiedName))

    GivenNamesLoader.classifiedNames(95).should(
      matchTo(GenderedName(Male, PersonGivenName("Abdel"), PersonGivenName("Hafiz"))
        .withProbabilities(`Arabia/Persia` -> LocaleProbability(0x2)): ClassifiedName))

    GivenNamesLoader.classifiedNames(186).should(
      matchTo(EquivalentNames(PersonGivenName("Abe"), PersonGivenName("Abraham"))
        .withProbabilities(`United States` -> LocaleProbability(0x1)): ClassifiedName))

    GivenNamesLoader.classifiedNames(19982).should(
      matchTo(
        GenderedName(Female, PersonGivenName("Jane"))
          .withProbabilities(
            `Great Britain` -> LocaleProbability(0x7),
            Ireland -> LocaleProbability(0x6),
            `United States` -> LocaleProbability(0x6),
            Malta -> LocaleProbability(0x6),
            Belgium -> LocaleProbability(0x1),
            Netherlands -> LocaleProbability(0x2),
            Austria -> LocaleProbability(0x2),
            Swiss -> LocaleProbability(0x3),
            Denmark -> LocaleProbability(0x7),
            Norway -> LocaleProbability(0x3),
            Sweden -> LocaleProbability(0x3),
            Estonia -> LocaleProbability(0x6)
          ): ClassifiedName))

    GivenNamesLoader.classifiedNames(27155).should(
      matchTo(GenderedName(Female, PersonGivenName("Maria da Conceição"))
        .withProbabilities(Portugal -> LocaleProbability(0x3)): ClassifiedName))

    GivenNamesLoader.classifiedNames(48173).should(
      matchTo(
        GenderedName(Female, PersonGivenName("Zina"))
          .withProbabilities(
            `United States` -> LocaleProbability(0x1),
            Italy -> LocaleProbability(0x1),
            Lithuania -> LocaleProbability(0x1),
            `Czech Republic` -> LocaleProbability(0x1),
            Romania -> LocaleProbability(0x3),
            Bulgaria -> LocaleProbability(0x1),
            `Bosnia/Herzegovina` -> LocaleProbability(0x1),
            Albania -> LocaleProbability(0x1),
            Greece -> LocaleProbability(0x1),
            Belarus -> LocaleProbability(0x2),
            Moldova -> LocaleProbability(0x3),
            Armenia -> LocaleProbability(0x2),
            `Kazakhstan/Uzbekistan` -> LocaleProbability(0x2),
            `Arabia/Persia` -> LocaleProbability(0x4)
          ): ClassifiedName))

    GivenNamesLoader.classifiedNames(48507).should(
      matchTo(GenderedName(Female, PersonGivenName("Žydronė"))
        .withProbabilities(Lithuania -> LocaleProbability(0x1)): ClassifiedName))

    GivenNamesLoader.classifiedNames(48508).should(
      matchTo(GenderedName(Male, PersonGivenName("Žydrūnas"))
        .withProbabilities(Lithuania -> LocaleProbability(0x5)): ClassifiedName))

    GivenNamesLoader.classifiedNames(48527).should(
      matchTo(GenderedName(Female, PersonGivenName("Zyta"))
        .withProbabilities(Poland -> LocaleProbability(0x2)): ClassifiedName))
  }

}
