import sbt.nio.file.FileTreeView

import scala.collection.convert.ImplicitConversionsToScala._

val unpackZipArchives: TaskKey[Seq[File]] = taskKey[Seq[File]]("Extract entries from source resource archives to managed resources.")

unpackZipArchives := {
  val log = streams.value.log

  FileTreeView.default
    .list((Compile / resourceDirectory)
      .value
      .toGlob / ** / "*.zip")
    .flatMap {
      case (fromFile, _) =>

        val toDirectory =
          (Compile / resourceManaged)
          .value
          .toPath
          .resolve(fromFile
            .getParent
            .drop((Compile / resourceDirectory)
              .value
              .toPath
              .size)
            .reduce(_.resolve(_)))

        val files =
          IO.unzip(
            from =              fromFile.toFile,
            toDirectory =              toDirectory.toFile
          )


        log.info("""Extracted %d files from archive "%s" to "%s"."""
          .format(
            files.size,

            fromFile
              .drop(baseDirectory.value
                .toPath
                .size)
              .reduce(_.resolve(_)),

            toDirectory
              .drop(baseDirectory.value
                .toPath
                .size)
              .reduce(_.resolve(_))
          ))

        files
    }
}

Compile / resourceGenerators += unpackZipArchives
