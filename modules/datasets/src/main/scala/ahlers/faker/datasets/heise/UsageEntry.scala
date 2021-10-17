package ahlers.faker.datasets.heise

/**
 * @since October 04, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
private[heise] case class UsageEntry(
  index: UsageIndex,
  template: TemplateIndex,
  usage: Usage)
