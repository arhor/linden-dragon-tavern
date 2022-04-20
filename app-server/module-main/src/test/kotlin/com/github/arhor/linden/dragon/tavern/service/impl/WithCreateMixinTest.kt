package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.TestDto
import com.github.arhor.linden.dragon.tavern.TestEntity
import com.github.arhor.linden.dragon.tavern.common.Converter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.data.repository.CrudRepository

@ExtendWith(MockitoExtension::class, com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
internal class WithCreateMixinTest {

    // @formatter:off
    @Mock private lateinit var converter: Converter<TestEntity, TestDto>
    @Mock private lateinit var repository: CrudRepository<TestEntity, String>
    @Mock private lateinit var testDto: TestDto
    @Mock private lateinit var testEntity: TestEntity
    // @formatter:on

    @InjectMocks
    private lateinit var creatorUnderTest: WithCreateMixin<TestEntity, TestDto, String>

    @Test
    fun `should throw IllegalArgumentException`(
        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter testId: String
    ) {
        // given
        doReturn(testId).`when`(testDto).id

        // when
        val action = ThrowingCallable { creatorUnderTest.create(testDto) }

        // then
        assertThatThrownBy(action)
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(testId)

        verifyNoInteractions(repository)
    }

    @Test
    fun `should create passed object without exception`() {
        // given
        doReturn(null).`when`(testDto).id
        doReturn(testEntity).`when`(converter).mapDtoToEntity(testDto)
        doReturn(testEntity).`when`(repository).save(testEntity)
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)

        // when
        val result = creatorUnderTest.create(testDto)

        // then
        assertThat(result).isEqualTo(testDto)
    }
}
