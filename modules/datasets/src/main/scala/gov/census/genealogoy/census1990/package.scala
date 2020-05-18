package gov.census.genealogoy

import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
package object census1990 {

  type NameRankType = Int Refined Positive
  @newtype case class NameRank(toIht: NameRankType)

}
