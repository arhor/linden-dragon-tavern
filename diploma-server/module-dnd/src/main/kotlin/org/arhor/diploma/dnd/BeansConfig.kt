package org.arhor.diploma.dnd

import org.arhor.diploma.commons.file.ChecksumCalc
import org.arhor.diploma.commons.file.ChecksumCalcMD5
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class BeansConfig {

    @Bean
    fun checksumCalc(): ChecksumCalc {
        return ChecksumCalcMD5
    }
}