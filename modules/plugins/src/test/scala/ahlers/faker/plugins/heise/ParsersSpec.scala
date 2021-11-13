package ahlers.faker.plugins.heise

import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._
import org.scalatest.wordspec.FixtureAnyWordSpec
import org.scalatest.matchers.should.Matchers._
import better.files._

import java.nio.charset.StandardCharsets

/**
 * @since September 30, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class ParsersSpec extends FixtureAnyWordSpec {

  override type FixtureParam = IndexedSeq[DictionaryLine]
  override protected def withFixture(test: OneArgTest) = {
    val lines =
      Resource.my.getAsStream("nam_dict.txt")
        .lines(StandardCharsets.ISO_8859_1)
        .map(DictionaryLine(_))
        .toIndexedSeq

    withFixture(test.toNoArgTest(lines))
  }

  "Character encodings" in { lines =>
    val characterEncodings =
      CharacterEncodingsParser.default
        .apply(lines)

    characterEncodings
      .should(have(size(79)))

    characterEncodings(0)
      .should(matchTo(CharacterEncoding("<A/>", "Ā")))

    characterEncodings(61)
      .should(matchTo(CharacterEncoding("\u009A", "š")))

    characterEncodings(62)
      .should(matchTo(CharacterEncoding("<s^>", "š")))

    characterEncodings(63)
      .should(matchTo(CharacterEncoding("<sch>", "š")))

    characterEncodings(64)
      .should(matchTo(CharacterEncoding("<sh>", "š")))

    characterEncodings(78)
      .should(matchTo(CharacterEncoding("<ß>", "ẞ")))
  }

  "Region indexes" in { lines =>
    val regionIndexes =
      RegionIndexesParser.using(IndexedSeq.empty)
        .apply(lines)

    regionIndexes
      .should(have(size(55)))

    regionIndexes(0)
      .should(matchTo(RegionIndex(
        region = Region(RegionLabel("Great Britain"), Set.empty),
        index = 0
      )))

    regionIndexes(20)
      .should(matchTo(RegionIndex(
        region = Region(RegionLabel("Estonia"), Set.empty),
        index = 20
      )))

    regionIndexes(54)
      .should(matchTo(RegionIndex(
        region = Region(RegionLabel("other countries"), Set.empty),
        index = 54
      )))

  }

  "Entries" in { lines =>
    val entries =
      DictionaryEntriesReader.using(IndexedSeq.empty)
        .apply(lines)

    entries
      .should(have(size(48528)))

    entries(0)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Male,
          template = Template("Aad"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("the Netherlands")),
                weight = Weight(0x4)
              )))))

    entries(3)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Male,
          template = Template("Ådne"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Norway")),
                weight = Weight(0x1)
              )))))

    entries(95)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Male,
          template = Template("Abdel+Hafiz"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Arabia/Persia")),
                weight = Weight(0x2)
              )))))

    entries(186)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Equivalent,
          template = Template("Abe Abraham"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("U.S.A.")),
                weight = Weight(0x1)
              )))))

    entries(19982)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Female,
          template = Template("Jane"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Great Britain")),
                weight = Weight(0x7)
              ),
              RegionWeight(
                region = Region(RegionLabel("Ireland")),
                weight = Weight(0x6)
              ),
              RegionWeight(
                region = Region(RegionLabel("U.S.A.")),
                weight = Weight(0x6)
              ),
              RegionWeight(
                region = Region(RegionLabel("Malta")),
                weight = Weight(0x6)
              ),
              RegionWeight(
                region = Region(RegionLabel("Belgium")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("the Netherlands")),
                weight = Weight(0x2)
              ),
              RegionWeight(
                region = Region(RegionLabel("Austria")),
                weight = Weight(0x2)
              ),
              RegionWeight(
                region = Region(RegionLabel("Swiss")),
                weight = Weight(0x3)
              ),
              RegionWeight(
                region = Region(RegionLabel("Denmark")),
                weight = Weight(0x7)
              ),
              RegionWeight(
                region = Region(RegionLabel("Norway")),
                weight = Weight(0x3)
              ),
              RegionWeight(
                region = Region(RegionLabel("Sweden")),
                weight = Weight(0x3)
              ),
              RegionWeight(
                region = Region(RegionLabel("Estonia")),
                weight = Weight(0x6)
              )
            )
        )))

    entries(27155)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Female,
          template = Template("Maria da Conceição"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Portugal")),
                weight = Weight(0x3)
              )))))

    entries(48173)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Female,
          template = Template("Zina"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("U.S.A.")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Italy")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Lithuania")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Czech Republic")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Romania")),
                weight = Weight(0x3)
              ),
              RegionWeight(
                region = Region(RegionLabel("Bulgaria")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Bosnia and Herzegovina")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Albania")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Greece")),
                weight = Weight(0x1)
              ),
              RegionWeight(
                region = Region(RegionLabel("Belarus")),
                weight = Weight(0x2)
              ),
              RegionWeight(
                region = Region(RegionLabel("Moldova")),
                weight = Weight(0x3)
              ),
              RegionWeight(
                region = Region(RegionLabel("Armenia")),
                weight = Weight(0x2)
              ),
              RegionWeight(
                region = Region(RegionLabel("Kazakhstan/Uzbekistan,etc.")),
                weight = Weight(0x2)
              ),
              RegionWeight(
                region = Region(RegionLabel("Arabia/Persia")),
                weight = Weight(0x4)
              )
            )
        )))

    entries(48507)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Female,
          template = Template("Žydronė"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Lithuania")),
                weight = Weight(0x1)
              )))))

    entries(48508)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Male,
          template = Template("Žydrūnas"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Lithuania")),
                weight = Weight(0x5)
              )))))

    entries(48527)
      .should(matchTo(
        DictionaryEntry(
          usage = Usage.Female,
          template = Template("Zyta"),
          regionWeights =
            Seq(
              RegionWeight(
                region = Region(RegionLabel("Poland")),
                weight = Weight(0x2)
              )))))

  }

}
