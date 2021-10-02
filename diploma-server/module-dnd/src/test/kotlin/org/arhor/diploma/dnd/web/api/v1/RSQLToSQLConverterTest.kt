package org.arhor.diploma.dnd.web.api.v1

import cz.jirutka.rsql.parser.RSQLParser
import org.junit.jupiter.api.Test

internal class RSQLToSQLConverterTest {

    @Test
    fun shouldRun() {
        // given
        val query = "(firstName=='Str[Max]';lastName=='Str[Buryshynets]'),age>='Num[3,0]'"

        // when
        val predicate = RSQLParser().parse(query).accept(RSQLToSQLConverterUnsafe())

        // then
        println(predicate)
    }
}