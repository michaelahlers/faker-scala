package ahlers.faker.plugins.heise

import better.files.Dispose
import better.files.File
import better.files.Resource

import java.io.InputStream
import java.nio.file.Path
import java.util.zip.ZipInputStream
import scala.io.Codec
import scala.io.Source
import better.files._
import scala.util.Try

/**
 * @since September 17, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class ClassifiedName(
  gender: String,
  parts: Seq[String])
