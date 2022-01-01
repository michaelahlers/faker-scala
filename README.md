# Faker: Scala [![Appveyor][appveyor-status-badge]][appveyor-build] [![Codacy][codacy-status-badge]][codacy-build] [![Scala Steward][scala-steward-status-badge]][scala-steward-overview]

Realistic sample value generators for Scala.

## Status

**This project's a work-in-progress**, and it's settling on design patterns and essential datasets to provide. **Until those are stabilized**, this project will _not_ accept pull-requests.

## Artifacts

| Target | Scala 2.12 | Scala 2.13 | Scala 3 |
| ---: | :---: | :---: | :---: |
| **JVM** | :white_check_mark: | :white_check_mark: | :white_check_mark: |
| **JavaScript** | :grey_question: | :grey_question: | :grey_question: |

## Installation

### SBT

Add both this project's resolver and library dependencies.

Most projects will want to obtain the ScalaCheck `Gen` and `Arbitrary` instances:

```scala
resolvers += "Ahlers Consulting" at "https://artifacts.ahlers.consulting/maven2"
libraryDependencies += "ahlers.faker" %% "faker-scalatest" % "0.2.0-SNAPSHOT" % Test 
```

For those interested only in the data:

```scala
resolvers += "Ahlers Consulting" at "https://artifacts.ahlers.consulting/maven2"
libraryDependencies += "ahlers.faker" %% "faker-datasets" % "0.2.0-SNAPSHOT" % Test :: 
```

### Mill

_TBD._

### Gradle

_TBD._

## Platforms

This library tested on and supported on these platforms.

### Java

| JDK | macOS | Linux | Windows |
| ---: | :---: | :---: | :---: |
| **8** | :white_check_mark: | :white_check_mark: | :white_check_mark: | 
| **9** | :grey_question: | :grey_question: | :grey_question: | 
| **11** | :white_check_mark: | :white_check_mark: | :white_check_mark: |
| **12** | :grey_question: | :grey_question: | :grey_question: | 
| **13** | :grey_question: | :grey_question: | :grey_question: | 
| **14** | :grey_question: | :grey_question: | :grey_question: | 
| **15** | :white_check_mark: | :white_check_mark: | :white_check_mark: |

## Datasets

### Companies

Various parts (_e.g._, name, homepage, locality) of businesses from these sources:

- [Open Data 500 KR](https://www.opendata500.com/kr/)
- [Open Data 500 US](https://www.opendata500.com/us/)

### Emails

Made available as component parts (local, domain, and comment) with strict validation according to applicable IETF standards (_e.g._, [RFC 5322][RFC-5322]). Synthesized using this library's person name and company generators.

### Person Names

As name prefix, given name, middle name, family name, nickname, and name suffix from these sources:

- [United States Census Bureau: Frequently Occurring Surnames from Census 1990](https://www.census.gov/topics/population/genealogy/data/1990_census/1990_census_namefiles.html)
- [United States Census Bureau: Frequently Occurring Surnames from the Census 2000](https://census.gov/topics/population/genealogy/data/2000_surnames.html)
- [Magazin für Computertechnik: _40 000 Namen_](https://www.heise.de/ct/ftp/07/17/182/)

## Plugins

This module provides what are, essentially, small [ETL][wikipedia-ETL] pipelines with the aim of reducing complexity, payload, and dependencies in dataset modules, used by this same project to ship for consumers a set of concise, trivially-parsed formats.

**Most consumers of these libraries will not use this library of plugins**; the preprocessing is made available to document how upstream sources are obtained and modified, and make available additional output formats should consumers wish to repurpose their output. For example, power users might want to use different output formats (planned are JSON and YAML) in different ways.

Over time, this is expected to grow in to a larger and more elaborate corpus of sources and normalizations. These upstream sources often provide greater detail than is used by this library. Also, over time, greater detail will be made available in the output of generators.

## Inspiration

While arbitrary value generation is useful, testing often profits from realistic samples. This library aims to provide generators for email addresses, geographical locations, person names, phone numbers, universal resource identifiers (URI), and more.

It takes cues from these similar projects for other languages:

- [faker-ruby/faker](https://github.com/faker-ruby/faker)
- [Data::Faker](https://metacpan.org/pod/Data::Faker)

And means to fill gaps left by those for Scala which haven't seen recent activity:

- [dunnhumby/data-faker](https://github.com/dunnhumby/data-faker)
- [justwrote/scala-faker](https://github.com/justwrote/scala-faker)
- [therealadam/scala-faker](https://github.com/therealadam/scala-faker)

## Dependencies

Because this project preprocesses upstream sources, it's able to be largely free of extraneous dependencies in modules intended for end users. Confined to the [Plugins][#plugins] module, noteworthy dependencies include: 

- [kantan.csv](https://github.com/nrinaudo/kantan.csv)
- [refined](https://github.com/fthomas/refined)

## Acknowledgements

- [The answer](https://stackoverflow.com/a/24871136/700420) by [lpiepiora](https://stackoverflow.com/users/480975/lpiepiora) to [_Add plugins under a same project in sbt_](https://stackoverflow.com/q/24864296/700420) on Stack Overflow was helpful in factoring out resource generators and using them as plugins as part of this same project.
  - See also:
    - [_How to reference external sbt project from another sbt project?_](https://stackoverflow.com/q/11653435)
    - [_In sbt, how do you add a plugin that's in the local filesystem?_](https://stackoverflow.com/q/8568821)

[appveyor-build]: https://ci.appveyor.com/project/michaelahlers/faker-scala
[appveyor-status-badge]: https://ci.appveyor.com/api/projects/status/erw16d62o5erwy95/branch/v0.0.x?svg=true

[codacy-build]: https://codacy.com/app/michaelahlers/faker-scala
[codacy-status-badge]: https://api.codacy.com/project/badge/Grade/73e169149c3d49c6b4b3c8f1e8c65dc1?branch=v0.0.x

[RFC-5322]: https://tools.ietf.org/html/rfc5322

[scala-steward-status-badge]: https://img.shields.io/badge/Scala_Steward-helping-blue.svg
[scala-steward-overview]: https://scala-steward.org

[wikipedia-ETL]: https://en.wikipedia.org/wiki/Extract,_transform,_load 
