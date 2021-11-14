package ahlers.faker.plugins

import java.net.URL
import java.net.URLStreamHandler

/**
 * Supports opening connections with `classpath` as a URL scheme. Adapted to Scala from [[https://stackoverflow.com/a/1769454 an answer in Java on Stack Overflow]]. Licensed to the public domain by its original author.
 * @see [[https://stackoverflow.com/q/861500 URL to load resources from the classpath in Java]]
 * @see [[https://stackoverflow.com/users/37193/stephen]]
 * @since November 20, 2009
 */
object ClassPathStreamHandler extends URLStreamHandler {
  override def openConnection(location: URL) =
    getClass
      .getClassLoader
      .getResource(location.getPath)
      .openConnection()
}
