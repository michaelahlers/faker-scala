package heise.ct

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 14, 2020
 */
class NameDictionarySpec extends AnyWordSpec {

  "Encodings" in {
    NameDictionary.encodings.size should be(79)
  }

  "Locales" in {
    NameDictionary.locales.size should be(55)
  }

}
