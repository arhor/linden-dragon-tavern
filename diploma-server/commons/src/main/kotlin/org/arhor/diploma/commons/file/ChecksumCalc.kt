package org.arhor.diploma.commons.file

import java.io.InputStream

interface ChecksumCalc {

    /**
     * Calculates checksum for the passed input stream. Also closes passed stream.
     */
    fun calculate(source: () -> InputStream): String
}