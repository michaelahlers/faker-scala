package ahlers.faker.datasets.heise

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 16, 2020
 */
sealed trait Locale
object Locales {

  case object `Great Britain` extends Locale
  case object `Ireland` extends Locale
  case object `United States` extends Locale
  case object `Italy` extends Locale
  case object `Malta` extends Locale
  case object `Portugal` extends Locale
  case object `Spain` extends Locale
  case object `France` extends Locale
  case object `Belgium` extends Locale
  case object `Luxembourg` extends Locale
  case object `Netherlands` extends Locale
  case object `East Frisia` extends Locale
  case object `Germany` extends Locale
  case object `Austria` extends Locale
  case object `Switzerland` extends Locale
  case object `Iceland` extends Locale
  case object `Denmark` extends Locale
  case object `Norway` extends Locale
  case object `Sweden` extends Locale
  case object `Finland` extends Locale
  case object `Estonia` extends Locale
  case object `Latvia` extends Locale
  case object `Lithuania` extends Locale
  case object `Poland` extends Locale
  case object `Czech Republic` extends Locale
  case object `Slovakia` extends Locale
  case object `Hungary` extends Locale
  case object `Romania` extends Locale
  case object `Bulgaria` extends Locale
  case object `Bosnia/Herzegovina` extends Locale
  case object `Croatia` extends Locale
  case object `Kosovo` extends Locale
  case object `Macedonia` extends Locale
  case object `Montenegro` extends Locale
  case object `Serbia` extends Locale
  case object `Slovenia` extends Locale
  case object `Albania` extends Locale
  case object `Greece` extends Locale
  case object `Russia` extends Locale
  case object `Belarus` extends Locale
  case object `Moldova` extends Locale
  case object `Ukraine` extends Locale
  case object `Armenia` extends Locale
  case object `Azerbaijan` extends Locale
  case object `Georgia` extends Locale
  case object `Kazakhstan/Uzbekistan` extends Locale
  case object `Turkey` extends Locale
  case object `Arabia/Persia` extends Locale
  case object `Israel` extends Locale
  case object `China` extends Locale
  case object `India/Sri Lanka` extends Locale
  case object `Japan` extends Locale
  case object `Korea` extends Locale
  case object `Vietnam` extends Locale

  case object `Other` extends Locale

}
