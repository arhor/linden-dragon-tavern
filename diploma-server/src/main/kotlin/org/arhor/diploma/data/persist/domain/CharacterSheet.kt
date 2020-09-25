//package org.arhor.diploma.data.persist.domain
//
//import org.arhor.diploma.data.persist.domain.core.DomainObject
//import org.arhor.diploma.service.dto.Abilities
//import org.hibernate.annotations.Cache
//import org.hibernate.annotations.CacheConcurrencyStrategy
//import javax.persistence.*
//
//@Entity
//@Table(name = "character_sheets")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//class CharacterSheet(
//    @Column(length = 50, name = "character_name")
//    val characterName: String?,
//
//    @Column
//    val background: String,
//
//    @Column
//    val playerName: String,
//
//    @Column
//    val race: String,
//
//    @Column
//    val alignment: String,
//
//    @Column
//    val experiencePoints: String,
//
//    @Embedded
//    val abilities: Abilities,
//
//    @OneToMany(mappedBy = "characterSheet")
//    val classes: List<CharacterClass>,
//) : DomainObject<Long>() {
//
//    @Embeddable
//    class Abilities(
//        @Column(nullable = false) val str: Byte,
//        @Column(nullable = false) val dex: Byte,
//        @Column(nullable = false) val con: Byte,
//        @Column(nullable = false) val int: Byte,
//        @Column(nullable = false) val wis: Byte,
//        @Column(nullable = false) val cha: Byte,
//    )
//}