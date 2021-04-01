package org.arhor.diploma.commons.file

import java.io.File

interface ChecksumCalc {

    fun calculate(file: File): String
}