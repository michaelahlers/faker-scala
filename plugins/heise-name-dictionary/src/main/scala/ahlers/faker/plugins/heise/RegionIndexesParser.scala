package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait RegionIndexesParser extends (TraversableOnce[DictionaryLine] => Seq[RegionIndex])
object RegionIndexesParser {

  case class UnknownRegionLabelException(label: RegionLabel)
    extends Exception(s""""$label" wasn't known.""")

  def apply(regions: TraversableOnce[Region]): RegionIndexesParser = {
    val regionByLabel: Map[RegionLabel, Region] =
      regions
        .map(region => (region.label, region))
        .toMap
        .withDefault(label => throw UnknownRegionLabelException(label))

    lines =>
      lines
        .toIndexedSeq
        .sliding(2, 3)
        .map {

          case Seq(label, index) =>
            RegionIndex(
              region =
                regionByLabel(
                  RegionLabel(label
                    .toText
                    .tail
                    .init
                    .trim())),
              index =
                index
                  .toText
                  .indexOf('|') - 30)

          /** @todo Handle errors properly. */
          case _ =>
            ???

        }
        .toIndexedSeq
  }

}
