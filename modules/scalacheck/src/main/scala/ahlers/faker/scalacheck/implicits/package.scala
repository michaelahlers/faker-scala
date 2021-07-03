package ahlers.faker.scalacheck

import ahlers.faker.scalacheck.company.implicits.CompanyArbitraryInstances
import ahlers.faker.scalacheck.name.implicits.NameArbitraryInstances

package object implicits extends CompanyArbitraryInstances with NameArbitraryInstances
