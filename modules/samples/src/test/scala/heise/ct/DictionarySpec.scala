package heise.ct

import cats.syntax.option._
import eu.timepit.refined.auto._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import com.softwaremill.diffx.scalatest.DiffMatcher._

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
  }

  "Names" in {
    Dictionary.names.size should be(48528)

    Dictionary.names(0).should(matchTo(Name(NamePart("Aad"))))

    Dictionary.names(3).should(matchTo(Name(NamePart("Ådne"))))

    Dictionary.names(95).should(matchTo(Name(NamePart("Abdel"), NamePart("Hafiz"))))

    Dictionary.names(186).should(matchTo(Name(NamePart("Abe"), EquivalentName("Abraham"))))

    Dictionary.names(27155).should(matchTo(Name(NamePart("Maria da Conceição"))))

    Dictionary.names(48527).should(matchTo(Name(NamePart("Zyta"))))
  }

}
