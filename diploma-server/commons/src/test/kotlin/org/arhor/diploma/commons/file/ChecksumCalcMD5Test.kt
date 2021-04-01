package org.arhor.diploma.commons.file

import mu.KLogging
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(RandomParameter.Resolver::class)
class ChecksumCalcMD5Test {

    @Test
    fun `calculate method should return the same checksum for the same sources`(
        @RandomParameter(length = 50) source: String
    ) {
        // when
        val checksum1 = ChecksumCalcMD5.calculate(source.byteInputStream())
        val checksum2 = ChecksumCalcMD5.calculate(source.byteInputStream())

        logger.info { "MUSYA1" }
        logger.info("MUSYA2")
        logger.info("MUSYA3")
        logger.info("MUSYA4")

        // then
        assertThat(checksum1).isEqualTo(checksum2)
    }

    companion object : KLogging()
}
