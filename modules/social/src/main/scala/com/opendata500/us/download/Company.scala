package com.opendata500.us.download

import ahlers.faker.social._
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import kantan.csv.refined._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 11, 2020
 */
case class Company(id: CompanyId, name: CompanyName)
object Company {

  val values: IndexedSeq[Company] =
    Thread.currentThread()
      .getContextClassLoader()
      .getResourceAsStream("com/opendata500/us/download/us_companies.csv")
      .asUnsafeCsvReader[Company](rfc.withHeader)
      .toIndexedSeq

}
