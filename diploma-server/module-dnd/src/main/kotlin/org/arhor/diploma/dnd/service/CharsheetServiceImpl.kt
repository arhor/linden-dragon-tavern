package org.arhor.diploma.dnd.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class CharsheetServiceImpl : CharsheetService {

    @Value("classpath:dnd/sheets/5E_CharacterSheet_Fillable_Page1.pdf")
    lateinit var resource: Resource

    override fun getEmptyCharsheet() = resource
}