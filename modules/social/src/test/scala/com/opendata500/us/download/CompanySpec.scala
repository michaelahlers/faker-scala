package com.opendata500.us.download

import org.scalatest.wordspec._
import org.scalatest.matchers.should.Matchers._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
class CompanySpec extends AnyWordSpec {

  "company values" must {
    "parse dataset" in {
      Company.values
        .collect {
          case Left(error) => error
        }
        .toIterable
        .size
        .should(be(0))

      Company.values
        .collect {
          case Right(company) => company
        }
        .toIterable
        .size
        .should(be(529))
    }
  }

}
