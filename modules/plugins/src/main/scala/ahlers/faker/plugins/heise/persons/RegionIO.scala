package ahlers.faker.plugins.heise.persons

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import sbt.util.Logger

/**
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait RegionIO {
  def loadRegions(): Seq[Region]
}

object RegionIO {

  def apply(logger: Logger): RegionIO =
    new RegionIO {
      override def loadRegions() = {
        val regions =
          ConfigFactory
            .load(getClass.getClassLoader)
            .as[Seq[Region]]("regions")

        logger.info("Loaded %d regions, defining %d ISO country codes, from configuration."
          .format(
            regions.size,
            regions.flatMap(_.countryCodes).size))

        regions
      }
    }

}
