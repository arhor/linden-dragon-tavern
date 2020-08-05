package org.arhor.diploma.core

interface Deletable {

    var isDeleted: Boolean

    @JvmDefault
    fun onDelete() { /* do nothing by default */ }
}
