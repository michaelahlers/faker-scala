package ahlers.faker.plugins.heise

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait RegionIndexesParser extends (Iterator[DictionaryLine] => Seq[RegionIndex])
object RegionIndexesParser {

  def using(regions: IndexedSeq[Region]): RegionIndexesParser = {
    val regionByLabel: Map[RegionLabel, Region] =
      regions
        .map(region => (region.label, region))
        .toMap
        .withDefault(label =>
          Region(
            label = label,
            countryCodes = Set.empty))

    lines =>
      lines
        .dropWhile(!_.toText.contains("list of countries"))
        .drop(7)
        .take(164)
        .toIndexedSeq
        .sliding(2, 3)
        .toIndexedSeq
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
  }

}
