package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.arhor.diploma.TestDto
import org.arhor.diploma.TestEntity
import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@ExtendWith(MockitoExtension::class, RandomParameter.Resolver::class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
internal class ReaderMixinTest {

    // @formatter:off
    @Mock private lateinit var converter: Converter<TestEntity, TestDto>
    @Mock private lateinit var repository: CoroutineCrudRepository<TestEntity, String>
    @Mock private lateinit var testDto: TestDto
    @Mock private lateinit var testEntity: TestEntity
    // @formatter:on

    @InjectMocks
    private lateinit var readerUnderTest: ReaderMixin<TestEntity, TestDto, String>

    @Test
    fun `should return correct DTO list for object returned by repository`(): Unit = runBlocking {
        // given
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
        doReturn(flowOf(testEntity)).`when`(repository).findAll()

        // when
        val result = readerUnderTest.getList().toList()

        // then
        assertThat(result)
            .isNotNull
            .isNotEmpty
            .hasSize(1)
            .contains(testDto)

        verify(converter).mapEntityToDto(testEntity)
        verify(repository).findAll()
    }

    @Test
    fun `should return correct DTO for object returned by repository`(
        @RandomParameter search: String
    ): Unit = runBlocking {
        // given
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
        doReturn(testEntity).`when`(repository).findById(search)

        // when
        val result = readerUnderTest.getOne(search)

        // then
        assertThat(result)
            .isNotNull
            .isSameAs(testDto)

        verify(converter).mapEntityToDto(testEntity)
        verify(repository).findById(search)
    }

    @Test
    fun `should throw EntityNotFoundException`(@RandomParameter search: String): Unit = runBlocking {
        // given
        doReturn(null).`when`(repository).findById(search)

        // when
        val action = ThrowingCallable { runBlocking { readerUnderTest.getOne(search) } }

        // then
        assertThatThrownBy(action)
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasFieldOrPropertyWithValue("propName", ReaderMixin.KEY_PROPERTY)
            .hasFieldOrPropertyWithValue("propValue", search)

        verify(repository).findById(search)
    }

    @Test
    fun `should return size provided by repository`(@RandomParameter size: Long): Unit = runBlocking {
        // given
        doReturn(size).`when`(repository).count()

        // when
        val result = readerUnderTest.getTotalSize()

        // then
        assertThat(result).isEqualTo(size)
    }
}
