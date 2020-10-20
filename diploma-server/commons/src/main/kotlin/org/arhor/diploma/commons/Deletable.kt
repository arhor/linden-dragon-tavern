package org.arhor.diploma.commons

interface Deletable {

    var isDeleted: Boolean

    @JvmDefault
    fun onDelete() { /* do nothing by default */ }
}
