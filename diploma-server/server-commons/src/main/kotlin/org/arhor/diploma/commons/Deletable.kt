package org.arhor.diploma.commons

interface Deletable {

    val isDeleted: Boolean

    @JvmDefault
    fun onDelete() { /* do nothing by default */ }
}
