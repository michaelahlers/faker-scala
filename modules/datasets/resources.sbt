enablePlugins(JörgMichaelNameDictionaryPlugin)
enablePlugins(OpenData500CompanyDictionariesPlugin)
enablePlugins(UsCensus1990NameDictionariesPlugin)
enablePlugins(UsCensus2000NameDictionariesPlugin)

jörgMichaelNameDictionaryOutputDirectory :=
  (Compile / resourceManaged).value /
    "ahlers" /
    "faker" /
    "datasets" /
    "jörgmichael" /
    "persons"

openData500CompanyOutputDirectory :=
  (Compile / resourceManaged).value /
    "ahlers" /
    "faker" /
    "datasets" /
    "opendata500" /
    "companies"

usCensus1990NameDictionaryOutputDirectory :=
  (Compile / resourceManaged).value /
    "ahlers" /
    "faker" /
    "datasets" /
    "uscensus1990" /
    "persons"

usCensus2000NameDictionaryOutputDirectory :=
  (Compile / resourceManaged).value /
    "ahlers" /
    "faker" /
    "datasets" /
    "uscensus2000" /
    "persons"
