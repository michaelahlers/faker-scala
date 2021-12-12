package ahlers.faker.plugins.jörgmichael.persons

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait RegionWeightsParser extends (DictionaryLine => Seq[RegionWeight])
object RegionWeightsParser {

  def using(regionIndexes: TraversableOnce[RegionIndex]): RegionWeightsParser = {
    val localIndexes =
      regionIndexes
        .toIndexedSeq

    line =>
      localIndexes
        .flatMap { case RegionIndex(region, index) =>
          Option(line
            .toText
            .charAt(index + 30)
            .toString
            .trim())
            .filter(_.nonEmpty)
            .map(Integer.parseInt(_, 16))
            .map { weight =>
              RegionWeight(region, Weight(weight))
            }
        }
  }

}
