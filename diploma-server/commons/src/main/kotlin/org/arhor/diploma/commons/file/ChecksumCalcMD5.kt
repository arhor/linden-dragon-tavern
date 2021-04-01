package org.arhor.diploma.commons.file

import mu.KLogging
import java.io.File
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object ChecksumCalcMD5 : ChecksumCalc, KLogging() {

    private val digest: MessageDigest?
        get() = try {
            MessageDigest.getInstance("MD5")
        } catch (ex: NoSuchAlgorithmException) {
            logger.error(ex.message, ex)
            null
        }

    override fun calculate(file: File): String {
        return calculate(file.inputStream())
    }

    fun calculate(stream: InputStream): String {
        return digest?.let {

            stream.buffered().chunked { chunk, size ->
                it.update(chunk, 0, size)
            }

            BigInteger(1, it.digest())
                .toString(16)
                .padStart(32, '0')
        } ?: ""
    }
}