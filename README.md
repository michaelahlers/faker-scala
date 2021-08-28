# Faker: Scala [![TravisCI][travis-ci-status-badge]][travis-ci-build] [![Appveyor][appveyor-status-badge]][appveyor-build] [![Codacy][codacy-status-badge]][codacy-build] [![Scala Steward][scala-steward-status-badge]][scala-steward-overview]

Realistic sample value generators for Scala.

## Status

**This project's a work-in-progress**, and it's settling on design patterns and essential datasets to provide. **Until those are stabilized**, this project will _not_ publish artifacts or accept pull-requests.

## Datasets

### Companies

Various parts (_e.g._, name, homepage, locality) of businesses from these sources:

- [Open Data 500 KR](https://www.opendata500.com/kr/)
- [Open Data 500 US](https://www.opendata500.com/us/)

### Emails

Made available as component parts (local, domain, and comment) with strict validation according to applicable IETF standards (_e.g._, [RFC 5322](https://tools.ietf.org/html/rfc5322)). Synthesized using this library's person name and company generators.

### Person Names

As name prefix, given name, middle name, family name, nickname, and name suffix from these sources:

- [United States Census Bureau: Frequently Occurring Surnames from Census 1990](https://www.census.gov/topics/population/genealogy/data/1990_census/1990_census_namefiles.html)
- [United States Census Bureau: Frequently Occurring Surnames from the Census 2000](https://census.gov/topics/population/genealogy/data/2000_surnames.html)
- [Magazin für Computertechnik: 40 000 Namen](https://www.heise.de/ct/ftp/07/17/182/)

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

This project's working to keep transitive dependencies at a minimum, and includes the following:

- [kantan.csv](https://github.com/nrinaudo/kantan.csv)
- [NewType](https://github.com/estatico/scala-newtype)
- [refined](https://github.com/fthomas/refined)

## Platforms

This library and tools are fully-tested on and supported for these Java versions and operating systems:

| JDK | macOS | Linux | Windows |
| ---: | :---: | :---: | :---: |
| **1.8** | :grey_question: | :white_check_mark: | :white_check_mark: | 
| **9** | :grey_question: | :grey_question: | :grey_question: | 
| **11** | :grey_question: | :grey_question: | :grey_question: | 
| **12** | :white_check_mark: | :white_check_mark: | :white_check_mark: |
| **13** | :grey_question: | :grey_question: | :grey_question: | 
| **14** | :white_check_mark: | :white_check_mark: | :white_check_mark: |

[appveyor-build]: https://ci.appveyor.com/project/michaelahlers/faker-scala
[appveyor-status-badge]: https://ci.appveyor.com/api/projects/status/erw16d62o5erwy95/branch/v0.0.x?svg=true

[codacy-build]: https://codacy.com/app/michaelahlers/faker-scala
[codacy-status-badge]: https://api.codacy.com/project/badge/Grade/73e169149c3d49c6b4b3c8f1e8c65dc1?branch=v0.0.x

[scala-steward-status-badge]: https://img.shields.io/badge/Scala_Steward-helping-blue.svg
[scala-steward-overview]: https://scala-steward.org

[travis-ci-build]: https://www.travis-ci.com/michaelahlers/faker-scala
[travis-ci-status-badge]: https://www.travis-ci.com/michaelahlers/faker-scala.svg?branch=v0.0.x
