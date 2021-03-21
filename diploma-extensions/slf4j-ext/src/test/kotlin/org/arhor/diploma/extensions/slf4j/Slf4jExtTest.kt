package org.arhor.diploma.extensions.slf4j

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ExtendWith(MockitoExtension::class)
class Slf4jExtTest {

    lateinit var log: Logger

    @BeforeEach
    fun setUp() {
        log = LoggerFactory.getLogger(Slf4jExtTest::class.java)
    }
}