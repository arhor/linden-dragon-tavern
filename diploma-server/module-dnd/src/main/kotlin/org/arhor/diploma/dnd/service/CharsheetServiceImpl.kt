package org.arhor.diploma.dnd.service

import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException

@Service
class CharsheetServiceImpl(private val resourceLoader: ResourceLoader) : CharsheetService {

    override fun getEmptyCharsheet(): File {
        return resourceLoader.getResource(CHAR_SHEET_PAGE_1).takeIf { it.isReadable }?.file
            ?: throw IOException("File is not readable")
    }

    companion object {
        private const val CHAR_SHEET_PAGE_1 = "classpath:dnd/sheets/5E_CharacterSheet_Fillable_Page1.pdf"
        private const val CHAR_SHEET_PAGE_2 = "classpath:dnd/sheets/5E_CharacterSheet_Fillable_Page2.pdf"
        private const val CHAR_SHEET_PAGE_3 = "classpath:dnd/sheets/5E_CharacterSheet_Fillable_Page3.pdf"
    }
}