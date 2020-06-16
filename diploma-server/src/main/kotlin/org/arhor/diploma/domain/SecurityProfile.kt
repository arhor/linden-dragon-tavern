package org.arhor.diploma.domain

import org.arhor.diploma.domain.core.DomainObject
import javax.persistence.*


@Entity
@Table(name = "SecurityProfile")
class SecurityProfile: DomainObject<Long>() {

  @OneToMany(
      mappedBy = "certificate",
      fetch = FetchType.LAZY,
      cascade = [CascadeType.ALL],
      orphanRemoval = true)
  var accounts: List<Account> = listOf()


  var authorities : List<String> = listOf()

}