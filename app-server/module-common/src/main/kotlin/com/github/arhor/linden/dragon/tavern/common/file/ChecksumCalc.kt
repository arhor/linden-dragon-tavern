package com.github.arhor.linden.dragon.tavern.common.file

import java.io.InputStream

// TODO: add suspending version fo the calculate method
interface ChecksumCalc {

    /**
     * Calculates checksum for the passed input stream. Also closes passed stream.
     */
    fun calculate(source: () -> InputStream): String
}
