package ahlers.faker.datasets.jörgmichael.persons

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[jörgmichael] trait UsageEntryParser extends (UsageLine => UsageEntry)
private[jörgmichael] object UsageEntryParser {

  def using(parseUsage: UsageParser): UsageEntryParser = line =>
    line
      .toText
      .split(',') match {

      case Array(templateIndex, usage) =>
        UsageEntry(
          index = UsageIndex(line.toInt),
          template = TemplateIndex(Integer.parseInt(templateIndex, 16)),
          usage = parseUsage(usage))

      /** @todo Proper error-handling. */
      case _ =>
        ???

    }

  val default =
    using(UsageParser.default)

}
