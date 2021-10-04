package ahlers.faker.plugins.heise

/**
 * @since September 25, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait DictionaryEntryParser extends (DictionaryLine => DictionaryEntry)
object DictionaryEntryParser {

  def using(
    decodeUsage: UsageDecoder,
    decodeName: TemplateDecoder,
    parseRegionWeights: RegionWeightsParser
  ): DictionaryEntryParser = {
    line =>
      val usage: Usage =
        decodeUsage(line
          .toText
          .take(2)
          .trim())

      val regionWeights: Seq[RegionWeight] =
        parseRegionWeights(line)

      val encodedName: String =
        line
          .toText
          .slice(3, 29)
          .trim()

      val decodedName: Template =
        decodeName(encodedName)

      val normalizedName: Template =
        usage match {

          case Usage.Equivalent =>
            Template(decodedName
              .toText
              .replace(' ', '='))

          case _ =>
            decodedName

        }

      usage match {
        case Usage.Equivalent =>
          require(1 == normalizedName.toText.count('=' == _), s"Expected exactly one '=' character in $normalizedName normalized from $decodedName with usage $usage.")
          require(!normalizedName.toText.contains(' '), s"Expected no space characters in $normalizedName normalized from $decodedName with usage $usage.")
        case _ =>
          require(!normalizedName.toText.contains('='), s"Expected no '=' characters $normalizedName normalized from $decodedName with usage $usage.")
      }

      DictionaryEntry(
        usage = usage,
        template = decodedName,
        regionWeights = regionWeights)
  }

}
