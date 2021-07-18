package org.arhor.diploma.commons

interface Deletable {

    val isDeleted: Boolean

    fun onDelete() { /* do nothing by default */ }
}
