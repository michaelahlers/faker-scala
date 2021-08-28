package ahlers.faker.datasets.heise

import ahlers.faker.datasets.census1990.ClassifiedGivenName
import ahlers.faker._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._
import eu.timepit.refined.auto._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 14, 2020
 */
class ClassifiedNameIteratorSpec extends FixtureAnyWordSpec {

  override type FixtureParam = ClassifiedNameIterator
  override protected def withFixture(test: OneArgTest) = {
    val loader = ClassifiedNameIterator()
    withFixture(test.toNoArgTest(loader))
  }

  "Character encodings" in { loader =>
    loader.characterEncodings.size should be(79)

    loader.characterEncodings(0).should(matchTo(CharacterEncoding("<A/>", "Ā")))

    loader.characterEncodings(61).should(matchTo(CharacterEncoding("\u009A", "š")))
    loader.characterEncodings(62).should(matchTo(CharacterEncoding("<s^>", "š")))
    loader.characterEncodings(63).should(matchTo(CharacterEncoding("<sch>", "š")))
    loader.characterEncodings(64).should(matchTo(CharacterEncoding("<sh>", "š")))

    loader.characterEncodings(78).should(matchTo(CharacterEncoding("<ß>", "ẞ")))
  }

  "Locale by index" in { loader =>
    import Locales._

    loader.localeByIndex.size should be(55)

    loader.localeByIndex(0).should(be(`Great Britain`))
    loader.localeByIndex(20).should(be(Estonia))
    loader.localeByIndex(54).should(be(Other))
  }

  "Given names" in { loader =>
    import Genders._
    import Locales._

    val givenNames = loader.toIndexedSeq

    givenNames.size should be(48528)

    givenNames(0).should(
      matchTo(GenderedName(Male, PersonGivenName("Aad"))
        .withProbabilities(Netherlands -> LocaleProbability(0x4)): ClassifiedName))

    givenNames(3).should(
      matchTo(GenderedName(Male, PersonGivenName("Ådne"))
        .withProbabilities(Norway -> LocaleProbability(0x1)): ClassifiedName))

    givenNames(95).should(
      matchTo(GenderedName(Male, PersonGivenName("Abdel"), PersonGivenName("Hafiz"))
        .withProbabilities(`Arabia/Persia` -> LocaleProbability(0x2)): ClassifiedName))

    givenNames(186).should(
      matchTo(EquivalentNames(PersonGivenName("Abe"), PersonGivenName("Abraham"))
        .withProbabilities(`United States` -> LocaleProbability(0x1)): ClassifiedName))

    givenNames(19982).should(
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

    givenNames(27155).should(
      matchTo(GenderedName(Female, PersonGivenName("Maria da Conceição"))
        .withProbabilities(Portugal -> LocaleProbability(0x3)): ClassifiedName))

    givenNames(48173).should(
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

    givenNames(48507).should(
      matchTo(GenderedName(Female, PersonGivenName("Žydronė"))
        .withProbabilities(Lithuania -> LocaleProbability(0x1)): ClassifiedName))

    givenNames(48508).should(
      matchTo(GenderedName(Male, PersonGivenName("Žydrūnas"))
        .withProbabilities(Lithuania -> LocaleProbability(0x5)): ClassifiedName))

    givenNames(48527).should(
      matchTo(GenderedName(Female, PersonGivenName("Zyta"))
        .withProbabilities(Poland -> LocaleProbability(0x2)): ClassifiedName))

  }

}
