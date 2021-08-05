package org.arhor.diploma.service.impl

import kotlinx.coroutines.runBlocking
import org.arhor.diploma.TestDto
import org.arhor.diploma.TestEntity
import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@ExtendWith(MockitoExtension::class, RandomParameter.Resolver::class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
internal class UpdaterMixinTest {

    // @formatter:off
    @Mock private lateinit var converter: Converter<TestEntity, TestDto>
    @Mock private lateinit var repository: CoroutineCrudRepository<TestEntity, String>
    @Mock private lateinit var testDto: TestDto
    @Mock private lateinit var testEntity: TestEntity
    // @formatter:on

    @InjectMocks
    private lateinit var updaterUnderTest: UpdaterMixin<TestEntity, TestDto, String>

    @Test
    fun `should throw IllegalArgumentException`() {
        // given
        doReturn(null).`when`(testDto).id

        // when
        val action = ThrowingCallable { runBlocking { updaterUnderTest.update(testDto) } }

        // then
        assertThatThrownBy(action)
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(UpdaterMixin.KEY_PROPERTY)

        verifyNoInteractions(repository, converter)
    }

    @Test
    fun `should throw EntityNotFoundException`(
        @RandomParameter testId: String
    ): Unit = runBlocking {
        // given
        doReturn(testId).`when`(testDto).id
        doReturn(null).`when`(repository).findById(testId)

        // when
        val action = ThrowingCallable { runBlocking { updaterUnderTest.update(testDto) } }

        // then
        assertThatThrownBy(action)
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasFieldOrPropertyWithValue("propName", UpdaterMixin.KEY_PROPERTY)
            .hasFieldOrPropertyWithValue("propValue", testId)

        verify(repository).findById(testId)
        verifyNoInteractions(converter)
    }

    @Test
    fun `should save passed object without exception`(
        @RandomParameter testId: String
    ): Unit = runBlocking {
        // given
        doReturn(testId).`when`(testDto).id
        doReturn(testEntity).`when`(repository).findById(testId)
        doReturn(testEntity).`when`(repository).save(testEntity)
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)

        // when
        val result = updaterUnderTest.update(testDto)

        // then
        Assertions.assertThat(result)
            .isNotNull
            .isEqualTo(testDto)
    }
}