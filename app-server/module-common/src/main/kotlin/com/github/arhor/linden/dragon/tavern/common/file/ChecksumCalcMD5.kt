package com.github.arhor.linden.dragon.tavern.common.file

import mu.KotlinLogging
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private val logger = KotlinLogging.logger {}

object ChecksumCalcMD5 : ChecksumCalc {

    private val digest: MessageDigest?
        get() = try {
            MessageDigest.getInstance("MD5")
        } catch (ex: NoSuchAlgorithmException) {
            logger.error(ex.message, ex)
            null
        }

    override fun calculate(source: () -> InputStream): String {
        return digest?.let {

            source().buffered().use { bufferedStream ->
                bufferedStream.chunked { chunk, size ->
                    it.update(chunk, 0, size)
                }
            }

            BigInteger(1, it.digest())
                .toString(16)
                .padStart(32, '0')
        } ?: ""
    }
}
