package org.arhor.diploma.commons.file

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

        // then
        assertThat(checksum1).isEqualTo(checksum2)
    }
}
