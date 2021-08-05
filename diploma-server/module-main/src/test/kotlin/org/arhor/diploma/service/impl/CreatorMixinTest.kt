package org.arhor.diploma.service.impl

import kotlinx.coroutines.runBlocking
import org.arhor.diploma.TestDto
import org.arhor.diploma.TestEntity
import org.arhor.diploma.commons.Converter
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions
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
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@ExtendWith(MockitoExtension::class, RandomParameter.Resolver::class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
internal class CreatorMixinTest {

    // @formatter:off
    @Mock private lateinit var converter: Converter<TestEntity, TestDto>
    @Mock private lateinit var repository: CoroutineCrudRepository<TestEntity, String>
    @Mock private lateinit var testDto: TestDto
    @Mock private lateinit var testEntity: TestEntity
    // @formatter:on

    @InjectMocks
    private lateinit var creatorUnderTest: CreatorMixin<TestEntity, TestDto, String>

    @Test
    fun `should throw IllegalArgumentException`(
        @RandomParameter testId: String
    ) {
        // given
        doReturn(testId).`when`(testDto).id

        // when
        val action = ThrowingCallable { runBlocking { creatorUnderTest.create(testDto) } }

        // then
        Assertions.assertThatThrownBy(action)
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(testId)

        verifyNoInteractions(repository)
    }

    @Test
    fun `should create passed object without exception`(): Unit = runBlocking {
        // given
        doReturn(null).`when`(testDto).id
        doReturn(testEntity).`when`(converter).mapDtoToEntity(testDto)
        doReturn(testEntity).`when`(repository).save(testEntity)
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)

        // when
        val result = creatorUnderTest.create(testDto)

        // then
        Assertions.assertThat(result).isEqualTo(testDto)
    }
}