package ahlers.faker.plugins.heise

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait ClassifiedName {
  def reference: ClassifiedNameReference
}

object ClassifiedName {

  case class WithEquivalents(
    override val reference: ClassifiedNameReference,
    equivalents: Seq[ClassifiedNameReference],
    regionWeights: Seq[RegionWeight])
    extends ClassifiedName

  case class WithGender(
    override val reference: ClassifiedNameReference,
    gender: Gender,
    variations: Seq[Name],
    regionWeights: Seq[RegionWeight])
    extends ClassifiedName

}
