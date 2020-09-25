package org.arhor.diploma.service.export

import org.apache.pdfbox.pdmodel.PDDocument
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@Component
class ExportToPDF(private val resourceLoader: ResourceLoader) {

    fun execute() {
        val charsheetPage1 = resourceLoader.getResource(CHAR_SHEET_PAGE_1)

        if (charsheetPage1.isReadable) {

            charsheetPage1.inputStream.use {
                val document = PDDocument.load(it)
                val acroForm = document.documentCatalog.acroForm

                if (acroForm != null) {
                    for (field in acroForm.fields) {
                        println(field.fullyQualifiedName)
                    }
                }
            }
        }
    }

    companion object {
        const val CHAR_SHEET_PAGE_1 = "classpath:sheets/5E_CharacterSheet_Fillable_Page1.pdf"
        const val CHAR_SHEET_PAGE_2 = "classpath:sheets/5E_CharacterSheet_Fillable_Page2.pdf"
        const val CHAR_SHEET_PAGE_3 = "classpath:sheets/5E_CharacterSheet_Fillable_Page3.pdf"
    }
}