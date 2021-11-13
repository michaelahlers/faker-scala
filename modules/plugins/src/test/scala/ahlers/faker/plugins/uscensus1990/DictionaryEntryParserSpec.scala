package ahlers.faker.plugins.uscensus1990

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import com.softwaremill.diffx.generic.auto._

/**
 * @since November 13, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class DictionaryEntryParserSpec extends AnyWordSpec {

  "Parse entries" in {
    val lines = IndexedSeq(
      DictionaryLine("Alpha 0.1 0.1 1"),
      DictionaryLine("Bravo  0.2  0.3  2"),
      DictionaryLine("Charlie   0.3   0.6   3")
    )

    val parser = DictionaryEntryParser.using()

    lines
      .map(parser(Usage.FemaleFirst, _))
      .should(matchTo(Seq(
        DictionaryEntry(Usage.FemaleFirst, Name("Alpha"), Frequency(0.1f), CumulativeFrequency(0.1f), Rank(1)),
        DictionaryEntry(Usage.FemaleFirst, Name("Bravo"), Frequency(0.2f), CumulativeFrequency(0.3f), Rank(2)),
        DictionaryEntry(Usage.FemaleFirst, Name("Charlie"), Frequency(0.3f), CumulativeFrequency(0.6f), Rank(3))
      )))
  }

}
