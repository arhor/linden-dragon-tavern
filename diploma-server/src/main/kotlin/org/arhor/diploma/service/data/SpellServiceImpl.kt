package org.arhor.diploma.service.data

import org.arhor.diploma.service.data.model.Spell
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

private typealias SpellDataProvider = DataProviderImplDefault<Spell, Spell.Details, String>

@Service
class SpellServiceImpl(loader: ResourceLoader) : SpellService, SpellDataProvider(loader) {

    override val resourcePath = "classpath:data/5e-SRD-Spells.json"
    override val resourceType = Array<Spell.Details>::class.java

    override fun shrinkData(details: Spell.Details): Spell {
        return Spell(
            name = details.name,
            level = details.level,
            school = details.school
        )
    }
}