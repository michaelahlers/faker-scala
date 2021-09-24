package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait RegionProbabilityParser extends (DictionaryLine => Seq[RegionProbability])
object RegionProbabilityParser {

  def apply(regionIndexes: TraversableOnce[RegionIndex]): RegionProbabilityParser = {
    val localIndexes =
      regionIndexes
        .toIndexedSeq

    line =>
      localIndexes
        .flatMap { case RegionIndex(region, index) =>
          Option(line
            .toString
            .charAt(index + 30)
            .toString
            .trim())
            .filter(_.nonEmpty)
            .map(Integer.parseInt(_, 16))
            .map { probability =>
              RegionProbability(region, probability)
            }
        }
  }

}
