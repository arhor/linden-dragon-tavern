package com.github.arhor.linden.dragon.tavern.dnd.data.model
//
//import com.github.arhor.linden.dragon.tavern.data.persist.domain.core.DomainObject
//import org.hibernate.annotations.Cache
//import org.hibernate.annotations.CacheConcurrencyStrategy
//import javax.persistence.*
//
//@Entity
//@Table(name = "character_classes")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//class CharacterClass(
//    @Column(nullable = false, length = 30)
//    val className: String,
//
//    @Column(nullable = false)
//    val classLevel: Byte,
//
//    @ManyToOne
//    @JoinColumn(name = "character_sheet_id", nullable = false)
//    val characterSheet: CharacterSheet
//) : DomainObject<Long>()
