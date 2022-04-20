package com.github.arhor.linden.dragon.tavern.common

interface Deletable {

    val isDeleted: Boolean

    fun onDelete() { /* do nothing by default */ }
}
