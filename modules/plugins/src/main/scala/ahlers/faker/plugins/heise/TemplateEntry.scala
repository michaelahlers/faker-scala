package ahlers.faker.plugins.heise

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class TemplateEntry(
  usage: Usage,
  template: Template,
  regionWeights: Seq[RegionWeight])
