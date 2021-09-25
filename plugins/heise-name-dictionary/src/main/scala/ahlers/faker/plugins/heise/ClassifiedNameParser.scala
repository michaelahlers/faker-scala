package ahlers.faker.plugins.heise

/**
 * @since September 25, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait ClassifiedNameParser extends (DictionaryLine => ClassifiedName)
object ClassifiedNameParser {

  def apply(
    decodeUsage: UsageDecoder,
    decodeName: NameDecoder,
    parseNames: NamesParser,
    parseRegionWeights: RegionWeightsParser
  ): ClassifiedNameParser = {
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

      val decodedName: Name =
        decodeName(encodedName)

      val names: Seq[Name] =
        parseNames(usage, decodedName)

      usage match {

        case gender: Gender =>
          ClassifiedName.WithGender(
            reference =
              ClassifiedNameReference(decodedName
                .toText),
            gender = gender,
            variations = names,
            regionWeights = regionWeights)

        case _ =>
          ClassifiedName.WithEquivalents(
            reference =
              ClassifiedNameReference(
                decodedName
                  .toText
                  .replaceAllLiterally(" ", "=")),
            equivalents =
              names
                .map(name =>
                  ClassifiedNameReference(name
                    .toText)),
            regionWeights =
              regionWeights
          )

      }

  }

}
