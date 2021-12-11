package ahlers.faker.plugins.heise.persons

/**
 * Names in this source aren't always finally usable. While character encodings are resolved, variants exist which can be expanded into different forms, ''e.g.'', equivalent names, hyphenated names, such `Abe=Abraham` denoting interchangeable ”Abe” and ”Abraham”, or `Jun+Wei` representing names ”Jun-Wei”, ”Jun Wei” and ”Junwei”. In order to keep the normalized data small, these few cases are expanded (at runtime) by the published dataset.
 *
 * @since September 23, 2021
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class Template(toText: String) extends AnyVal
