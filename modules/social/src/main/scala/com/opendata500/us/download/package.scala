package com.opendata500.us

import ahlers.faker.social._
import com.opendata500.kantan._
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
package object download {

  val usCompanies: IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("com/opendata500/us/download/us_companies.csv")
      .asCsvReader[Company](rfc.withHeader)
      .toIndexedSeq
      .flatMap(_.toOption)

}
