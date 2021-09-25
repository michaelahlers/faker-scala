package ahlers.faker.plugins.heise

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class ClassifiedName(
  reference: ClassifiedNameReference,
  usage: Usage,
  variations: Seq[Name],
  regionWeights: Seq[RegionWeight])
