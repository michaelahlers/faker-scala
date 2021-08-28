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
      case (file, _) =>
        val files =
          IO.unzip(
            from =
              file
                .toFile,
            toDirectory =
              (Compile / resourceManaged)
                .value
                .toPath
                .resolve(file
                  .getParent
                  .drop((Compile / resourceDirectory)
                    .value
                    .toPath
                    .size)
                  .reduce(_.resolve(_)))
                .toFile
          )

        sourceDirectory.value.toPath

        log.info("""Extracted %d files from archive "%s"."""
          .format(
            files.size,
            file
              .drop(baseDirectory.value
                .toPath
                .size)
              .reduce(_.resolve(_))
          ))

        files
    }
}

Compile / resourceGenerators += unpackZipArchives
