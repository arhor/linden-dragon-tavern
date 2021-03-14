package org.arhor.diploma.data.file

import org.arhor.diploma.data.file.model.Spell
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

private typealias SpellDataProvider = DataProviderImplDefault<Spell, Spell.Details, String>

@Service
class SpellProviderImpl(loader: ResourceLoader) : SpellProvider, SpellDataProvider(loader) {

    override val resourceName get() = "spell"
    override val resourcePath get() = "classpath:data/5e-SRD-Spells.json"
    override val resourceType get() = Array<Spell.Details>::class.java

    override fun shrinkData(details: Spell.Details): Spell {
        return Spell(
            name = details.name,
            level = details.level,
            school = details.school
        )
    }
}