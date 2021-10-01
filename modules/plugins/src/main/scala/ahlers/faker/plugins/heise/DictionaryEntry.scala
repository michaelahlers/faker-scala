package ahlers.faker.plugins.heise

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class DictionaryEntry(
  usage: Usage,
  template: Name,
  regionWeights: Seq[RegionWeight])
