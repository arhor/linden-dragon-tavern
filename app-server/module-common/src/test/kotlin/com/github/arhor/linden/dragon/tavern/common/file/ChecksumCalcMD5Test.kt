package com.github.arhor.linden.dragon.tavern.common.file

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
internal class ChecksumCalcMD5Test {

    @Test
    fun `calculate method should return the same checksum for the same sources`(
        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter(length = 50) source: String
    ) {
        // when
        val checksum1 = ChecksumCalcMD5.calculate(source::byteInputStream)
        val checksum2 = ChecksumCalcMD5.calculate(source::byteInputStream)

        // then
        assertThat(checksum1).isEqualTo(checksum2)
    }
}
