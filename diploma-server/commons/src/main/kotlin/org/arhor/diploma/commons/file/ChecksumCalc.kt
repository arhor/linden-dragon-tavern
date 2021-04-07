package org.arhor.diploma.commons.file

import java.io.File
import java.io.InputStream

interface ChecksumCalc {

    fun calculate(file: File): String

    /**
     * Calculates checksum for the passed input stream. Also closes passed stream.
     */
    fun calculate(stream: InputStream): String

    fun calculate(source: () -> InputStream): String
}